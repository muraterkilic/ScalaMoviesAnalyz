import model.Rating
import utill.Helper.{findGoldenYearOfCinema, findLeastMovieCountByGenre, findNumberOfGenresByYear}
import utill.Reader.{parseMovie, parseRatings, readFromFile}

import java.text.SimpleDateFormat

object Main {
  def main(args: Array[String]): Unit = {
   /**
     * Question1 :
     * Yıllara ve türlere () göre film sayıları nedir ? (1985 korku filmi x adet,
     * aksiyon filmi y adet, 1986 korku filmi x' adet, aksiyon filmi y' adet gibi)

     * Solution1
     */

    val movieLines: Seq[String] = readFromFile("movies.csv")
    // movieLines.take(30).foreach(println)

    val movies = movieLines.map(parseMovie)
    // movies.take(40).foreach(println)

    val numberOfGenresByYear = findNumberOfGenresByYear(movies)
    // numberOfGenresByYear.take(50).foreach(println)


    /**
      * Question2 :
      * Rating puanlarına göre en fazla yüksek puan verilen yıl hangisidir (sinemanın altın yılı)
      * movie ile joinle
      * timestamp 1 yıl'a göre çevirip tek case class'ta hallet.

     * Solution2
     */

    val ratingLines: Seq[String] = readFromFile("ratings.csv")
    //ratingLines.take(10).foreach(println)

    println("------")
    val ratings: Seq[Rating] = ratingLines.map(parseRatings)
    //ratings.take(20).foreach(println)

    println("------")
    val goldenYearOfCinema = findGoldenYearOfCinema(ratings)
   //println(goldenYearOfCinema)

    /**
      * Question3 :
      * Total çekilen film adetlerine göre en az çekilen tür "romantik komedidir".
      * Toplam "1000000" adet çekilen film arasında "13" film romantik komedidir.

     * Solution3
     */

    val LeastMovieCountByGenre = findLeastMovieCountByGenre(movies)

    println(s"Total Movie:  ${movies.length},Genre: ${LeastMovieCountByGenre.head._1}, Count: ${LeastMovieCountByGenre.head._2}")
  }
}
