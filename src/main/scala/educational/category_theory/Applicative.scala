package educational.category_theory

trait Applicative[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A]

  // derived methods
  def liftA2[A, B, Z](abc: (A, B) => Z)(fa: F[A], fb: F[B]): F[Z] = ap(map(fa)(abc.curried))(fb)

  override def map[A, B](fa: F[A])(f: A => B): F[B] =
    ap(pure(f))(fa)
}

trait ApplicativeLaws[F[_]] extends FunctorLaws[F]
  with Applicative[F] {

  def apIdentityLaw[A](fa: F[A]): Boolean = {
    val l1: F[A => A] = pure(identity[A])
    val l2: F[A] = ap(l1)(fa)

    //         ap F[identity]
    //  F[A] ==================> F[A]
    l2 == fa
  }

  def homomorphismLaw[A, B](ab: A => B, a: A): Boolean = {
    val fab: F[A => B] = pure(ab)
    val fa: F[A] = pure(a)

    //         F[A => B]
    // F[A] =============> F[B]
    val l: F[B] = ap(fab)(fa)

    val r: F[B] = pure(ab(a))

    l == r
  }

  def interchangeLaw[A, B](fab: F[A => B], a: A): Boolean = {
    //       ap F[A => B]
    // F[A] ==============> F[B]
    val l: F[B] = ap(fab)(pure(a))

    val fabb: F[(A => B) => B] = pure((f: A => B) => f(a))

    //              ap F[(A => B) => B]
    // F[A => B] ========================> F[B]
    val r: F[B] = ap(fabb)(fab)

    l == r
  }

  def mapLikeDerivedLaw[A, B](f: A => B, fa: F[A]): Boolean = {
    val l: F[B] = map(fa)(f)
    val r: F[B] = ap(pure(f))(fa)
    l == r
  }
}
