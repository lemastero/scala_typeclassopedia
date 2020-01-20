package functorfunctor

import educational.category_theory.higher.~>

/**
  * Functor Functor
  *
  * @see https://www.benjamin.pizza/posts/2017-12-15-functor-functors.html
  *
  * Laws:
  * -- identity
  * ffmap id == id
  *
  * -- composition
  * ffmap (eta . phi) = ffmap eta . ffmap phi
  */
trait FFunctor[FF[_[_]]] {
  def ffmap[F[_],G[_]](nat: F~>G): FF[F] => FF[G]
}
