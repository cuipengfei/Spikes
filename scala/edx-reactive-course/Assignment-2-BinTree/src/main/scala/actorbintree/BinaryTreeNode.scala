package actorbintree

import akka.actor.{Actor, ActorRef, Props}

class BinaryTreeNode(val elem: Int, initiallyRemoved: Boolean) extends Actor {

  import BinaryTreeNode._
  import BinaryTreeSet._

  var subtrees: Map[Position, ActorRef] = Map[Position, ActorRef]()
  var removed: Boolean = initiallyRemoved

  // optional
  def receive: Receive = normal

  // optional

  /** Handles `Operation` messages and `CopyTo` requests. */
  val normal: Receive = {

    case Insert(req, id, newElem) =>
      if (newElem > elem) insert(req, id, newElem, Right)
      else if (newElem < elem) insert(req, id, newElem, Left)
      else req ! OperationFinished(id)

    case Contains(req, id, elem) =>
      if (elem == this.elem) req ! ContainsResult(id, !removed)
      else if (elem > this.elem) contains(req, id, elem, Right)
      else contains(req, id, elem, Left)

    case Remove(req, id, elem) =>
      if (elem == this.elem) {
        this.removed = true
        req ! OperationFinished(id)
      }
      else if (elem > this.elem) remove(req, id, elem, Right)
      else remove(req, id, elem, Left)

    case _ => ???
  }

  def remove(req: ActorRef, id: Int, elem: Int, pos: Position) = {
    subtrees.get(pos) match {
      case Some(subTree) => subTree ! Remove(req, id, elem)
      case None => req ! OperationFinished(id)
    }
  }

  private def contains(req: ActorRef, id: Int, elem: Int, pos: Position) = {
    subtrees.get(pos) match {
      case Some(subTree) => subTree ! Contains(req, id, elem)
      case None => req ! ContainsResult(id, false)
    }
  }

  private def insert(req: ActorRef, id: Int, newElem: Int, pos: Position): Unit = {
    subtrees.get(pos) match {
      case Some(subTree) => subTree ! Insert(req, id, newElem)
      case None =>
        subtrees = subtrees + (pos -> createNode(newElem))
        req ! OperationFinished(id)
    }
  }

  def createNode(elem: Int): ActorRef = context.actorOf(BinaryTreeNode.props(elem, initiallyRemoved = false))

  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = ???


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