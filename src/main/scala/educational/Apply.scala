package educational

import scala.language.higherKinds

trait Apply[F[_]] extends Functor[F] {
  def apply[A, B](ff: F[A => B])(fa: F[A]): F[B]
}

trait ApplyLaws[F[_]]
  extends FunctorLaws[F]
  with Apply[F] {

  def composition[A, B, C](fa: F[A], fab: F[A => B], fbc: F[B => C]): Boolean = {

    //        apply F[A => B]              apply F[B => C]
    // F[A] =====================> F[B] =====================> F[C]
    val fb: F[B] = apply(fab)(fa)
    val left: F[C] = apply(fbc)(fb)

    val expand: (B => C) => ((A => B) => (A => C)) =
      (bc: B => C) =>
        (ab: A => B) =>
          bc compose ab

    //               map( A=>B => B=>C => A=>C )
    // F[B => C] ======================================> F[A=>B => A=>C]
    val fabac: F[(A => B) => (A => C)] = map(fbc)(expand)

    //              apply F[A=>B => A=>C]
    // F[A => B] =================================> F[A => C]
    val fac: F[A => C] = apply(fabac)(fab)

    //           apply F[A=>C]
    // F[A] ============================> F[C]
    val right: F[C] = apply(fac)(fa)

    left == right
  }
}

