package kvstore.node

import akka.actor.{ActorRef, PoisonPill}
import kvstore.Arbiter.Replicas
import kvstore.Persistence.{Persist, Persisted}
import kvstore.Replica._
import kvstore.Replicator.{Replicate, Replicated}
import kvstore.{Replica, Replicator}

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
      val replicator = sender()
      pendingReplicates.get((id, replicator)).foreach(client => {
        pendingReplicates -= ((id, replicator))
        ackToClientIfBothFinished(id, client)
      })

    case Replicas(replicas) =>
      val newJoiners = (replicas - self) -- secondaries.keys
      catchUp(newJoiners)
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

    failAfterOneSecond(id, client)
  }

  private def failAfterOneSecond(id: Long, client: ActorRef) = {
    context.system.scheduler.scheduleOnce(1.second) {
      if (!isPersistFinished(id) || !isAllReplicationsFinished(id)) {
        pendingPersists -= id
        pendingReplicates = pendingReplicates.dropWhile { case ((i, _), _) => i == id }
        client ! OperationFailed(id)
      }
    }
  }

  private def ackToClientIfBothFinished(id: Long, client: ActorRef): Unit = {
    if (isAllReplicationsFinished(id) && isPersistFinished(id)) {
      client ! OperationAck(id)
    }
  }

  private def catchUp(newJoiners: Set[ActorRef]): Unit = {
    newJoiners.foreach(newJoiner => {
      val newReplicator = context.actorOf(Replicator.props(newJoiner))
      secondaries += (newJoiner -> newReplicator)

      kv.foreach { case (k, v) =>
        //todo: what id to use here?
        newReplicator ! Replicate(k, Some(v), Long.MinValue)
      }
    })
  }

  private def drop(quitters: Set[ActorRef]): Unit = {
    quitters.foreach(quitter => {
      val replicator = secondaries(quitter)
      dropPendingReplicationsOf(replicator)
      replicator ! PoisonPill

      secondaries -= quitter
      //todo: need to stop the removed secondary as well?
    })
  }

  private def dropPendingReplicationsOf(replicator: ActorRef): Unit = {
    val toBeDropped = pendingReplicates.filter { case ((_, r), _) => r == replicator }

    //remove from pending, so that primary won't wait for it anymore
    pendingReplicates --= toBeDropped.keys
    //check if should ack to client after remove
    toBeDropped.foreach { case ((id, _), client) =>
      ackToClientIfBothFinished(id, client)
    }
  }

  private def isAllReplicationsFinished(id: Long) = {
    !pendingReplicates.keys.exists { case (i, _) => i == id }
  }

}
