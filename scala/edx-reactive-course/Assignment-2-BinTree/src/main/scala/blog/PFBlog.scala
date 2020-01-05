package blog

object PFBlog {

  def usePF(pf: PartialFunction[Option[Int], Int]) = {
    pf(Some(11))
  }

  usePF {
    case Some(x) => x + 1
    case None => 0
  }
}
