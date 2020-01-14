package educational.category_theory.higher

import educational.category_theory.~>

class NatCategory extends CategoryPoly[~>] {
  def id[A[_]]: ~>[A, A] = new IdNat[A]
  def compose[A[_], B[_], C[_]](f: ~>[B, C])(g: ~>[A, B]): ~>[A, C] = new NatCompose(g,f)
}
