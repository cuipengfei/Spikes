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

class Replica(val arbiter: ActorRef, persistenceProps: Props) extends Actor with SecondaryNode {

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

  var kv = Map.empty[String, String]

  private val persistence: ActorRef = context.system.actorOf(persistenceProps)
  var pendingPersists = Map.empty[Long, (ActorRef, Persist)]
  // [(id,replicator),caller]
  private var pendingReplicates = Map.empty[(Long, ActorRef), ActorRef]

  // a map from secondary replicas to replicators
  private var secondaries = Map.empty[ActorRef, ActorRef]

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

    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)

    case Persisted(k, id) =>
      val (client, _) = pendingPersists(id)
      pendingPersists -= id
      ackToClientIfBothFinished(id, client)

    case Replicated(k, id) =>
      val replicator = sender()
      handleReplicated(id, replicator)

    case Replicas(replicas) =>
      val newJoiners = (replicas - self) -- secondaries.keys
      val quitters = secondaries.keys.toSet -- (replicas - self)
      replicateToNewJoiners(newJoiners)
      removeQuittersAndDropPendingReplications(quitters)

    case _ =>
  }

  private def removeQuittersAndDropPendingReplications(quitters: Set[ActorRef]) = {
    quitters.foreach(quitter => {
      val replicator = secondaries(quitter)

      pendingReplicates.keys.foreach(key => {
        val (id, r) = key
        //for a removed secondary, pretend all its pending replications are finished
        //so that primary won't wait for it anymore
        if (r == replicator) handleReplicated(id, r)
      })

      secondaries -= quitter
      replicator ! PoisonPill
    })
  }

  private def replicateToNewJoiners(newJoiners: Set[ActorRef]) = {
    newJoiners.foreach(newJoiner => {
      val newReplicator = context.actorOf(Replicator.props(newJoiner))
      secondaries += (newJoiner -> newReplicator)

      kv.foreach(entry => {
        val (k, v) = entry
        //todo: what id to use here?
        newReplicator ! Replicate(k, Some(v), Long.MinValue)
      })
    })
  }

  private def handleReplicated(id: Long, replicator: ActorRef) = {
    //should not directly use Map.apply here
    //need the if guard because replication to newJoiners will also trigger this method
    //and they are not in the pendingReplicates map
    val client: Option[ActorRef] = pendingReplicates.get((id, replicator))
    if (client.isDefined) {
      pendingReplicates -= ((id, replicator))
      ackToClientIfBothFinished(id, client.get)
    }
  }

  private def ackToClientIfBothFinished(id: Long, client: ActorRef) = {
    if (isAllReplicationsFinished(id) && isPersistFinished(id)) {
      client ! OperationAck(id)
    }
  }

  def isPersistFinished(id: Long) = {
    !pendingPersists.contains(id)
  }

  private def isAllReplicationsFinished(id: Long) = {
    !pendingReplicates.keys.exists(k => k._1 == id)
  }

  private def goReplicate(id: Long, client: ActorRef, replicate: Replicate) = {
    val replicators = secondaries.values
    replicators.foreach(replicator => {
      replicator ! replicate
      pendingReplicates += ((id, replicator) -> client)
    })

    context.system.scheduler.scheduleOnce(1.second) {
      val unfinishedReplicas: Iterable[(Long, ActorRef)] = pendingReplicates.keys.filter(k => k._1 == id)

      if (!isPersistFinished(id) || !isAllReplicationsFinished(id)) {
        client ! OperationFailed(id)
        pendingPersists -= id
        pendingReplicates --= unfinishedReplicas
      }
    }
  }


  def goPersist(id: Long, caller: ActorRef, persist: Persist) = {
    persistence ! persist
    pendingPersists += (id -> (caller, persist))
  }
}

