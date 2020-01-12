package kvstore

import akka.actor.{Actor, ActorRef, Props}
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

class Replica(val arbiter: ActorRef, persistenceProps: Props)
  extends Actor with PrimaryNode with SecondaryNode {

  import Persistence._
  import context.dispatcher

  var kv = Map.empty[String, String]
  private val persistence: ActorRef = context.system.actorOf(persistenceProps)
  //[id/seq,(client/replicator,persist)]
  var pendingPersists = Map.empty[Long, (ActorRef, Persist)]

  override def preStart(): Unit = {
    arbiter ! Join

    // keep telling persistence to persist, until removed from map, for both primary and secondary
    context.system.scheduler.scheduleAtFixedRate(0.millisecond, 100.millisecond) { () =>
      pendingPersists.foreach { case (_, (_, persist)) => persistence ! persist }
    }
  }

  def receive: Receive = {
    case JoinedPrimary => context.become(primary)
    case JoinedSecondary => context.become(secondary)
  }

  def isPersistFinished(id: Long) = !pendingPersists.contains(id)

  def goPersist(id: Long, caller: ActorRef, persist: Persist) = {
    persistence ! persist
    pendingPersists += (id -> (caller, persist))
  }
}
