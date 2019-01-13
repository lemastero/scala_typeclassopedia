package profunctor.mapping

import functor.FunctorSimpleImpl.Functor

trait Settable[P[_,_]] extends Walk[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Mapping.html
  def mapping[A,B,F[_]](pab: P[A,B])(implicit FT: Functor[F]): P[F[A], F[B]]
}
