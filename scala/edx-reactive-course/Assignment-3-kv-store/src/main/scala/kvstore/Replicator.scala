package kvstore

import akka.actor.{Actor, ActorLogging, ActorRef, Props, Timers}

import scala.concurrent.duration._
import scala.language.postfixOps

object Replicator {

  case class Replicate(key: String, valueOption: Option[String], id: Long)

  case class Replicated(key: String, id: Long)

  case class Snapshot(key: String, valueOption: Option[String], seq: Long)

  case class SnapshotAck(key: String, seq: Long)

  def props(replica: ActorRef): Props = Props(new Replicator(replica))
}

class Replicator(val replica: ActorRef) extends Actor with ActorLogging with Timers {

  import Replicator._

  // map from sequence number to pair of sender and request
  //[seq,(primary,replicate)]
  var pendingAcks = Map.empty[Long, (ActorRef, Replicate)]

  var _seqCounter = 0L

  override def preStart(): Unit = {
    timers.startTimerAtFixedRate("RetrySnapshot", "RetrySnapshot", 100.milliseconds)
  }

  private def nextSeq() = {
    val ret = _seqCounter
    _seqCounter += 1
    ret
  }

  def receive: Receive = {
    case msg@Replicate(k, vOption, _) =>
      replica ! Snapshot(k, vOption, _seqCounter)
      pendingAcks += ((_seqCounter, (sender(), msg)))
      nextSeq()

    case SnapshotAck(k, seq) =>
      //todo: why can not do "map(seq)"? when could seq not be in the map?
      // when could the same secondary ack to its partner replicator more then once?
      // once a secondary finishes its persistence it ack to replicator
      // which leads the replicator to remove one item from pending acks
      // which leads to no more retries, so the following if statement should never hit
      // but it does, why?
      if (!pendingAcks.contains(seq)) log.error(s"$seq ack again, $sender")

      pendingAcks.get(seq).foreach {
        case (primary, replicate) =>
          primary ! Replicated(k, replicate.id)
          log.info(s"$seq ack, $sender")
          pendingAcks -= seq
      }

    case "RetrySnapshot" =>
      pendingAcks.foreach { case (seq, (_, replicate)) =>
        replica ! Snapshot(replicate.key, replicate.valueOption, seq)
      }
    case _ =>
  }
}
