package actorbintree

import akka.actor.{Actor, ActorRef, PoisonPill, Props}

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor {

  import BinaryTreeNode._
  import BinaryTreeSet._

  var subtrees: Map[Position, ActorRef] = Map[Position, ActorRef]()
  var removed: Boolean = initiallyRemoved

  def receive: Receive = normal

  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive = {

    case ins: Insert =>
      if (ins.elem > elem) addTo(Right, ins)
      else if (ins.elem < elem) addTo(Left, ins)
      else {
        this.removed = false //it could be removed then added back
        ins.requester ! OperationFinished(ins.id)
      }

    case cts: Contains =>
      if (cts.elem == this.elem) cts.requester ! ContainsResult(cts.id, !removed)
      else if (cts.elem > this.elem) contains(Right, cts)
      else contains(Left, cts)

    case rm: Remove =>
      if (rm.elem == this.elem) {
        this.removed = true
        rm.requester ! OperationFinished(rm.id)
      }
      else if (rm.elem > this.elem) remove(Right, rm)
      else remove(Left, rm)

    case CopyTo(tree) =>
      if (this.removed) {
        if (subtrees.values.isEmpty) {
          context.parent ! CopyFinished
          self ! PoisonPill
        } else {
          context.become(copying(Set.from(subtrees.values), true))
          subtrees.values.foreach(child => child ! CopyTo(tree))
        }
      } else {
        context.become(copying(Set.from(subtrees.values), false))
        subtrees.values.foreach(child => child ! CopyTo(tree))
        tree ! Insert(self, 0, elem)
      }
  }

  private def remove(pos: Position, rm: Remove): Unit = {
    subtrees.get(pos) match {
      case Some(subTree) => subTree ! rm
      case None => rm.requester ! OperationFinished(rm.id)
    }
  }

  private def contains(pos: Position, cts: Contains): Unit = {
    subtrees.get(pos) match {
      case Some(subTree) => subTree ! cts
      case None => cts.requester ! ContainsResult(cts.id, false)
    }
  }

  private def addTo(pos: Position, ins: Insert): Unit = {
    subtrees.get(pos) match {
      case Some(subTree) => subTree ! ins
      case None =>
        subtrees = subtrees + (pos -> createNode(ins.elem))
        ins.requester ! OperationFinished(ins.id)
    }
  }

  def createNode(elem: Int): ActorRef = context.actorOf(BinaryTreeNode.props(elem, initiallyRemoved = false))

  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = {
    case OperationFinished(id) =>
      if (expected.isEmpty) {
        context.parent ! CopyFinished
        self ! PoisonPill
      } else {
        context.become(copying(expected, true))
      }
    case CopyFinished =>
      val oneLess = expected - sender()
      if (oneLess.isEmpty && insertConfirmed) {
        context.parent ! CopyFinished
        self ! PoisonPill
      } else {
        context.become(copying(oneLess, insertConfirmed))
      }
  }

}

object BinaryTreeNode {

  trait Position

  case object Left extends Position

  case object Right extends Position

  case class CopyTo(treeNode: ActorRef)

  /**
    * Acknowledges that a copy has been completed. This message should be sent
    * from a node to its parent, when this node and all its children nodes have
    * finished being copied.
    */
  case object CopyFinished

  def props(elem: Int, initiallyRemoved: Boolean) = Props(classOf[BinaryTreeNode], elem, initiallyRemoved)
}
