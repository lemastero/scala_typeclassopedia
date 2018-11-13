package contravariant

import contravariant.DivisibleSimpleImpl.Divisible
import simple.Void

import scala.language.higherKinds

object DecidableSimpleImpl {

  trait Decide[F[_]] extends Divisible[F] {
    def choose[A,B,C](f: A => Either[B,C], fb: F[B], fc: F[C]): F[A]
  }

  trait Decidable[F[_]] extends Divisible[F] with Decide[F] {
    def loose[A](f: A => Void): F[A]
  }
}
