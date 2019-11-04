import org.scalatest.{FlatSpec, Matchers}

class MovieRatingSortTest extends FlatSpec with Matchers {

  "movie rating sorter" should "be able to fetch csv file from url" in {
    // mock http?
    val url = "https://gist.githubusercontent.com/CatTail/18695526bd1adcc21219335f23ea5bea/raw/54045ceeae6a508dec86330c072c43be559c233b/movies.csv"

    val csvBody = MovieSorter.fetchFrom(url)

    csvBody.split(sys.props("line.separator")) should have size 1001
  }

  "movie rating sorter" should "be able to parse csv into objects" in {
    val csvString = "name,running_time,rating\n0,74.0376347426586,4.810865240700383\n1,105.56145504302435,7.172971895254635\n2,123.00650732900348,6.998624003961524"

    val movies: List[Movie] = MovieSorter.csvToMovies(csvString)

    movies should have size 3

    movies.head.name should be("0")
    movies.head.rating should be(4.810865240700383)
  }

  "movie rating sorter" should "be able to sort movies by rating" in {
    val movies = List(Movie("hello", 1.23), Movie("ni hao", 2.34))

    val topMovies: List[Movie] = MovieSorter.takeTopM(movies, 1)

    topMovies should have size 1
    topMovies.head.name should be("ni hao")
  }
}