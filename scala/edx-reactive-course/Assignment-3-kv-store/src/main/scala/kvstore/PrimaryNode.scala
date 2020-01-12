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
  // [(id,replicator),client]
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

    case Persisted(_, id) =>
      pendingPersists.get(id).foreach { case (client, _) =>
        pendingPersists -= id
        ackToClientIfBothFinished(id, client)
      }

    case Replicated(_, id) =>
      handleReplicated(id, sender())

    case Replicas(replicas) =>
      val newJoiners = (replicas - self) -- secondaries.keys
      replicateTo(newJoiners)
      val quitters = secondaries.keys.toSet -- (replicas - self)
      drop(quitters)

    case _ =>
  }

  private def goReplicate(id: Long, client: ActorRef, replicate: Replicate) = {
    val replicators = secondaries.values
    replicators.foreach(replicator => {
      replicator ! replicate
      pendingReplicates += ((id, replicator) -> client)
    })

    failAtOneSecond(id, client)
  }

  private def failAtOneSecond(id: Long, client: ActorRef) = {
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
    //should not directly use pendingReplicates(id) here
    //because replication to newJoiners will also tell primary Replicated, which will trigger this method
    //but replications to newJoiners are not in the pendingReplicates map
    pendingReplicates.get((id, replicator)).foreach(client => {
      pendingReplicates -= ((id, replicator))
      ackToClientIfBothFinished(id, client)
    })
  }

  private def replicateTo(newJoiners: Set[ActorRef]) = {
    newJoiners.foreach(newJoiner => {
      val newReplicator = context.actorOf(Replicator.props(newJoiner))
      secondaries += (newJoiner -> newReplicator)

      //todo: what id to use here?
      kv.foreach { case (k, v) =>
        newReplicator ! Replicate(k, Some(v), Long.MinValue)
      }
    })
  }

  private def drop(quitters: Set[ActorRef]) = {
    quitters.foreach(quitter => {
      val replicator = secondaries(quitter)
      dropPendingReplicationsOf(replicator)

      secondaries -= quitter
      replicator ! PoisonPill
      //todo: stop the removed secondary as well?
    })
  }

  private def dropPendingReplicationsOf(replicator: ActorRef) = {
    pendingReplicates.keys.foreach {
      //for a removed secondary, pretend all its pending replications are finished
      //so that primary won't wait for it anymore
      case (id, r) if r == replicator => handleReplicated(id, r)
    }
  }

  private def isAllReplicationsFinished(id: Long) = {
    !pendingReplicates.keys.exists { case (i, _) => i == id }
  }

}
