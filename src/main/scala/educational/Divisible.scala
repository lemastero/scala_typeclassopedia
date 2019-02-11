package educational

import scala.language.higherKinds

trait Divisible[F[_]] extends Divide[F] {
  def conquer[A]: F[A] // unit
}

trait DivisibleLaws[F[_]] extends DivideLaws[F] with Divisible[F] {

  // divide delta m conquer = m
  def rightIdentity[A](fa: F[A]): Boolean = {
    //                 divide(delta)
    // F[A], conquer ===============> F[A]
    val l: F[A] = divide(delta, fa, conquer[A])

    l == fa
  }

  // divide delta conquer m = m
  def leftIdentity[A](fa: F[A]): Boolean = {
    //                 divide(delta)
    // conquer, F[A] ===============> F[A]
    val l: F[A] = divide(delta, conquer[A], fa)

    l == fa
  }

  // TODO more eneral laws: http://hackage.haskell.org/package/contravariant-1.5/docs/Data-Functor-Contravariant-Divisible.html#g:4
}
