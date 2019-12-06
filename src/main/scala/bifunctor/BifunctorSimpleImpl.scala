package bifunctor

object BifunctorSimpleImpl extends App {
  import educational.BifunctorInstances._

  val toUpper: Char => Char = _.toUpper
  val add1: Int => Int = _ + 1
  println( tupleBifunctor.bimap(toUpper, add1)( ('j', 3) ) ) // (43, 'J')
  println( eitherBifunctor.bimap(toUpper, add1)(Left('j')) )  // Left('J')
  println( eitherBifunctor.bimap(toUpper, add1)(Right(3)) )  // Right(43)

  println( tupleBifunctor.first(toUpper)( ('j', 3) ) ) // (42, 'j')
  println( eitherBifunctor.first(toUpper)(Left('j')) )

  println( tupleBifunctor.second(add1)( (3, 'j') ) ) // (42, 'j')
  println( eitherBifunctor.second(add1)(Left('j')) )
  println( eitherBifunctor.second(add1)(Right(42)) )
}