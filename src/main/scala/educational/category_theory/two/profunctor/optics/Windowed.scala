package educational.category_theory.two.profunctor.optics

import educational.category_theory.two.profunctor.choice.Choice

trait Windowed[=>:[_,_]] extends Choice[=>:] with Glassed[=>:] {
  def windowedRGL[A,B,U,V,T](pab: A =>: B): Either[V, (T,U=>A)] =>: Either[V, (T,U=>B)] = right(glassedL(pab))
  def windowedRGR[A,B,U,V,T](pab: A =>: B): Either[V, (U=>A,T)] =>: Either[V, (U=>B,T)] = right(glassedR(pab))
  def windowedLGL[A,B,U,V,T](pab: A =>: B): Either[(U=>A,T), V] =>: Either[(U=>B,T), V] = left(glassedR(pab))
  def windowedLGR[A,B,U,V,T](pab: A =>: B): Either[(U=>A,T),V] =>: Either[(U=>B,T),V] = left(glassedR(pab))
}
