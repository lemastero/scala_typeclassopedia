package educational.category_theory.two.profunctor.mapping

import educational.category_theory.Functor

trait Settable[P[_,_]] extends Walk[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Mapping.html
  def mapping[A,B,F[_]](pab: P[A,B])(implicit FT: Functor[F]): P[F[A], F[B]]
}
