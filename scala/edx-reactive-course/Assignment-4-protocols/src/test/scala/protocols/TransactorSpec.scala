package protocols

import org.junit.Assert._
import org.junit.Test

import akka.Done
import org.scalacheck.Gen
import org.scalacheck.Arbitrary.arbitrary
import org.scalacheck.Prop._
import akka.actor.typed._
import akka.actor.testkit.typed.Effect
import akka.actor.testkit.typed.scaladsl._

import scala.concurrent.duration._

trait TransactorSpec {

  import Transactor._

  @Test def `A Transactor must compute`(): Unit = {
    val i = TestInbox[Int]()
    val done = TestInbox[String]()

    var lastSerial = 1L
    val serial = Gen.const(0) map { _ =>
      lastSerial += 1
      lastSerial
    }
    val extract = Gen.const(Extract[Int, Int](identity, i.ref))
    val map = Gen.oneOf(
      Gen.zip(arbitrary[Int], serial).map {
        case(x, id) => Modify[Int, String](x + _, id, s"add $x", done.ref)
      },
      Gen.zip(arbitrary[Int], serial).map {
        case(x, id) => Modify[Int, String](x * _, id, s"times $x", done.ref)
      }
    )
    val op = Gen.oneOf(extract, map)
    val ops = Gen.listOf(op)

    Util.assertPropPassed(forAll(ops) { list =>
      val start = 1
      val testkit = BehaviorTestKit(Transactor(start, 3.seconds))

      val sessionInbox = TestInbox[ActorRef[Session[Int]]]()
      testkit.ref ! Begin(sessionInbox.ref)
      testkit.runOne()
      val session = testkit.childTestKit(sessionInbox.receiveMessage())

      val end = list.foldLeft(start) { (current, op) =>
        session.ref ! op
        session.runOne()
        op match {
          case Extract(_, _) =>
            assertEquals(Seq(current), i.receiveAll())
            current
          case Modify(f, _, reply, _) =>
            assertEquals(Seq(reply), done.receiveAll())
            f(current)
          case _ => current
        }
      }
      session.ref ! Extract[Int, Int](identity, i.ref)
      session.runOne()
      i.receiveAll() == Seq(end)
    })
  }

  @Test def `A Transactor must commit`(): Unit = {
    val done = TestInbox[String]()

    val start = 1
    val sessionTimeout = 3.seconds
    val testkit = BehaviorTestKit(Transactor(start, sessionTimeout))

    val sessionInbox = TestInbox[ActorRef[Session[Int]]]()
    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs = sessionInbox.receiveAll()
    assert(refs.size == 1, "A session should be created upon reception of a `Begin` message")
    val ref = refs.head
    val effects = testkit.retrieveAllEffects()
    assert(
      effects.exists(_.isInstanceOf[Effect.SpawnedAnonymous[_]]),
      "A child actor should be spawned to handle the session"
    )
    assert(
      effects.contains(Effect.WatchedWith(ref, RolledBack(ref))),
      "The actor handling the session should be watched with a `RolledBack` message"
    )
    assert(
      effects.exists { case Effect.Scheduled(`sessionTimeout`, _, RolledBack(`ref`)) => true case _ => false },
      "In case of timeout, the session should be rolled back"
    )
    assertEquals(testkit.childInbox(ref.path.name).ref, ref)
    val session = testkit.childTestKit(ref)

    session.ref ! Modify((_: Int) + 1, 0, "done", done.ref)
    session.runOne()
    assertEquals(Seq("done"), done.receiveAll())
    session.ref ! Commit("committed", done.ref)
    session.runOne()
    assertEquals(Seq("committed"), done.receiveAll())
    session.ref ! Extract((x: Int) => x.toString, done.ref)
    session.runOne()
    assertEquals(Nil, done.receiveAll())

    testkit.runOne()
    assertFalse(testkit.selfInbox.hasMessages)

    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs2 = sessionInbox.receiveAll()
    assert(refs2.size == 1, "A session should be created upon reception of the `Begin` message")
    val ref2 = refs2.head
    assertEquals(testkit.childInbox(ref2.path.name).ref, ref2)
    val session2 = testkit.childTestKit(ref2)

    val i = TestInbox[Int]()
    session2.ref ! Extract((x: Int) => x, i.ref)
    session2.runOne()
    assertEquals(Seq(start + 1), i.receiveAll())
  }

  @Test def `A Transactor must rollback`(): Unit = {
    val done = TestInbox[String]()

    val start = 1
    val testkit = BehaviorTestKit(Transactor(start, 3.seconds).asInstanceOf[Behavior[PrivateCommand[Int]]])

    val sessionInbox = TestInbox[ActorRef[Session[Int]]]()
    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs = sessionInbox.receiveAll()
    assert(refs.size == 1, "A session should be created upon reception of a `Begin` message")
    testkit.retrieveAllEffects() // Clear effects produced by the session creation
    val ref = refs.head
    assertEquals(testkit.childInbox(ref.path.name).ref, ref)
    val session = testkit.childTestKit(ref)

    session.ref ! Modify((_: Int) + 1, 0, "done", done.ref)
    session.runOne()
    assertEquals(Seq("done"), done.receiveAll())
    session.ref ! Rollback()
    session.runOne()
    assertEquals(Nil, done.receiveAll())
    session.ref ! Extract((x: Int) => x.toString, done.ref)
    session.runOne()
    assertEquals(Nil, done.receiveAll())

    assertEquals(Nil, testkit.selfInbox.receiveAll())
    testkit.ref ! RolledBack(ref)
    testkit.runOne()
    assertEquals(Seq(Effects.stopped(ref.path.name)), testkit.retrieveAllEffects())

    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs2 = sessionInbox.receiveAll()
    assert(refs2.size == 1, "A session should be created upon reception of the `Begin` message")
    val ref2 = refs2.head
    assertEquals(testkit.childInbox(ref2.path.name).ref, ref2)
    val session2 = testkit.childTestKit(ref2)

    val i = TestInbox[Int]()
    session2.ref ! Extract((x: Int) => x, i.ref)
    session2.runOne()
    assertEquals(Seq(start), i.receiveAll())
  }

  @Test def `A Transactor must timeout`(): Unit = {
    val done = TestInbox[String]()

    val start = 1
    val testkit = BehaviorTestKit(Transactor(start, 3.seconds).asInstanceOf[Behavior[PrivateCommand[Int]]])

    val sessionInbox = TestInbox[ActorRef[Session[Int]]]()
    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs = sessionInbox.receiveAll()
    assert(refs.size == 1, "A session should be created upon reception of the `Begin` message")
    val ref = refs.head
    testkit.retrieveAllEffects()
    assertEquals(testkit.childInbox(ref.path.name).ref, ref)
    val session = testkit.childTestKit(ref)

    session.ref ! Modify((_: Int) + 1, 0, "done", done.ref)
    session.runOne()
    assertEquals(Seq("done"), done.receiveAll())

    testkit.ref ! RolledBack(ref)
    testkit.runOne()
    assertEquals(Seq(Effects.stopped(ref.path.name)), testkit.retrieveAllEffects())

    // session child actor should be terminated now
    session.ref ! Extract((x: Int) => x.toString, done.ref)
    session.runOne()
    assertEquals(Seq("2"), done.receiveAll()) // BehaviorTestKit does not actually stop the child

    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs2 = sessionInbox.receiveAll()
    assert(refs2.size == 1, "A session should be created upon reception of the `Begin` message")
    val ref2 = refs2.head
    assertEquals(testkit.childInbox(ref2.path.name).ref, ref2)
    val session2 = testkit.childTestKit(ref2)

    val i = TestInbox[Int]()
    session2.ref ! Extract((x: Int) => x, i.ref)
    session2.runOne()
    assertEquals(Seq(start), i.receiveAll())
  }

  @Test def `A Transactor must ignore new sessions while in a session`(): Unit = {
    val start = 1
    val testkit = BehaviorTestKit(Transactor(start, 3.seconds).asInstanceOf[Behavior[PrivateCommand[Int]]])

    val sessionInbox = TestInbox[ActorRef[Session[Int]]]()
    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val refs = sessionInbox.receiveAll()
    assert(refs.size == 1, "A session should be created upon reception of a `Begin` message")
    val ref = refs.head
    assertEquals(testkit.childInbox(ref.path.name).ref, ref)

    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    assertEquals("New sessions should not be created while in a session", Nil, sessionInbox.receiveAll())

    testkit.ref ! RolledBack(ref)
    testkit.runOne()

    val refs2 = sessionInbox.receiveAll()
    assert(refs2.size == 1, "New sessions should eventually be created after the current session is terminated")
    val ref2 = refs2.head
    assertEquals(testkit.childInbox(ref2.path.name).ref, ref2)
  }

  @Test def `A Transactor must have idempotent modifications`(): Unit = {
    val extracted = TestInbox[Int]()
    val done = TestInbox[Done]()

    val start = 1
    val testkit = BehaviorTestKit(Transactor(start, 3.seconds))

    val sessionInbox = TestInbox[ActorRef[Session[Int]]]()
    testkit.ref ! Begin(sessionInbox.ref)
    testkit.runOne()
    val session = testkit.childTestKit(sessionInbox.receiveMessage())

    val modify = Modify[Int, Done](1 + _, 1L, Done, done.ref)
    session.ref ! modify
    session.runOne()
    assertEquals(Seq(Done), done.receiveAll())
    session.ref ! modify
    session.runOne()
    assertEquals(Seq(Done), done.receiveAll())
    session.ref ! Extract[Int, Int](identity, extracted.ref)
    session.runOne()
    assertEquals(Seq(2), extracted.receiveAll())
  }

  @Test def `A Transactor must properly ignore stale termination notices`(): Unit = {
    // the messages resulting from DeathWatch must not accumulate
    val sessionMock = TestInbox[Session[Int]]()
    val testkit = BehaviorTestKit(Transactor(0, 3.seconds))
    (1 to 31).foreach(_ => {
      testkit.ref.unsafeUpcast[PrivateCommand[Int]] ! RolledBack(sessionMock.ref)
      testkit.runOne()
    })
  }

}
