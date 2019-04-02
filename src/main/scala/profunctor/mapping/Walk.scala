package profunctor.mapping

import educational.Traverse
import profunctor.traverse.Step

import scala.language.higherKinds

trait Walk[P[_,_]] extends Step[P] {
  def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
}
