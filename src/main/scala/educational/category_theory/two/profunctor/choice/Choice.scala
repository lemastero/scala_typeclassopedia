package educational.category_theory.two.profunctor.choice

import educational.category_theory.two.profunctor.Profunctor

trait Choice[=>:[_, _]] extends Profunctor[=>:] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Choice.html
  def left[A,B,C](pab: A =>: B):  Either[A,C] =>: Either[B,C]
  def right[A,B,C](pab: A =>: B): Either[C,A] =>: Either[C,B] = {
    val v1: Either[A,C] =>: Either[B,C] = left(pab)
    dimap(v1)(_.swap, _.swap)
  }
}
