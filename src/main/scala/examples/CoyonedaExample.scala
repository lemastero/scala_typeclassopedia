package examples

import educational.category_theory.kan.Coyoneda

object CoyonedaExample extends App { // TODO change into unit tests
  val s = Set(1, 2, 3, 4, 5)
  val s2 = Coyoneda.liftCoyoneda(s)
  val s3 = Coyoneda.coyoFunctor.map(s2)(_ % 2) // mapping over set
  println(s3)
}
