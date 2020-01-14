package educational.category_theory

trait Apply[F[_]] extends Functor[F] {
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]

  def product[A, B](fa: F[A], fb: F[B]): F[(A, B)] = {
    val abTuple: A => B => (A, B) =
      a => b => (a, b)
    val fb2ab: F[B => (A, B)] = map(fa)(abTuple)
    ap(fb2ab)(fb)
  }

  def ap2[A, B, Z](ff: F[(A, B) => Z])(fa: F[A], fb: F[B]): F[Z] =
    map(product(fa, product(fb, ff))) { case (a, (b, f)) => f(a, b) }
}

trait ApplyLaws[F[_]]
  extends FunctorLaws[F]
     with Apply[F] {

  def apComposition[A, B, C](fa: F[A], fab: F[A => B], fbc: F[B => C]): Boolean = {

    //        ap F[A => B]              ap F[B => C]
    // F[A] ==================> F[B] =================> F[C]
    val fb: F[B] = ap(fab)(fa)
    val left: F[C] = ap(fbc)(fb)

    val expand: (B => C) => ((A => B) => (A => C)) =
      (bc: B => C) =>
        (ab: A => B) =>
          bc compose ab

    //               map( A=>B => B=>C => A=>C )
    // F[B => C] ======================================> F[A=>B => A=>C]
    val fabac: F[(A => B) => (A => C)] = map(fbc)(expand)

    //              ap F[A=>B => A=>C]
    // F[A => B] ==============================> F[A => C]
    val fac: F[A => C] = ap(fabac)(fab)

    //           ap F[A=>C]
    // F[A] =========================> F[C]
    val right: F[C] = ap(fac)(fa)

    left == right
  }
}
