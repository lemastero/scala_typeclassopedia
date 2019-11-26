package bifunctor

import const.Const

import scala.language.higherKinds


object BifunctorSimpleImpl extends App {
  /**
    * Map over both arguments at the same time.
    *
    * class Bifunctor p where
    *   bimap :: (a -> b) -> (c -> d) -> p a c -> p b d
    *   bimap f g = first f . second g
    *
    *   first :: (a -> b) -> p a c -> p b c
    *   first f = bimap f id
    *
    *   second :: (b -> c) -> p a b -> p a c
    *   second = bimap id
    */
  trait Bifunctor[P[_,_]] {

    def bimap[A, B, C, D](f: A => B, g: C => D): P[A, C] => P[B, D] =
      first(f) andThen second(g)

    def first[A, B, C](f: A => B): P[A,C] => P[B,C] = bimap(f, identity[C])

    def second[A, B, C](f: B => C): P[A, B] => P[A, C] = bimap(identity, f)
  }

  object BifunctorInstances {

    implicit val tupleBifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {
      override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple2[A, C] => Tuple2[B, D] =
        t => (f(t._1), g(t._2))
    }

    implicit val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
      override def bimap[A, B, C, D](f: A => B, g: C => D): Either[A, C] => Either[B, D] = {
        case Left(a) => Left(f(a))
        case Right(c) => Right(g(c))
      }
    }

    implicit val constBifunctor: Bifunctor[Const] = new Bifunctor[Const] {
      override def bimap[A, B, C, D](f: A => B, g: C => D): Const[A, C] => Const[B, D] =
        c => Const[B, D](f(c.getConst))
    }

    implicit def tuple3Bifunctor[X]: Bifunctor[Tuple3[X, ?,?]] = new Bifunctor[Tuple3[X, ?,?]] {
      override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple3[X, A, C] => Tuple3[X, B, D] =
        c => (c._1, f(c._2), g(c._3))
    }

    implicit def tuple4Bifunctor[X1, X2]: Bifunctor[Tuple4[X1, X2, ?, ?]] = new Bifunctor[Tuple4[X1, X2, ?, ?]] {
      override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple4[X1, X2, A, C] => Tuple4[X1, X2, B, D] =
        c => (c._1, c._2, f(c._3), g(c._4))
    }
  }

  import BifunctorInstances._

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