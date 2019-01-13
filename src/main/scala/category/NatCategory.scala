package category

import natural_transformation.NaturalTransf.NaturalTransf

import scala.language.higherKinds

class NatCategory extends CategoryPoly[NaturalTransf] {
  def id[A[_]]: NaturalTransf[A, A] = new IdNat[A]
  def compose[A[_], B[_], C[_]](f: NaturalTransf[B, C])(g: NaturalTransf[A, B]): NaturalTransf[A, C] = new NatCompose(g,f)
}
