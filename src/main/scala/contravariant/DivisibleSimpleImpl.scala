package contravariant

import contravariant.ContravariantSimpleImpl.Contravariant

import scala.language.higherKinds

object DivisibleSimpleImpl {

  trait Divisible[F[_]] extends Contravariant[F] {
    def divide[A,B,C](f: A => (B,C), fb: F[B], fc: F[C]): F[A]
    def conquer[A]: F[A]
  }
}
