package educational

import scala.language.higherKinds

trait Divide[F[_]] extends Contravariant[F] {
  def divide[A,B,C](f: A => (B,C), fb: F[B], fc: F[C]): F[A] // contramap2
}

trait DivideLaws[F[_]] extends ContravariantLaws[F] with Divide[F] {

  def delta[A]: A => (A, A) = a => (a, a)

  // divide delta (divide delta m n) o = divide delta m (divide delta n o)
  def divideComposition[A](fa1: F[A], fa2: F[A], fa3: F[A]): Boolean = {
    //                divide(delta)
    //  F[A1], F[A2] ===============> F[A12]
    val fa12: F[A] = divide(delta[A], fa1, fa2)

    //                 divide(delta)
    // F[A12], F[A3] =================> F[A123]
    val l: F[A] = divide( delta[A], fa12, fa3)


    //                divide(delta)
    //  F[A2], F[A3] ===============> F[A23]
    val fa23: F[A] = divide(delta[A], fa2, fa3)

    //                  divide(delta)
    //  F[A1], F[A23] ===============> F[A123]
    val r: F[A] = divide( delta[A], fa1, fa23 )
    l == r
  }

  // TODO more eneral laws: http://hackage.haskell.org/package/contravariant-1.5/docs/Data-Functor-Contravariant-Divisible.html#g:4
}
