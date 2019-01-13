package category

import natural_transformation.NaturalTransf.NaturalTransf

import scala.language.higherKinds

class NatCompose[F[_],G[_],H[_]](f: NaturalTransf[F,G], g: NaturalTransf[G,H]) extends NaturalTransf[F,H] {
  def apply[A](fa: F[A]): H[A] = g(f(fa))
}
