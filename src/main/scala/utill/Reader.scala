package utill

import model.{Movie, Rating, Tag}

import java.text.SimpleDateFormat
import scala.io.Source
import scala.util.Try

object Reader {

  def readFromFile(fileName: String): Seq[String] = {

    val file = Source.fromFile(f"data/$fileName")
    val Lines = file.getLines().toList.drop(1)
    file.close
    Lines
  }

  def parseMovie(line: String): Movie ={
    val splinted = line.split(",",2)
    val id = splinted(0).toInt
    val remaining = splinted(1)
    val sp = remaining.lastIndexOf(",")
    val titleDirty = remaining.substring(0,sp)
    val title = if(titleDirty.startsWith("\"")) titleDirty.drop(1).init else titleDirty // Movie Name

    val year = Try(
      title
        .substring(title.lastIndexOf("("), title.lastIndexOf(")"))
        .drop(1)
        .toInt
    ).getOrElse(-1)

    val genres = remaining.substring(sp + 1).split('|').toList
    Movie(id,title,year,genres)
  }

  def parseRatings(line: String): Rating = {

    val df: SimpleDateFormat = new SimpleDateFormat("yyyy")

    line.split(",").toList match {
      case userId :: movieId :: rating :: ts:: Nil =>
        Rating(
          userId.toInt,
          movieId.toInt,
          rating.toDouble,
          df.format(ts.toLong * 1000L)
      )
    }
  }

  def parseTags(Line: String):  Tag ={
    val df: SimpleDateFormat = new SimpleDateFormat("yyyy")

    Line.split(",").toList match {
      case userId :: movieId :: tag :: ts:: Nil =>
        Tag(
          userId.toInt,
          movieId.toInt,
          tag,
          df.format(ts.toLong * 1000L))
    }
  }
}
