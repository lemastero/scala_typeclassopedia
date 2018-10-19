package functor

import scala.language.higherKinds

object FunctorSimpleImpl {

  /**
    * Simple implementation of Monads shown in:
    * Rúnar Bjarnason
    * A Scala Comonad Tutorial, Part 1
    * http://blog.higher-order.com/blog/2015/06/22/a-scala-comonad-tutorial/
    */

  // law: Functor[F].map(x)(identity) == x
  trait Functor[F[_]] {
    def map[A, B](x: F[A])(f: A => B): F[B]
  }
}
