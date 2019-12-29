package async.forBlog

import java.util.{Timer, TimerTask}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Promise
import scala.util.{Failure, Random, Success, Try}

object CallBackStyle_Vs_FutureStyle {

  type CallBack = Try[String] => Unit
  type CallBackBasedFunction = (CallBack) => Unit

  def pretendCallAPI(callBack: CallBack, okMsg: String, failedMsg: String) = {
    val task = new TimerTask {
      override def run() = {
        val percentage = Random.between(1, 100)

        if (percentage >= 50)
          callBack(Success(okMsg))
        else if (percentage <= 30)
          callBack(Failure(new Exception(failedMsg)))
        else
          callBack(Failure(new Exception("network problem")))
      }
    }

    new Timer().schedule(task, Random.between(200, 500))
  }

  val searchTB = pretendCallAPI(_, "product price found", "product not listed")

  val buyFromTB = pretendCallAPI(_, "product bought", "can not buy, no money left")

  def futurize(f: CallBackBasedFunction) = () => {
    val promise = Promise[String]()

    f {
      case Success(msg) => promise.success(msg)
      case Failure(err) => promise.failure(err)
    }

    promise.future
  }

  val searchTBFutureVersion = futurize(searchTB)

  val buyFromTBFutureVersion = futurize(buyFromTB)

  def main(args: Array[String]): Unit = {

    def searchPriceThenBuy() = {
      searchTB {
        case Success(searchMsg) =>
          println(searchMsg)
          buyFromTB {
            case Success(buyMsg) => println(buyMsg)
            case Failure(err) => println(err.getMessage)
          }
        case Failure(err) => println(err.getMessage)
      }
    }

    def searchPriceThenBuyFutureVersion() = {
      val eventualResult = for {
        searchResult <- searchTBFutureVersion().map(msg => println(msg))
        buyResult <- buyFromTBFutureVersion().map(msg => println(msg))
      } yield (searchResult, buyResult)

      eventualResult.onComplete {
        case Failure(err) => println(err.getMessage)
        case _ =>
      }
    }

    //    searchPriceThenBuy()
    searchPriceThenBuyFutureVersion()
  }

}
