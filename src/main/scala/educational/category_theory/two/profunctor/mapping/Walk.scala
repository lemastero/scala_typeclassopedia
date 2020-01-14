package educational.category_theory.two.profunctor.mapping

import educational.category_theory.Traverse
import educational.category_theory.two.profunctor.traverse.Step

trait Walk[P[_,_]] extends Step[P] {
  def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
}
