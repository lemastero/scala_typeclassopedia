package educational

import functor.FunctorSimpleImpl.Functor

/**
  * Simple implementation of Monads shown in:
  * Rúnar Bjarnason
  * A Scala Comonad Tutorial, Part 1
  * http://blog.higher-order.com/blog/2015/06/22/a-scala-comonad-tutorial/
  */

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
  def pure[A](a: A): M[A]
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B] // flatten(map(ma)(f))

  def flatten[A](mma: M[M[A]]): M[A] = flatMap(mma)(identity)
  def map[A, B](x: M[A])(f: A => B): M[B] = flatMap(x)(a => pure(f(a)))
}
