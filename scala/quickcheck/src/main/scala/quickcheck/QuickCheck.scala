package quickcheck

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen.{const, oneOf}
import org.scalacheck.Prop._
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] =
    for {
      elem <- arbitrary[A]
      heap <- oneOf(const(empty), genHeap)
    } yield insert(elem, heap)

  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  property("sorted insert should succeed") = forAll { (l: List[A]) =>
    val heap = l.foldRight(empty)(insert)
    asList(heap) == l.sorted
  }

  def asList(h: H): List[A] = // the sorted list resulting from extracting all elements of h
    if (isEmpty(h)) List() else findMin(h) :: asList(deleteMin(h))

}
