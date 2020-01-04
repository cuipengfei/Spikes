/**
  * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
  */
package actorbintree

import actorbintree.BinaryTreeNode.{CopyFinished, CopyTo}
import akka.actor._

import scala.collection.immutable.Queue

class BinaryTreeSet extends Actor {

  import BinaryTreeSet._

  def createRoot: ActorRef = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = false))

  var root: ActorRef = createRoot

  var pendingQueue: Queue[Operation] = Queue.empty[Operation]

  def receive: Receive = normal

  val normal: Receive = {
    case Insert(req, id, elem) => root ! Insert(req, id, elem)
    case Contains(req, id, elem) => root ! Contains(req, id, elem)
    case Remove(req, id, elem) => root ! Remove(req, id, elem)
    case GC =>
      val newRoot = createRoot
      root ! CopyTo(newRoot)
      context.become(garbageCollecting(newRoot))
  }

  /** Handles messages while garbage collection is performed.
    * `newRoot` is the root of the new binary tree where we want to copy
    * all non-removed elements into.
    */
  def garbageCollecting(newRoot: ActorRef): Receive = {
    case CopyFinished =>
      this.root = newRoot
      replayQueue(pendingQueue)
      context.become(normal)
    case Insert(req, id, elem) => pendingQueue = pendingQueue.enqueue(Insert(req, id, elem))
    case Contains(req, id, elem) => pendingQueue = pendingQueue.enqueue(Contains(req, id, elem))
    case Remove(req, id, elem) => pendingQueue = pendingQueue.enqueue(Remove(req, id, elem))
  }

  def replayQueue(queue: Queue[Operation]): Unit = {
    if (queue.nonEmpty) {
      val (head, tail) = queue.dequeue
      root ! head
      replayQueue(tail)
    } else {
      this.pendingQueue = queue
    }
  }

}


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
