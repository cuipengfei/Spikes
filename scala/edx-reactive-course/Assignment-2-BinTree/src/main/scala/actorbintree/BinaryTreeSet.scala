/**
  * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
  */
package actorbintree

import actorbintree.BinaryTreeNode.{CopyFinished, CopyTo}
import akka.actor._

import scala.collection.immutable.Queue

class BinaryTreeSet extends Actor with Stash {

  import BinaryTreeSet._

  def createRoot() = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = false))

  var root: ActorRef = createRoot()

  var pendingQueue: Queue[Operation] = Queue.empty[Operation]

  def receive: Receive = normal

  val normal: Receive = {
    case op: Operation => root ! op //regular operations, just need to forward to root
    case GC =>
      val newRoot = createRoot()
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
      unstashAll()
      context.become(normal)
    case _ => stash() //queue all regular ops
  }

  def replayQueue(queue: Queue[Operation]): Unit = {
    if (queue.isEmpty) {
      this.pendingQueue = queue //stop, exit
    } else {
      val (head, tail) = queue.dequeue
      root ! head
      replayQueue(tail)
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
