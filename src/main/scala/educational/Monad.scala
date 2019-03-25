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

            pure
        A ---------> M[A]
        |             |
flatten |             |  pure
        |             |
        \/            \/
   M[A] <--------- M[M[A]]
         flatten

Monad[M].join( Monad[M].unit( Monad[M].unit(a) ) ) == Monad[M].unit(a)

  1. flatmap associativity: `fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(b => g(b))`
  2. left identity: `pure(a).flatMap(f) == f(a)`
  3. right identity: `fa.flatMap(a => pure(a)) == fa`
*/

trait Monad[M[_]] extends Functor[M] {
  def pure[A](a: A): M[A]
  def flatMap[A, B](ma: M[A])(f: A => M[B]): M[B] // flatten(map(ma)(f))

  def flatten[A](mma: M[M[A]]): M[A] = flatMap(mma)(identity)
  def map[A, B](x: M[A])(f: A => B): M[B] = flatMap(x)(a => pure(f(a)))
}

trait MonadLaws[M[_]] extends Monad[M] {

  // fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(b => g(b))
  def flatMapAssociativity[A,B,C](fa: M[A], f: A => M[B], g: B => M[C]): Boolean = {
    //         flatMap(f)
    //  M[A] =============> M[B]
    val l1: M[B] = flatMap(fa)(f)
    //         flatMap(g)
    //  M[B] =============> M[C]
    val l2: M[C] = flatMap(l1)(g)

    val r1: A => M[C] = a => flatMap(f(a))(b => g(b))
    //         flatMap(f flatMap g)
    //  M[A] ======================> M[C]
    val r2: M[C] = flatMap(fa)(r1)
    l2 == r2
  }

  // pure(a).flatMap(f) == f(a)
  def leftIdentity[A,B,C](a: A, f: A => M[B], g: B => M[C]): Boolean = {
    //    pure
    // A =======> M[A]
    val l1: M[A] = pure(a)
    //        flatMap
    // M[A] ==========> M[B]
    val l2: M[B] = flatMap(l1)(f)

    // A =======> M[B]
    val r: M[B] = f(a)
    l2 == r
  }

  // fa.flatMap(pure) == fa
  def rightIdentity[A,B,C](a: A, fa: M[A], g: B => M[C]): Boolean = {
    //        flatMap
    // M[A] ==========> M[A]
    val l: M[A] = flatMap(fa)(pure)

    val r: M[A] = fa
    l == r
  }
}
