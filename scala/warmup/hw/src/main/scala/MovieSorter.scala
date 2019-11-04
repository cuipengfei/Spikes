import scalaj.http.Http

object MovieSorter {


  def takeTop(movies: List[Movie], topN: Int): List[Movie] = {
    movies.sortBy(movie => movie.rating).reverse.take(topN)

  }

  def takeTopM(movies: List[Movie], topN: Int, results: List[Movie] = Nil): List[Movie] = {
    if (topN == 0) results
    else {
      val movie = movies.maxBy(m => m.rating)
      takeTopM(movies.diff(List(movie)), topN - 1, results :+ movie)
    }
  }

  def csvToMovies(csvString: String): List[Movie] = {
    val linesOfCSV = csvString.split(sys.props("line.separator"))

    linesOfCSV.tail.toList.map(line => {
      val values = line.split(",")
      Movie(values.head, values.last.toDouble)
    })

  }


  def fetchFrom(url: String): String =
    Http(url).asString.body


  def main(args: Array[String]): Unit = {
    //    val url = "https://gist.githubusercontent.com/CatTail/18695526bd1adcc21219335f23ea5bea/raw/54045ceeae6a508dec86330c072c43be559c233b/movies.csv"
    //
    //    val csvStr = fetchFrom(url)
    //    val movies = csvToMovies(csvStr)
    //    val topTen = takeTopM(movies, 50)
    //
    //    print(topTen)

    val lz: Seq[Int] = List(9, 4, 6, 2, 3, 13, 6).to(LazyList)
    val rst = lz.sortBy(i => {
      println(s"called $i")
      i
    }).reverse.take(2)

    println(rst.toList)
  }

}
