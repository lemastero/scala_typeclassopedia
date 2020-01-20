package educational.category_theory.two.profunctor.optics

import educational.category_theory.two.profunctor.closed.Closed
import educational.category_theory.two.profunctor.strong.Strong

trait Glassed[=>:[_,_]] extends Strong[=>:] with Closed[=>:] {
  def glassedL[A,B,U,T](pab: A =>: B): (T, U => A) =>: (T, U => B) = second(closed[A,B,U](pab))
  def glassedR[A,B,U,T](pab: A =>: B): (U => A,T) =>: (U => B,T) = first(closed[A,B,U](pab))
}
