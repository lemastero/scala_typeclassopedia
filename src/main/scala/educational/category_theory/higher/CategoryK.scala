package educational.category_theory.higher

import educational.category_theory.Functor

// objects are type constructors
// morpshisms are natural transformations
trait CategoryK[Morphism[ _[_], _[_] ]] {
  def id[A[_]]: Morphism[A,A]
  def compose[A[_],B[_],C[_]](f: Morphism[B,C])(g: Morphism[A,B]): Morphism[A,C]
}

object CategoryKInstances {
  val functorCategory: CategoryK[~>] = new CategoryK[~>] {
    def id[F[_]]: F~>F = new IdNat[F]
    def compose[A[_], B[_], C[_]](f: B ~> C)(g: A ~> B): A ~> C = VerticalComposition(g,f)
  }
}
