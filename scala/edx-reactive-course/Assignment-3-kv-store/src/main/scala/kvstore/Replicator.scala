package kvstore

import akka.actor.{Actor, ActorRef, Props}

import scala.concurrent.duration._
import scala.language.postfixOps

object Replicator {

  case class Replicate(key: String, valueOption: Option[String], id: Long)

  case class Replicated(key: String, id: Long)

  case class Snapshot(key: String, valueOption: Option[String], seq: Long)

  case class SnapshotAck(key: String, seq: Long)

  def props(replica: ActorRef): Props = Props(new Replicator(replica))
}

class Replicator(val replica: ActorRef) extends Actor {

  import Replicator._
  import context.dispatcher

  // map from sequence number to pair of sender and request
  var acks = Map.empty[Long, (ActorRef, Replicate)]

  // a sequence of not-yet-sent snapshots (you can disregard this if not implementing batching)
  var pending = Vector.empty[Snapshot]

  var _seqCounter = 0L

  // keep telling the partner replica to snapshot, until it has acked and removed from map
  context.system.scheduler.scheduleAtFixedRate(0 millisecond, 100 millisecond) { () =>
    acks.foreach { entry =>
      val (seq, (_, replicate)) = entry
      replica ! Snapshot(replicate.key, replicate.valueOption, seq)
    }
  }

  def nextSeq() = {
    val ret = _seqCounter
    _seqCounter += 1
    ret
  }

  def receive: Receive = {
    case replicate@Replicate(k, vOption, _) =>
      replica ! Snapshot(k, vOption, _seqCounter)
      acks += (_seqCounter -> (sender(), replicate))
      nextSeq()
    case SnapshotAck(k, seq) =>
      if (acks.contains(seq)) { //todo: why do i need this? when could seq not be in the map?
        val (primary, replicate) = acks(seq)
        primary ! Replicated(k, replicate.id)
        acks -= seq
      } else {
        //todo: add log to find out why
      }
    case _ =>
  }
}
