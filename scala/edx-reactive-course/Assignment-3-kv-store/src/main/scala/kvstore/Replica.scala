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
  var pendingPersist = Map.empty[Long, (ActorRef, Persist)]
  var pendingReplicate = Map.empty[Long, (ActorRef, Int)]

  var kv = Map.empty[String, String]
  // a map from secondary replicas to replicators
  var secondaries = Map.empty[ActorRef, ActorRef]
  // the current set of replicators
  var replicators = Set.empty[ActorRef]

  var expectedSeq = 0L

  // keep telling persistence to persist, until removed from map
  // todo: move to persist method?
  context.system.scheduler.scheduleAtFixedRate(0.millisecond, 100.millisecond) { () =>
    if (pendingPersist.nonEmpty) {
      pendingPersist.foreach { entry =>
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
      kv += (k -> v)
      persistAndReplicate(id, sender(), Persist(k, Some(v), id))

    case Remove(k, id) =>
      kv -= k
      persistAndReplicate(id, sender(), Persist(k, None, id))

    case Persisted(k, id) =>
      val (client, _) = pendingPersist(id)
      pendingPersist -= id
      if (!pendingReplicate.contains(id)) client ! OperationAck(id)

    case Replicated(k, id) =>
      val (client, todo) = pendingReplicate(id)
      if ((todo - 1) == 0) {
        pendingReplicate -= id
        if (!pendingPersist.contains(id)) client ! OperationAck(id)
      } else {
        pendingReplicate += (id -> (client, todo - 1))
      }

    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)

    case Replicas(replicas) =>
      (replicas - self -- secondaries.keys)
        .foreach(newSecondary => {
          val replicator = context.actorOf(Replicator.props(newSecondary))
          secondaries += (newSecondary -> replicator)
          replicators += replicator
          kv.foreach(entry => {
            val (k, v) = entry
            replicator ! Replicate(k, Some(v), Long.MinValue)
            //todo: what id? what if failed here?
          })
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
      val (replicator, _) = pendingPersist(seq)
      replicator ! SnapshotAck(k, seq)
      pendingPersist -= seq
    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)
    case _ =>
  }

  private def persistAndReplicate(id: Long, caller: ActorRef, persist: Persist) = {
    def p2r(p: Persist) = Replicate(p.key, p.valueOption, p.id)

    persistence ! persist
    pendingPersist += (id -> (caller, persist))

    if (replicators.nonEmpty) {
      replicators.foreach(replicator => {
        replicator ! p2r(persist)
      })
      pendingReplicate += (id -> (caller, replicators.size))
    }

    context.system.scheduler.scheduleOnce(1.second) {
      if (pendingPersist.contains(id) || pendingReplicate.contains(id)) {
        caller ! OperationFailed(id)
        pendingPersist -= id
        pendingReplicate -= id
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

