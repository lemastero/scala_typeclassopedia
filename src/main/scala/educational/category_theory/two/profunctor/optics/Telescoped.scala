package educational.category_theory.two.profunctor.optics

import educational.category_theory.two.profunctor.choice.Choice
import educational.category_theory.two.profunctor.closed.Closed

trait Telescoped[=>:[_,_]] extends Closed[=>:] with Choice[=>:] {
  def telescopeL[A,B,C,D](pab: A =>: B): Either[C => A, D] =>: Either[C => B, D] = left(closed(pab))
  def telescopeR[A,B,C,D](pab: A =>: B): Either[D, C => A] =>: Either[D, C => B] = right(closed(pab))
}
