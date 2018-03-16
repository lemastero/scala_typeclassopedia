package monad

class SimpleImplementation {

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

  /* laws:

  1) join associativity -- TODO how

  2) unit must be identity for join

           unit
       A ---------> M[A]
       |             |
  unit |             |  unit
       |             |
       \/            \/
   M[A] <------  M[M[A]]
           join

  Monad[M].join( Monad[M].unit( Monad[M].unit(a) ) ) == Monad[M].unit(a)
  */

  trait Monad[M[_]] extends Functor[M] {
    def unit[A](a: A): M[A]
    def join[A](mma: M[M[A]]): M[A]
  }
}
