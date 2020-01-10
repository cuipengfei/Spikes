package kvstore

import akka.actor.{OneForOneStrategy, PoisonPill, Props, SupervisorStrategy, Terminated, ActorRef, Actor}
import kvstore.Arbiter._
import akka.pattern.{ask, pipe}
import scala.concurrent.duration._
import akka.util.Timeout

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

  var pendingPersistence = Map.empty[Long, (ActorRef, Persist)]

  var kv = Map.empty[String, String]
  // a map from secondary replicas to replicators
  var secondaries = Map.empty[ActorRef, ActorRef]
  // the current set of replicators
  var replicators = Set.empty[ActorRef]

  var expectedSeq = 0L

  // keep telling persistence to persist, until removed from map
  context.system.scheduler.scheduleAtFixedRate(0.millisecond, 100.millisecond) { () =>
    if (pendingPersistence.nonEmpty) {
      pendingPersistence.foreach { entry =>
        val (_, (_, persist)) = entry
        persistence ! persist
      }
    }
  }

  def receive = {
    case JoinedPrimary => context.become(leader)
    case JoinedSecondary => context.become(replica)
  }

  /* TODO Behavior for  the leader role. */
  val leader: Receive = {
    case Insert(k, v, id) =>
      kv += (k -> v)
      sender() ! OperationAck(id)
    case Remove(k, id) =>
      kv -= k
      sender() ! OperationAck(id)
    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)
    case _ =>
  }

  /* TODO Behavior for the replica role. */
  val replica: Receive = {
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
      val (replicator, _) = pendingPersistence(seq)
      replicator ! SnapshotAck(k, seq)
      pendingPersistence -= seq
    case Get(k, id) =>
      sender() ! GetResult(k, kv.get(k), id)
    case _ =>
  }

  private def update(k: String, vOption: Option[String], seq: Long) = {
    if (vOption.isDefined) kv += (k -> vOption.get)
    else kv -= k

    persistence ! Persist(k, vOption, seq)
    pendingPersistence += (seq -> (sender(), Persist(k, vOption, seq)))

    expectedSeq += 1
  }
}

