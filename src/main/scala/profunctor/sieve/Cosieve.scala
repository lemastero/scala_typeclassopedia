package profunctor.sieve

import profunctor.Profunctor

// TODO F is Functor
// TODO p f | p -> f
trait Cosieve[P[_,_],F[_]] extends Profunctor[P] {
  def cosieve[A,B](pab: P[A,B], fa: F[A]): B
}

object CosieveInstances {
  // TODO need Profunctor Costar
  // TODO need Cokleisli
  // TODO need Profuncotr Compose
  // TODO need Functor Compose
}
