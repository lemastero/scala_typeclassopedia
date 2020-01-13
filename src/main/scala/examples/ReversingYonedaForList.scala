package examples

import cats.free.Yoneda


/**
  * Example 2 show that you could add some invertable transformation to Yoneda
  *
  * from excellent talk by: Alissa Pajer
  * How Haskell is Changing my Brain - Yay Yoneda
  * https://vimeo.com/96639840
  */

case class ReverseIntListYoneda(list: List[Int]) extends Yoneda[List, Int] {
  override def apply[B](f: Int => B): List[B] = list.reverse.map(f)
}

object ReversingYonedaFOrList
  extends App {

  val reverseYo = ReverseIntListYoneda(List(1,2,3))
  val yoneda2 = reverseYo.map(_ + 3)
  println(yoneda2.run)
}
