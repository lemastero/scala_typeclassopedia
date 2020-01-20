package educational.category_theory.two.profunctor.optics

import educational.category_theory.two.profunctor.Profunctor

trait Traversing[=>:[_,_]] extends Profunctor[=>:] {
  def strechL[A,B,C](pab: A =>: B): (List[A],C) =>: (List[B],C) // TODO replace List by Traverse
  def strechR[A,B,C](pab: A =>: B): (C, List[A]) =>: (C,List[B])
}
