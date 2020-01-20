package educational.category_theory.two.profunctor.optics

import educational.category_theory.two.profunctor.choice.Choice
import educational.category_theory.two.profunctor.strong.Strong

trait Magnified[=>:[_,_]] extends Strong[=>:] with Choice[=>:] {
  def maginfyLS[A,B,C,D](pab: A =>: B): (C, Either[A,D]) =>: (C, Either[B,D]) = second(left(pab))
  def maginfyRS[A,B,C,D](pab: A =>: B): (C, Either[D,A]) =>: (C, Either[D,B]) = second(right(pab))
  def maginfyLF[A,B,C,D](pab: A =>: B): (Either[A,D],C) =>: (Either[B,D],C) = first(left(pab))
  def maginfyRF[A,B,C,D](pab: A =>: B): (Either[D,A],C) =>: (Either[D,B],C) = first(right(pab))
}
