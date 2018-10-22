package applicative

import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

object ApplicativeSimpleImpl {

  trait Apply[F[_]] extends Functor[F] {
    def apply[A, B](ff: F[A => B])(fa: F[A]): F[B]
  }

  trait Applicative[F[_]] extends Apply[F] {
    def pure[A](value: A): F[A]
    def liftA2[A, B, Z](abc: (A, B) => Z)(fa: F[A], fb: F[B]): F[Z] = apply(map(fa)(abc.curried))(fb)
  }
}
