package examples

import cats.data.Kleisli
import cats.implicits._

/**
  * First example from cats docs: http://typelevel.org/cats/datatypes/kleisli.html
  */
object KleisliExamples extends App {
  val twice: Int => Int = _ * 2
  val countCats: Int => String = x => if(x ==1) "1 cat" else s"$x cats"
  val twiceAsManyCats = twice andThen countCats
  println(twiceAsManyCats(1))

  val parse: String => Option[Int] = s => try { Some(s.toInt) } catch { case _: NumberFormatException => None }
  val reciprocal: Int => Option[Double] = i => if (i != 0) Some(1.0 / i) else None

  val parseKleisli = Kleisli(parse)
  val reciprocalKleisli = Kleisli(reciprocal)
  val parseAndReciprocal = reciprocalKleisli.compose(parseKleisli)
  val parseAndReciprocal2 = parseKleisli.andThen(reciprocalKleisli)

  println(parseAndReciprocal("10"))
  println(parseAndReciprocal2("10"))
}
