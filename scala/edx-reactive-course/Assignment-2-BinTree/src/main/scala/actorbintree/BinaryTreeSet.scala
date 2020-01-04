/**
  * Copyright (C) 2009-2013 Typesafe Inc. <http://www.typesafe.com>
  */
package actorbintree

import actorbintree.BinaryTreeNode.{CopyFinished, CopyTo}
import akka.actor._

class BinaryTreeSet extends Actor with Stash {

  import BinaryTreeSet._

  def createRoot() = context.actorOf(BinaryTreeNode.props(0, initiallyRemoved = false))

  var root: ActorRef = createRoot()

  def receive: Receive = normal

  val normal: Receive = {
    case op: Operation => root ! op //regular operations, just need to forward to root
    case GC =>
      val newRoot = createRoot()
      context.become(garbageCollecting(newRoot))
      root ! CopyTo(newRoot)
  }

  def garbageCollecting(newRoot: ActorRef): Receive = {
    case CopyFinished =>
      this.root = newRoot
      context.become(normal)
      unstashAll()

    case _ => stash() //queue all regular ops
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
