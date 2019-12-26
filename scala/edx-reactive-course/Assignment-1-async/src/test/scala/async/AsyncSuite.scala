package async

import org.junit.Assert._
import org.junit.Test

import java.util.concurrent.atomic.AtomicInteger

import scala.concurrent.duration.DurationInt
import scala.concurrent.{Await, Future, Promise, TimeoutException}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Random, Success, Try}

class AsyncSuite {

  import Async._

  /**
    * Returns a function that performs an asynchronous
    * computation returning the given result after 50 milliseconds.
    */
  def delay[A](result: Try[A]): () => Future[A] = {
    val t = new java.util.Timer()
    () => {
      val p = Promise[A]()
      val task = new java.util.TimerTask {
        def run(): Unit = {
          p.complete(result)
          ()
        }
      }
      t.schedule(task, 200 /* milliseconds */)
      p.future
    }
  }

  @Test def `transformSuccess should transform successful computations`(): Unit = {
    val x = Random.nextInt()
    val eventuallyResult =
      transformSuccess(Future.successful(x))
    val result =
      Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(Success(x % 2 == 0), result)
  }

  @Test def `transformSuccess should propagate the failure of a failed computation`(): Unit = {
    val failure = new Exception("Failed asynchronous computation")
    val eventuallyResult =
      transformSuccess(Future.failed(failure))
    val result =
      Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(Failure(failure), result)
  }

  @Test def `recoverFailure should recover from failed computations`(): Unit = {
    val eventuallyResult =
      recoverFailure(Future.failed(new Exception))
    val result = Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(Success(-1), result)
  }

  @Test def `recoverFailure should propagate successful computations`(): Unit = {
    val x = Random.nextInt()
    val eventuallyResult =
      recoverFailure(Future.successful(x))
    val result = Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(Success(x), result)
  }

  @Test def `sequenceComputations should start the second computation after the first has completed (2pts)`(): Unit = {
    try {
      val eventuallyResult =
        sequenceComputations(delay(Success(1)), delay(Success(2)))
      Await.ready(eventuallyResult, 250.milliseconds)
      fail("Asynchronous computations finished too early")
    } catch {
      case _: TimeoutException =>
        ()
    }
  }

  @Test def `sequenceComputations should not start the second computation if the first has failed (2pts)`(): Unit = {
    val counter = new AtomicInteger(0)
    val eventuallyResult =
      sequenceComputations(
        () => Future.failed(new Exception),
        () => Future.successful { counter.incrementAndGet(); () }
      )
    Await.ready(eventuallyResult, 100.milliseconds)
    assertEquals(0, counter.get())
  }

  @Test def `concurrentComputations should start both computations independently of each other’s completion (2pts)`(): Unit = {
    try {
      val eventuallyResult =
        concurrentComputations(delay(Success(1)), delay(Success(2)))
      Await.ready(eventuallyResult, 350.milliseconds)
      ()
    } catch {
      case _: TimeoutException =>
        fail("Asynchronous computations took too much time")
    }
  }

  @Test def `insist should not retry successful computations (3pts)`(): Unit = {
    val counter = new AtomicInteger(0)
    val eventuallyResult =
      insist(() => Future { counter.incrementAndGet() }, maxAttempts = 3)
    Await.ready(eventuallyResult, 100.milliseconds).value.get
    assertEquals(1, counter.get())
  }

  @Test def `insist should retry failed computations (3pts)`(): Unit = {
    val counter = new AtomicInteger(0)
    val eventuallyResult =
      insist(
        () => Future { counter.incrementAndGet(); throw new Exception },
        maxAttempts = 3
      )
    Await.ready(eventuallyResult, 200.milliseconds).value.get
    assertEquals(3, counter.get())
  }

  @Test def `futurize should handle successful computation results (3pts)`(): Unit = {
    val success = Success(Random.nextInt())
    val succeeding = new CallbackBasedApi {
      def computeIntAsync(continuation: Try[Int] => Unit): Unit = continuation(success)
    }
    val eventuallyInt = futurize(succeeding).computeIntAsync()
    Await.ready(eventuallyInt, 200.milliseconds)
    assertEquals(success, eventuallyInt.value.get)
  }

  @Test def `futurize should handle failed computation results (3pts)`(): Unit = {
    val failure = Failure(new Exception("Oops"))
    val failing = new CallbackBasedApi {
      def computeIntAsync(continuation: Try[Int] => Unit): Unit = continuation(failure)
    }
    val eventuallyInt = futurize(failing).computeIntAsync()
    Await.ready(eventuallyInt, 200.milliseconds)
    assertEquals(failure, eventuallyInt.value.get)
  }
}
