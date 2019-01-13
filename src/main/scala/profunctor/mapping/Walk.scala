package profunctor.mapping

import profunctor.traverse.Step
import traverse.TraverseSimpleImpl.Traverse

trait Walk[P[_,_]] extends Step[P] {
  def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
}
