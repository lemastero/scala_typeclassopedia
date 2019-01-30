package educational

import scala.language.higherKinds

trait Applicative[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A]

  // derived methods
  def liftA2[A, B, Z](abc: (A, B) => Z)(fa: F[A], fb: F[B]): F[Z] = apply(map(fa)(abc.curried))(fb)
}

trait ApplicativeLaws[F[_]] extends FunctorLaws[F]
  with Applicative[F] {

  def identityAp[A](fa: F[A]): Boolean = {
    val l1: F[A => A] = pure(identity[A])
    val l2: F[A] = apply(l1)(fa)

    //         apply F[identity]
    //  F[A] ====================> F[A]
    l2 == fa
  }

  def homomorphism[A, B](ab: A => B, a: A): Boolean = {
    val fab: F[A => B] = pure(ab)
    val fa: F[A] = pure(a)

    //         F[A => B]
    // F[A] =============> F[B]
    val l: F[B] = apply(fab)(fa)

    val r: F[B] = pure(ab(a))

    l == r
  }

  def interchange[A, B](fab: F[A => B], a: A): Boolean = {
    //       apply F[A => B]
    // F[A] =================> F[B]
    val l: F[B] = apply(fab)(pure(a))

    val fabb: F[(A => B) => B] = pure((f: A => B) => f(a))

    //               apply F[(A => B) => B]
    // F[A => B] ===========================> F[B]
    val r: F[B] = apply(fabb)(fab)

    l == r
  }

  /** `map` is like the one derived from `point` and `ap`. */
  def mapLikeDerived[A, B](f: A => B, fa: F[A]): Boolean = {
    val l: F[B] = map(fa)(f)
    val r: F[B] = apply(pure(f))(fa)
    l == r
  }
}
