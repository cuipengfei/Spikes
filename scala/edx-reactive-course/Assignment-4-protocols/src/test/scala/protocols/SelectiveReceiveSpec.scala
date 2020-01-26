package protocols

import org.junit.Assert._
import org.junit.Test
import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors._
import akka.actor.typed.scaladsl._
import akka.actor.testkit.typed.scaladsl.{BehaviorTestKit, TestInbox}
import org.scalacheck.Gen
import org.scalacheck.Prop._

import scala.reflect.ClassTag

trait SelectiveReceiveSpec {

  def behavior[T: ClassTag](inbox: TestInbox[T], size: Int, seq: List[T]) =
    SelectiveReceive(size, expectOne(inbox, seq))

  def expectOne[T](inbox: TestInbox[T], seq: List[T]): Behavior[T] =
    seq match {
      case x :: xs =>
        receiveMessagePartial {
          case `x` =>
            inbox.ref ! x
            expectOne(inbox, xs)
        }
      case Nil => Behaviors.ignore
    }

  def expectStart[T](inbox: TestInbox[T], start: T, followUp: Behavior[T]): Behavior[T] =
    receiveMessagePartial {
      case x @ `start` =>
        inbox.ref ! x
        followUp
    }

  @Test def `A SelectiveReceive Decorator must eventually execute the behavior`(): Unit = {
    val values = List("A", "B", "C")
    val abc = Gen.oneOf(values)
    val abcs = Gen.choose(0, 30).flatMap(Gen.listOfN(_, abc))

    Util.assertPropPassed(forAll(abcs) { list =>
      val i = TestInbox[String]()
      val b = behavior(i, 30, values)
      val testkit = BehaviorTestKit(b, "eventually execute")
      list.foreach(value => {
        testkit.ref ! value
        testkit.runOne()
      })
      val delivered = i.receiveAll()
      assertEquals(delivered.sorted, delivered)
      values.foldLeft((passed, true)) { case ((prevProp, prev), v) =>
        val contained = prev && list.contains(v)
        val deliveredCount =
          (delivered.count(_ == v) == (if (contained) 1 else 0)) :| s"testing for $v when list=$list and delivered=$delivered: "
        (prevProp && deliveredCount, contained)
      }._1
    })
  }

  @Test def `A SelectiveReceive Decorator must tolerate worst-case sorting`(): Unit = {
    val values = (1 to 4).toList
    val i = TestInbox[Int]()
    val b = behavior(i, 3, values)
    val testkit = BehaviorTestKit(b, "worst-case sorting")
    values.reverse.foreach(value => {
      testkit.ref ! value
      testkit.runOne()
    })
    assertEquals(values, i.receiveAll())
  }

  @Test def `A SelectiveReceive Decorator must overflow (size 0)`(): Unit = {
    val values = List(1, 2)
    val i = TestInbox[Int]()
    val b = behavior(i, 0, values)
    val testkit = BehaviorTestKit(b, "overflow 0")
    testkit.ref ! 2
    try {
      testkit.runOne()
      fail("StashOverflowException should have been thrown")
    } catch {
      case _: StashOverflowException => () // OK
    }
  }

  @Test def `A SelectiveReceive Decorator must overflow (size 1)`(): Unit = {
    val values = List(1, 2)
    val i = TestInbox[Int]()
    val b = behavior(i, 1, values)
    val testkit = BehaviorTestKit(b, "overflow 1")
    testkit.ref ! 2
    testkit.runOne()
    testkit.ref ! 2
    try {
      testkit.runOne()
      fail("StashOverflowException should have been thrown")
    } catch {
      case _: StashOverflowException => () // OK
    }
  }

  @Test def `A SelectiveReceive Decorator must try in receive order`(): Unit = {
    val i = TestInbox[Int]()
    val b = SelectiveReceive(2, expectStart(i, 0,
      receiveMessage[Int] { t =>
        i.ref ! t
        same
      }
    ))
    val testkit = BehaviorTestKit(b, "receive order")
    testkit.ref ! 1
    testkit.runOne()
    testkit.ref ! 2
    testkit.runOne()
    assertEquals(Seq(), i.receiveAll())
    testkit.ref ! 0
    testkit.runOne()
    assertEquals(Seq(0, 1, 2), i.receiveAll())
  }

  @Test def `A SelectiveReceive Decorator must restart retrying at the head of the queue`(): Unit = {
    // hint: only the first parameter list participates in equality checking
    case class Msg(cls: Int)(val value: Int)

    val i = TestInbox[Msg]()
    val b = SelectiveReceive(3,
      expectStart(i, Msg(0)(0),
        expectStart(i, Msg(1)(0),
          receiveMessage[Msg] { t =>
            i.ref ! t
            same
          }
        )))
    val testkit = BehaviorTestKit(b, "receive order")
    testkit.ref ! Msg(2)(2)
    testkit.runOne()
    testkit.ref ! Msg(1)(1)
    testkit.runOne()
    testkit.ref ! Msg(2)(3)
    testkit.runOne()
    assertEquals(Seq(), i.receiveAll())
    testkit.ref ! Msg(0)(0)
    testkit.runOne()
    assertEquals(Seq(0, 1, 2, 3), i.receiveAll().map(_.value))
  }

  @Test def `A SelectiveReceive Decorator must still stash unhandled messages after some messages have been handled`(): Unit = {
    val i = TestInbox[Char]()
    val b = SelectiveReceive(1, expectStart(i, 'a', expectStart(i, 'a', expectStart(i, 'z', Behaviors.ignore))))
    val testkit = BehaviorTestKit(b)
    testkit.ref ! 'z'
    testkit.runOne()
    assertEquals(Seq(), i.receiveAll()) // “z” has been stashed
    testkit.ref ! 'a'
    testkit.runOne()
    assertEquals(Seq('a'), i.receiveAll()) // “a” has been handled, and “z” has been stashed again
    testkit.ref ! 'a'
    testkit.runOne()
    // “a” and the initial “z” have been handled
    assertEquals(
      "The initial message 'z' has not been handled, eventually. Make sure unstashed messages are interpreted by a behavior wrapped in an interceptor.",
      Seq('a', 'z'),
      i.receiveAll()
    )
  }

}
