/**
  * Copyright (C) 2015 Typesafe Inc. <http://www.typesafe.com>
  */
package kvstore.given

import akka.actor.{Actor, ActorRef, Props}

import scala.util.Random

class Arbiter(lossy: Boolean, audit: ActorRef) extends Actor {

  import kvstore.Arbiter._

  var leader: Option[ActorRef] = None
  var replicas = Set.empty[ActorRef]

  def receive = {
    case Join =>
      if (leader.isEmpty) {
        leader = Some(sender)
        replicas += sender
        sender ! JoinedPrimary
        audit ! JoinedPrimary
      } else {
        val secondary =
          if (lossy) context.actorOf(Props(classOf[FakeSecondary], sender))
          else sender

        replicas += secondary
        sender ! JoinedSecondary
        audit ! JoinedSecondary
      }
      leader foreach (_ ! Replicas(replicas))
  }

}

// sometimes, secondary can not receive msg
class FakeSecondary(realSecondary: ActorRef) extends Actor {
  val rnd = new Random
  var dropped = 0

  def receive = {
    case msg =>
      if (dropped > 2 || rnd.nextFloat < 0.9) {
        dropped = 0
        realSecondary forward msg
      } else {
        dropped += 1
      }
  }
}
