package educational.category_theory.higher

import educational.category_theory.~>

class IdNat[F[_]] extends ~>[F,F] {
  def apply[A](fa: F[A]): F[A] = fa
}
