package kvstore

import akka.actor.{Actor, Props}

import scala.util.Random

object Persistence {

  case class Persist(key: String, valueOption: Option[String], id: Long)

  case class Persisted(key: String, id: Long)

  class PersistenceException extends Exception("Persistence failure")

  def props(flaky: Boolean): Props = Props(classOf[Persistence], flaky)
}

class Persistence(flaky: Boolean) extends Actor {

  import Persistence._

  def receive = {
    case Persist(key, _, id) =>
      if (flaky && !Random.nextBoolean()) {
        throw new PersistenceException
      } else {
        sender ! Persisted(key, id)
      }
  }

}
