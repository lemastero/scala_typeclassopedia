package educational.optics

case class Prism[+A,-B,-S,+T](
  matsh: S => Either[T,A],
  build: B => T
)

object PrismExamples {

  def optionPrism[A,B]: Prism[A,B,Option[A],Option[B]] = {
    val upcast: B => Option[B] = Some.apply
    val downCast: Option[A] => Either[Option[B],A] = {
      case Some(a) => Right(a)
      case None => Left(None)
    }
    Prism[A,B,Option[A],Option[B]](
      matsh = downCast,
      build = upcast
    )
  }

  def intToDoublePrism: Prism[Int,Int,Double,Double] = {
    val asDouble: Int => Double = _.toDouble
    val asInt: Double => Either[Double,Int] = d => {
      if(d.isValidInt) Right(d.toInt)
      else Left(d)
    }
    Prism[Int,Int,Double,Double](
      matsh = asInt,
      build = asDouble
    )
  }
}
