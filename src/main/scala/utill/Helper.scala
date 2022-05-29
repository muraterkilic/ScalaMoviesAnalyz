package utill

import model.{Movie, Rating, Tag}

object Helper {

  def findNumberOfGenresByYear(movies: Seq[Movie]): List[((Int, String), Int)] = {

    val movieListWithExplodeGenres = movies.flatMap(v => v.genres.map(g => (v.year, g)))
      .groupBy(x => x).map(x => (x._1, x._2.length)).toList
    // movieListWithExplodeGenres.take(10).foreach(println)
    movieListWithExplodeGenres.sortBy(r => r._2).reverse
  }

  def findGoldenYearOfCinema(ratings: Seq[Rating]): Unit ={
    ratings
      .filter(_.rating == 5)
      .groupBy(r => r.year)
      //.take(10).foreach(println)
      .map(x =>(x._1,x._2.length))
      .maxBy(_._2)
  }

  def findLeastMovieCountByGenre(movies: Seq[Movie]): List[(String, Int)]={
    movies.flatMap(v => v.genres.map(gemre => (v.id, gemre)))
      .filter{case(_, genre) => genre != "(no genres listed)"}
      .groupBy(x => x._2)
      .map{ case(movieId, genres) => (movieId,genres.length)}
      .toList
      .sortBy(x => x._2)
      .take(1)
  }
}
