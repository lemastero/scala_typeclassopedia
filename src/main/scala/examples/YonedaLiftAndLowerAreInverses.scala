package examples

import cats.free.Yoneda
import cats.implicits._

/**
  * Example show that Yoneda.run (in Haskell lowerYoneda) is
  * an inverse to  Yoneda.apply (in Haskell liftYoneda)
  *
  * from excellent talk by: Alissa Pajer
  * How Haskell is Changing my Brain - Yay Yoneda
  * https://vimeo.com/96639840
  */
object YonedaLiftAndLowerAreInverses
  extends App {

  val liftYonedaOpt = Yoneda(Option(true))
  val lowerYonedaOpt = liftYonedaOpt.run // Some(true)
  println(lowerYonedaOpt)


  val liftYonedaList = Yoneda(List(1,2,3))
  val lowerYonedaList = liftYonedaList.run // List(1, 2, 3)
  println(lowerYonedaList)
}
