package examples

object BifunctorSimpleImpl extends App { // TODO change into unit tests
  import educational.category_theory.two.bifunctors.BifunctorInstances._

  val toUpper: Char => Char = _.toUpper
  val add1: Int => Int = _ + 1
  println( tupleBifunctor.bimap( ('j', 3) )(toUpper, add1) )
  println( eitherBifunctor.bimap( Left('j') )(toUpper, add1) )
  println( eitherBifunctor.bimap( Right(3) )(toUpper, add1) )

  println( tupleBifunctor.mapLeft( ('j', 3) )(toUpper) )
  println( eitherBifunctor.mapLeft( Left('j'))(toUpper) )

  println( tupleBifunctor.map( ('j',41) )(add1))
  println( eitherBifunctor.map( Left('j') )(add1) )
  println( eitherBifunctor.map( Right(41) )(add1) )
}