package educational.category_theory.two.profunctor.sieve

import educational.category_theory.two.profunctor.Profunctor

trait Sieve[P[_,_],F[_]] extends Profunctor[P] {
  def sieve[A, B](pab: P[A, B], a: A): F[B]
}

object SieveInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Sieve.html#i:Sieve
  // TODO need Kleisli
  // TODO need Profunctor Compose
  // TODO need Functor Compose
  // TODO need Profunctor Start
  // TODO need Profunctor Forget
}
