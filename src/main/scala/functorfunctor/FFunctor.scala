package functorfunctor

import natural_transformation.NaturalTransf.NaturalTransf

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
//trait FFunctor[FF[_]] {
//  def ffmap[F[_],G[_]](nat: NaturalTransf[F,G]): FF[F] => FF[G]
//}


object FFunctorExample {
  // TODO port https://www.benjamin.pizza/posts/2017-12-15-functor-functors.html
}