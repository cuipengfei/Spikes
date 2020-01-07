package TraitSelfTypeBlog

trait User {
  def username: String
}

trait Tweeter {
  self: User =>
  def tweet(tweetText: String) = println(s"$username: $tweetText")
}

class VerifiedTweeter(val username_ : String) {
  def username = s"real $username_"
}

object SelfTypeBlog {
  def main(args: Array[String]): Unit = {
    val realBeyoncé = new VerifiedTweeter("Beyoncé") with User with Tweeter
    realBeyoncé.tweet("Just spilled my glass of lemonade")

    new JNotUser().tweet("hello")
  }
}
