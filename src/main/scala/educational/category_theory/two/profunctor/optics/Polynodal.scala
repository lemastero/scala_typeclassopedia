package educational.category_theory.two.profunctor.optics

import educational.category_theory.two.profunctor.closed.Closed

trait Polynodal[=>:[_,_]] extends Closed[=>:] with Traversing[=>:] {
  def griddedL[A,B,C,D](pab: A =>: B): (D => (List[A],C)) =>: (D => (List[B],C)) = closed(strechL[A,B,C](pab))
  def griddedR[A,B,C,D](pab: A =>: B): (D => (C,List[A])) =>: (D => (C,List[B])) = closed(strechR[A,B,C](pab))
}
