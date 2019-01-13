package category

import natural_transformation.NaturalTransf.NaturalTransf

import scala.language.higherKinds

class IdNat[F[_]] extends NaturalTransf[F,F] {
  def apply[A](fa: F[A]): F[A] = fa
}
