import model._
import utill.Helper._
import utill.Reader._

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

   /**
     * Question4 :
     * En fazla tag veren kişinin en sevdiği ve en sevmediği türler hangi yıllardadır?
     * (519 adet tag’le en fazla tag yapan x id’li kullanıcının en yüksek puan verdiği yıl
     * 1985 yılı aksiyon filmleridir, en az puan verdiği yıl 2000 yılı romantik komedi filmleridir)

     * Solution4
    */

   val tagLines: Seq[String] = readFromFile("tags.csv")
   //tagLines.take(10).foreach(println)

   val tags: Seq[Tag] = tagLines.map(parseTags)

   // En çok tag veren kişi
   val taggerKingMax = tags.groupBy(_.userId)
     .map{case (userId, tags) => (userId, tags.length)}
     .toList
     .sortBy(_._2)
     .reverse
     .head
   println(taggerKingMax)

   // En fazla tag yapan kisinin verdiği filmleri bulduk
   val ratingsOfTaggerKingMax = ratings
     .filter(_.userId == taggerKingMax._1)
     .map(r => (r.userId,r.movieId))
     .toMap
   ratingsOfTaggerKingMax.take(10).foreach(println)

   // Tüm filmlerin id,year,genre bulduk
   val genreYearMovieIdTuple = movies.flatMap(m => m.genres.map(g => (g,m.year,m.id)))
   genreYearMovieIdTuple.take(10).foreach(println)

   val genreYearAndRatings = genreYearMovieIdTuple
     .filter{case(_, _, movieId) => !ratingsOfTaggerKingMax.get(movieId).isEmpty }
     .map{case (genre, year, movieId) => (genre, year, ratingsOfTaggerKingMax.get(movieId).getOrElse(0d) ) }
     .groupMap(i => (i._1, i._2))(i => i._3)

   println("---------")
   genreYearAndRatings.take(20).foreach(println)
   println("----------")

   val avgRatingByGenreYear = genreYearAndRatings
     .map{x => (x._1._1,x._1._2, x._2.sum / x._2.length.toDouble) }
     .toList
     .sortBy{case (_, _, avgRating) => avgRating}

   avgRatingByGenreYear.take(10).foreach(println)

   val mostRatedYearAndGenre = avgRatingByGenreYear.reverse.head
   println(mostRatedYearAndGenre)

   val leastMovieCountByGenre = avgRatingByGenreYear.head
   println(s"${taggerKingMax._2} tag ile ${taggerKingMax._1} id ' li kullanıcının en yüksek puan verdiği yıl" +
   s"${mostRatedYearAndGenre._2} yılı ${mostRatedYearAndGenre._1} filmlerdir" +
   s"en düşük puan verdiği yıl ${leastMovieCountByGenre._2} yılı ${leastMovieCountByGenre._1} filmlerdir.")

  }
}
