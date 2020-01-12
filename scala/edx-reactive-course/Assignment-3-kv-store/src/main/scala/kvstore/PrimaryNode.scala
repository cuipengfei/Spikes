package kvstore

import akka.actor.{ActorRef, PoisonPill}
import kvstore.Arbiter.Replicas
import kvstore.Persistence.{Persist, Persisted}
import kvstore.Replica._
import kvstore.Replicator.{Replicate, Replicated}

import scala.concurrent.duration._

trait PrimaryNode {

  this: Replica =>

  import context.dispatcher

  // a map from secondary replicas to replicators
  private var secondaries = Map.empty[ActorRef, ActorRef]
  // [(id,replicator),caller]
  private var pendingReplicates = Map.empty[(Long, ActorRef), ActorRef]

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

  private def goReplicate(id: Long, client: ActorRef, replicate: Replicate) = {
    val replicators = secondaries.values
    replicators.foreach(replicator => {
      replicator ! replicate
      pendingReplicates += ((id, replicator) -> client)
    })

    context.system.scheduler.scheduleOnce(1.second) {
      if (!isPersistFinished(id) || !isAllReplicationsFinished(id)) {
        client ! OperationFailed(id)
        pendingPersists -= id
        pendingReplicates = pendingReplicates.dropWhile { case ((i, _), _) => i == id }
      }
    }
  }

  private def ackToClientIfBothFinished(id: Long, client: ActorRef) = {
    if (isAllReplicationsFinished(id) && isPersistFinished(id)) {
      client ! OperationAck(id)
    }
  }

  private def handleReplicated(id: Long, replicator: ActorRef) = {
    //should not directly use Map.apply here, should have the if guard
    //need the if guard because replication to newJoiners will also trigger this method
    //and they are not in the pendingReplicates map
    val client: Option[ActorRef] = pendingReplicates.get((id, replicator))
    if (client.isDefined) {
      pendingReplicates -= ((id, replicator))
      ackToClientIfBothFinished(id, client.get)
    }
  }

  private def replicateToNewJoiners(newJoiners: Set[ActorRef]) = {
    newJoiners.foreach(newJoiner => {
      val newReplicator = context.actorOf(Replicator.props(newJoiner))
      secondaries += (newJoiner -> newReplicator)

      //todo: what id to use here?
      kv.foreach {
        case (k, v) =>
          newReplicator ! Replicate(k, Some(v), Long.MinValue)
      }
    })
  }

  private def removeQuittersAndDropPendingReplications(quitters: Set[ActorRef]) = {
    quitters.foreach(quitter => {
      val replicator = secondaries(quitter)

      //for a removed secondary, pretend all its pending replications are finished
      //so that primary won't wait for it anymore
      pendingReplicates.keys.foreach {
        case (id, r) if r == replicator => handleReplicated(id, r)
      }

      secondaries -= quitter
      replicator ! PoisonPill
    })
  }

  private def isAllReplicationsFinished(id: Long) = {
    !pendingReplicates.keys.exists { case (i, r) => i == id }
  }

}
