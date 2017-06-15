package quickcheck

import org.junit.runner.RunWith
import org.scalacheck.Prop
import org.scalatest.FunSuite
import org.scalatest.exceptions.TestFailedException
import org.scalatest.junit.JUnitRunner
import org.scalatest.prop.Checkers

@RunWith(classOf[JUnitRunner])
class QuickCheckSuite extends FunSuite with Checkers {
  def checkBogus(p: Prop) {
    var ok = false
    try {
      check(p)
    } catch {
      case e: TestFailedException =>
        ok = true
    }
    assert(ok, "A bogus heap should NOT satisfy all properties. Try to find the bug!")
  }

  test("Binomial heap satisfies properties.") {
    check(new QuickCheckHeap with quickcheck.BinomialHeap)
  }

  test("Bogus (1) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.Bogus1BinomialHeap)
  }

  test("Bogus (2) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.Bogus2BinomialHeap)
  }

  test("Bogus (3) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.Bogus3BinomialHeap)
  }

  test("Bogus (4) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.Bogus4BinomialHeap)
  }

  test("Bogus (5) binomial heap does not satisfy properties.") {
    checkBogus(new QuickCheckHeap with quickcheck.Bogus5BinomialHeap)
  }
}
