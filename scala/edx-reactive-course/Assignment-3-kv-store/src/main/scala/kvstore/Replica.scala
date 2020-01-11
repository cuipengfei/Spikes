package kvstore

import akka.actor.{Actor, ActorRef, OneForOneStrategy, PoisonPill, Props, SupervisorStrategy, Terminated}
import kvstore.Arbiter._
import akka.pattern.{ask, pipe}

import scala.concurrent.duration._
import akka.util.Timeout
import com.typesafe.config.ConfigException.Null

object Replica {

  sealed trait Operation {
    def key: String

    def id: Long
  }

  case class Insert(key: String, value: String, id: Long) extends Operation

  case class Remove(key: String, id: Long) extends Operation

  case class Get(key: String, id: Long) extends Operation

  sealed trait OperationReply

  case class OperationAck(id: Long) extends OperationReply

  case class OperationFailed(id: Long) extends OperationReply

  case class GetResult(key: String, valueOption: Option[String], id: Long) extends OperationReply

  def props(arbiter: ActorRef, persistenceProps: Props): Props = Props(new Replica(arbiter, persistenceProps))
}

class Replica(val arbiter: ActorRef, persistenceProps: Props) extends Actor {

  import Replica._
  import Replicator._
  import Persistence._
  import context.dispatcher

  /*
   * The contents of this actor is just a suggestion, you can implement it in any way you like.
   */

  arbiter ! Join

  private val persistence: ActorRef = context.system.actorOf(persistenceProps)
  var pendingPersists = Map.empty[Long, (ActorRef, Persist)]
  var pendingReplicates = Map.empty[(Long, ActorRef), ActorRef]

  var kv = Map.empty[String, String]
  // a map from secondary replicas to replicators
  var secondaries = Map.empty[ActorRef, ActorRef]
  // the current set of replicators
  var replicators = Set.empty[ActorRef]

  var expectedSeq = 0L

  // keep telling persistence to persist, until removed from map
  // todo: move to persist method?
  context.system.scheduler.scheduleAtFixedRate(0.millisecond, 100.millisecond) { () =>
    if (pendingPersists.nonEmpty) {
      pendingPersists.foreach { entry =>
        val (_, (_, persist)) = entry
        persistence ! persist
      }
    }
  }

  def receive = {
    case JoinedPrimary => context.become(primary)
    case JoinedSecondary => context.become(secondary)
  }

  val primary: Receive = {
    case Insert(k, v, id) =>
      println(s"insert $k $v $id")
      kv += (k -> v)
      persistAndReplicate(id, sender(), Persist(k, Some(v), id))

    case Remove(k, id) =>
      kv -= k
      persistAndReplicate(id, sender(), Persist(k, None, id))

    case Persisted(k, id) =>
      val (client, _) = pendingPersists(id)
      pendingPersists -= id
      if (!pendingReplicates.keys.exists(k => k._1 == id)) client ! OperationAck(id)

    case Replicated(k, id) =>
      val replicator = sender()
      handleReplicated(id, replicator)

    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)

    case Replicas(replicas) =>
      val newJoiners = (replicas - self) -- secondaries.keys
      newJoiners.foreach(newJoiner => {
        val newReplicator = context.actorOf(Replicator.props(newJoiner))
        replicators += newReplicator
        secondaries += (newJoiner -> newReplicator)

        kv.foreach(entry => {
          val (k, v) = entry
          newReplicator ! Replicate(k, Some(v), Long.MinValue)
          //todo: what id to use here?
        })
      })

      val quitters = secondaries.keys.toSet -- (replicas - self)
      quitters.foreach(quitter => {
        val replicator = secondaries(quitter)
        replicators -= replicator
        secondaries -= quitter

        pendingReplicates.keys.foreach(k => {
          val (id, r) = k
          //for a removed secondary, pretend all its pending replicates are done
          if (r == replicator) handleReplicated(id, r)
        })

        replicator ! PoisonPill
      })
    case _ =>
  }

  val secondary: Receive = {
    case Snapshot(k, vOption, seq) =>
      if (seq == expectedSeq) {
        update(k, vOption, seq)
      } else {
        if (seq < expectedSeq) {
          sender() ! SnapshotAck(k, seq)
        } else {
          //......
        }
      }
    case Persisted(k, seq) =>
      val (replicator, _) = pendingPersists(seq)
      replicator ! SnapshotAck(k, seq)
      pendingPersists -= seq
    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)
    case _ =>
  }

  private def handleReplicated(id: Long, replicator: ActorRef) = {
    val client = pendingReplicates.get((id, replicator))
    if (client.isDefined) {
      pendingReplicates -= ((id, replicator))
      val noMorePendingReplicates = !pendingReplicates.keys.exists(k => k._1 == id)
      if (noMorePendingReplicates && !pendingPersists.contains(id))
        client.get ! OperationAck(id)
    }
  }

  private def persistAndReplicate(id: Long, caller: ActorRef, persist: Persist) = {
    def p2r(p: Persist) = Replicate(p.key, p.valueOption, p.id)

    persistence ! persist
    pendingPersists += (id -> (caller, persist))

    println(s"p and r $persist")

    if (replicators.nonEmpty) {
      replicators.foreach(replicator => {
        replicator ! p2r(persist)
        pendingReplicates += ((id, replicator) -> caller)
      })
    }

    context.system.scheduler.scheduleOnce(1.second) {
      val unfinishedReplicas: Iterable[(Long, ActorRef)] = pendingReplicates.keys.filter(k => k._1 == id)

      if (pendingPersists.contains(id) || unfinishedReplicas.nonEmpty) {
        caller ! OperationFailed(id)
        pendingPersists -= id
        pendingReplicates --= unfinishedReplicas
      }
    }
  }

  private def update(k: String, vOption: Option[String], seq: Long) = {
    if (vOption.isDefined) kv += (k -> vOption.get)
    else kv -= k

    persistAndReplicate(seq, sender(), Persist(k, vOption, seq))

    expectedSeq += 1
  }
}

