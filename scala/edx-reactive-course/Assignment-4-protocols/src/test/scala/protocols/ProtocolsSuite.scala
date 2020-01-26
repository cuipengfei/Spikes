package protocols

import org.junit.Assert.fail
import org.scalacheck.{Prop, Test}

class ProtocolsSuite extends SelectiveReceiveSpec with TransactorSpec

object Util {
  // Convenient method that turns a scalacheck Prop into a JUnit assertion
  def assertPropPassed(prop: Prop): Unit = {
    val _ = Test.check(prop)(_.withTestCallback(new Test.TestCallback {
      override def onTestResult(name: String, result: Test.Result): Unit = {
        result.status match {
          case Test.Failed(_, labels) => fail("Property failed." + labels.mkString("\n", "\n", ""))
          case Test.PropException(_, t, labels) => fail(t.toString + labels.mkString("\n", "\n", ""))
          case _ => () // OK
        }
      }
    }))
  }
}
