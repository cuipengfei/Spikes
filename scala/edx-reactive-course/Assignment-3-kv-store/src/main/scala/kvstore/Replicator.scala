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

  var seq = 0L

  override def preStart(): Unit = {
    timers.startTimerAtFixedRate("RetrySnapshot", "RetrySnapshot", 100.milliseconds)
  }

  private def nextSeq() = {
    val ret = seq
    seq += 1
    ret
  }

  def receive: Receive = {
    case msg@Replicate(k, v, _) =>
      this.replica ! Snapshot(k, v, seq)
      pendingAcks += ((seq, (sender(), msg)))
      nextSeq()

    case SnapshotAck(k, seq) =>
      pendingAcks.get(seq).foreach {
        case (primary, replicate) =>
          primary ! Replicated(k, replicate.id)
          pendingAcks -= seq
      }

    case "RetrySnapshot" =>
      pendingAcks.foreach { case (seq, (_, replicate)) =>
        this.replica ! Snapshot(replicate.key, replicate.valueOption, seq)
      }
    case _ =>
  }
}
