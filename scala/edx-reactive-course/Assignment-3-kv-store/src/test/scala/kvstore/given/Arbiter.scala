/**
 * Copyright (C) 2015 Typesafe Inc. <http://www.typesafe.com>
 */
package kvstore.given

import akka.actor.{ ActorRef, Actor }
import scala.util.Random
import akka.actor.Props

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
        replicas += (if (lossy) context.actorOf(Props(classOf[LossyTransport], sender)) else sender)
        sender ! JoinedSecondary
        audit ! JoinedSecondary
      }
      leader foreach (_ ! Replicas(replicas))
  }

}

class LossyTransport(target: ActorRef) extends Actor {
  val rnd = new Random
  var dropped = 0
  def receive = {
    case msg =>
      if (dropped > 2 || rnd.nextFloat < 0.9) {
        dropped = 0
        target forward msg
      } else {
        dropped += 1
      }
  }
}
