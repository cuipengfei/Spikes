package async

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Future, Promise}
import scala.util.{Failure, Success, Try}

object Async extends AsyncInterface {

  /**
    * Transforms a successful asynchronous `Int` computation
    * into a `Boolean` indicating whether the number was even or not.
    * In case the given `Future` value failed, this method
    * should return a failed `Future` with the same error.
    */
  def transformSuccess(future: Future[Int]): Future[Boolean] = {
    future.map(x => x % 2 == 0)
  }

  /**
    * Transforms a failed asynchronous `Int` computation into a
    * successful one returning `-1`.
    * Any non-fatal failure should be recovered.
    * In case the given `Future` value was successful, this method
    * should return a successful `Future` with the same value.
    */
  def recoverFailure(future: Future[Int]): Future[Int] = {
    future.fallbackTo(Future.successful(-1))
  }


  /**
    * Perform two asynchronous computation, one after the other. `makeAsyncComputation2`
    * should start ''after'' the `Future` returned by `makeAsyncComputation1` has
    * completed.
    * In case the first asynchronous computation failed, the second one should not even
    * be started.
    * The returned `Future` value should contain the successful result of the first and
    * second asynchronous computations, paired together.
    */
  def sequenceComputations[A, B](f1: () => Future[A], f2: () => Future[B]): Future[(A, B)] = {
    for {
      a <- f1()
      b <- f2()
    } yield (a, b)
  }

  /**
    * Concurrently perform two asynchronous computations and pair their successful
    * result together.
    * The two computations should be started independently of each other.
    * If one of them fails, this method should return the failure.
    */
  def concurrentComputations[A, B](
                                    f1: () => Future[A],
                                    f2: () => Future[B]
                                  ): Future[(A, B)] = {
    f1().zip(f2())
  }

  /**
    * Attempt to perform an asynchronous computation.
    * In case of failure this method should try again to make
    * the asynchronous computation so that at most `maxAttempts`
    * are eventually performed.
    */
  def insist[A](f: () => Future[A], maxAttempts: Int): Future[A] = {
    if (maxAttempts == 1) { //last time
      f()
    } else {
      f().recoverWith {
        case _ => insist(f, maxAttempts - 1)
      }
    }
  }

  /**
    * Turns a callback-based API into a Future-based API
    *
    * @return A `FutureBasedApi` that forwards calls to `computeIntAsync` to the `callbackBasedApi`
    *         and returns its result in a `Future` value
    *
    *         Hint: Use a `Promise`
    */
  def futurize(callbackBasedApi: CallbackBasedApi): FutureBasedApi = {

    val p = Promise[Int]()
    callbackBasedApi.computeIntAsync {
      case Success(i) => p.success(i)
      case Failure(exception) => p.failure(exception)
    }

    () => p.future
  }

}

/**
  * Dummy example of a callback-based API
  */
trait CallbackBasedApi {
  def computeIntAsync(continuation: Try[Int] => Unit): Unit
}

/**
  * API similar to [[CallbackBasedApi]], but based on `Future` instead
  */
trait FutureBasedApi {
  def computeIntAsync(): Future[Int]
}
