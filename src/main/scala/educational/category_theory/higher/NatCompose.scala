package educational.category_theory.higher

import educational.category_theory.~>

class NatCompose[F[_],G[_],H[_]](f: F~>G, g: G~>H) extends ~>[F,H] {
  def apply[A](fa: F[A]): H[A] = g(f(fa))
}
