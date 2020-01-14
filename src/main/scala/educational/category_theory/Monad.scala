package educational.category_theory

/**
  * Simple implementation of Monads shown in:
  * Rúnar Bjarnason
  * A Scala Comonad Tutorial, Part 1
  * http://blog.higher-order.com/blog/2015/06/23/a-scala-comonad-tutorial/
  */

/* laws:

1) flatten associativity -- TODO how

2) pure must be identity for flatten

              pure
        A -------------> M[A]
        |                |
pure    |                |  pure
        |                |
        \/               \/
       M[A] <-------- M[M[A]]
             flatten

Monad[M].flatten( Monad[M].pure( Monad[M].pure(a) ) ) == Monad[M].pure(a)

  1. flatmap associativity: `fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(b => g(b))`
  2. left identity: `pure(a).flatMap(f) == f(a)`
  3. right identity: `fa.flatMap(a => pure(a)) == fa`
*/


trait Monad[F[_]] extends Applicative[F] {
  def flatMap[A, B](ma: F[A])(f: A => F[B]): F[B] // flatten(map(ma)(f))

  def flatten[A](mma: F[F[A]]): F[A] = flatMap(mma)(identity)
  override def map[A, B](x: F[A])(f: A => B): F[B] = flatMap(x)(a => pure(f(a)))

  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B] =
    flatMap(ff)(f => map(fa)(f))

  override def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] =
    flatMap(fa)(a => map(fb)(b => (a, b)))

  override def ap2[A, B, Z](ff: F[(A, B) => Z])(fa: F[A], fb: F[B]): F[Z] =
    flatMap(fa)(a => flatMap(fb)(b => map(ff)(_(a, b))))
}

object MonadInstance {
  val optionMonad: Monad[Option] = new Monad[Option] {

    def pure[A](a: A): Option[A] = Some(a)

    def flatMap[A, B](ma: Option[A])(f: A => Option[B]): Option[B] = {
      ma match {
        case Some(a) => f(a)
        case None => None
      }
    }
  }
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
  def leftIdentity[A,B,C](a: A, f: A => M[B]): Boolean = {
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
  def rightIdentity[A,B,C](fa: M[A]): Boolean = {
    //        flatMap
    // M[A] ==========> M[A]
    val l: M[A] = flatMap(fa)(pure)

    val r: M[A] = fa
    l == r
  }
}
