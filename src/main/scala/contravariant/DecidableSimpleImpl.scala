package contravariant

import contravariant.DivisibleSimpleImpl.Divisible

import scala.language.higherKinds

object DecidableSimpleImpl {

  type Void <: Nothing

  trait Decidable[F[_]] extends Divisible[F] {
    def choose[A,B,C](f: A => Either[B,C], fb: F[B], fc: F[C]): F[A]
    def loose[A](f: A => Void)
  }
}
