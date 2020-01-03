/**
  * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
  */
package actorbintree

import akka.actor._

import scala.collection.immutable.Queue

object BinaryTreeSet {

  trait Operation {
    def requester: ActorRef

    def id: Int

    def elem: Int
  }

  trait OperationReply {
    def id: Int
  }

  case class Insert(requester: ActorRef, id: Int, elem: Int) extends Operation

  case class Contains(requester: ActorRef, id: Int, elem: Int) extends Operation

  case class Remove(requester: ActorRef, id: Int, elem: Int) extends Operation

  case object GC

  case class ContainsResult(id: Int, result: Boolean) extends OperationReply

  case class OperationFinished(id: Int) extends OperationReply

}


class BinaryTreeSet extends Actor {

  import BinaryTreeSet._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = true))

  var root: ActorRef = createRoot

  // optional (used to stash incoming operations during garbage collection)
  var pendingQueue: Queue[Operation] = Queue.empty[Operation]

  // optional
  def receive: Receive = normal

  // optional
  /** Accepts `Operation` and `GC` messages. */
  val normal: Receive = {
    case Insert(req, id, elem) =>
      root ! Insert(req, id, elem)
    case Contains(req, id, elem) =>
      root ! Contains(req, id, elem)
    case _ => ???
  }

  // optional
  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive = ???

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
      if (elem == this.elem) req ! ContainsResult(id, true)
      else if (elem > this.elem) contains(req, id, elem, Right)
      else contains(req, id, elem, Left)

    case _ => ???
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

  def createNode(elem: Int): ActorRef = context.actorOf(BinaryTreeNode.props(elem, initiallyRemoved = true))

  // optional
  /** `expected` is the set of ActorRefs whose replies we are waiting for,
    * `insertConfirmed` tracks whether the copy of this node to the new tree has been confirmed.
    */
  def copying(expected: Set[ActorRef], insertConfirmed: Boolean): Receive = ???


}
