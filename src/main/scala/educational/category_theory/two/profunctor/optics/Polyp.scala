package educational.category_theory.two.profunctor.optics

import educational.category_theory.Applicative
import educational.category_theory.two.profunctor.Profunctor

trait Polyp[=>:[_,_]] extends Profunctor[=>:] {
  def polyper[A,B,F[_]](pab: A =>: B)(implicit A: Applicative[F]): F[A] =>: F[B]
}
