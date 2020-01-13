package educational.category_theory.higher

trait CategoryPoly[K[ _[_], _[_] ]] {
  def id[A[_]]: K[A, A]
  def compose[A[_], B[_], C[_]](f: K[B, C])(g: K[A, B]): K[A, C]
}
