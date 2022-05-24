package utill

import model.Movie

object Helper {

  def findNumberOfGenresByYear(movies: Seq[Movie]): List[((Int, String), Int)] = {

    val movieListWithExplodeGenres = movies.flatMap(v => v.genres.map(g => (v.year, g)))
      .groupBy(x => x).map(x => (x._1, x._2.length)).toList
    // movieListWithExplodeGenres.take(10).foreach(println)
    movieListWithExplodeGenres.sortBy(r => r._2).reverse
  }
}
