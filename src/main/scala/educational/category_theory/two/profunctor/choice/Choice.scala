package educational.category_theory.two.profunctor.choice

import educational.category_theory.two.profunctor.Profunctor

trait Choice[P[_, _]] extends Profunctor[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Choice.html
  def left[A,B,C](pab: P[A, B]):  P[Either[A, C], Either[B, C]]
  def right[A,B,C](pab: P[A, B]): P[Either[C, A], Either[C, B]] = {
    val v1: P[Either[A, C], Either[B, C]] = left(pab)
    val v2: P[Either[A, C], Either[B, C]] => P[Either[C, A], Either[C, B]]  = dimap(_.swap, _.swap)
    v2(v1)
  }
}
