package kvstore

import akka.actor.{Actor, ActorRef, PoisonPill, Props}
import kvstore.Arbiter._

import scala.concurrent.duration._

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

  import Persistence._
  import Replica._
  import Replicator._
  import context.dispatcher

  override def preStart(): Unit = {
    arbiter ! Join

    // keep telling persistence to persist, until removed from map
    // for both primary and secondary
    context.system.scheduler.scheduleAtFixedRate(0.millisecond, 100.millisecond) { () =>
      if (pendingPersists.nonEmpty) {
        pendingPersists.foreach { entry =>
          val (_, (_, persist)) = entry
          persistence ! persist
        }
      }
    }
  }

  def nextSeq() = {
    val ret = expectedSeq
    expectedSeq += 1
    ret
  }

  private var kv = Map.empty[String, String]

  private var expectedSeq = 0L

  private val persistence: ActorRef = context.system.actorOf(persistenceProps)
  private var pendingPersists = Map.empty[Long, (ActorRef, Persist)]
  // [(id,replicator),caller]
  private var pendingReplicates = Map.empty[(Long, ActorRef), ActorRef]

  // a map from secondary replicas to replicators
  private var secondaries = Map.empty[ActorRef, ActorRef]
  // the current set of replicators
  private var replicators = Set.empty[ActorRef]


  def receive: Receive = {
    case JoinedPrimary => context.become(primary)
    case JoinedSecondary => context.become(secondary)
  }

  val primary: Receive = {
    case Insert(k, v, id) =>
      kv += (k -> v)
      goPersist(id, sender(), Persist(k, Some(v), id))
      goReplicate(id, sender(), Replicate(k, Some(v), id))

    case Remove(k, id) =>
      kv -= k
      goPersist(id, sender(), Persist(k, None, id))
      goReplicate(id, sender(), Replicate(k, None, id))

    case Persisted(k, id) =>
      val (client, _) = pendingPersists(id)
      pendingPersists -= id
      // when persist finished, check if all replications also finished, ack if so
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
          //for a removed secondary, pretend all its pending replications are finished
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
          if (!pendingPersists.contains(seq)) sender() ! SnapshotAck(k, seq)
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
      val allReplicationFinished = !pendingReplicates.keys.exists(k => k._1 == id)
      val persistFinished = !pendingPersists.contains(id)
      if (allReplicationFinished && persistFinished) client.get ! OperationAck(id)
    }
  }

  private def goReplicate(id: Long, caller: ActorRef, replicate: Replicate) = {
    replicators.foreach(replicator => {
      replicator ! replicate
      pendingReplicates += ((id, replicator) -> caller)
    })

    context.system.scheduler.scheduleOnce(1.second) {
      val unfinishedReplicas: Iterable[(Long, ActorRef)] = pendingReplicates.keys.filter(k => k._1 == id)
      val persistNotFinished = pendingPersists.contains(id)
      val replicationNotFinished = unfinishedReplicas.nonEmpty

      if (persistNotFinished || replicationNotFinished) {
        caller ! OperationFailed(id)
        pendingPersists -= id
        pendingReplicates --= unfinishedReplicas
      }
    }
  }

  private def update(k: String, vOption: Option[String], seq: Long): Long = {
    if (vOption.isDefined) kv += (k -> vOption.get)
    else kv -= k

    goPersist(seq, sender(), Persist(k, vOption, seq))

    nextSeq()
  }

  private def goPersist(id: Long, caller: ActorRef, persist: Persist) = {
    persistence ! persist
    pendingPersists += (id -> (caller, persist))
  }
}

