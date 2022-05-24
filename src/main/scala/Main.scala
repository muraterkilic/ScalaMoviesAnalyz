import utill.Helper.findNumberOfGenresByYear
import utill.Reader.{parseMovie, parseRRatings, readFromFile}

import java.text.SimpleDateFormat

object Main {
  def main(args: Array[String]): Unit = {

    /**
     * Question1 :
     * Yıllara ve türlere () göre film sayıları nedir ? (1985 korku filmi x adet, aksiyon filmi y adet, 1986 korku filmi x' adet, aksiyon filmi y' adet gibi)
    */

    /**
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
    ratingLines.take(10).foreach(println)

    val rating: Seq[String] = ratingLines.map(parseRRatings)

    /**
     * Question3 :
     * Total çekilen film adetlerine göre en az çekilen tür "romantik komedidir". Toplam "1000000" adet çekilen film arasında "13" film romantik komedidir.
     * */

    /**
     * Solution3
     */

  }
}
