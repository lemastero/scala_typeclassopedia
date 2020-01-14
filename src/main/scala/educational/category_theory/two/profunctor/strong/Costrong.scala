package educational.category_theory.two.profunctor.strong

import educational.category_theory.two.profunctor.Profunctor

trait Costrong[F[_,_]] extends Profunctor[F] {
  def unfirst[A,B,D](fa: F[(A,D), (B, D)]): F[A,B]
  def unsecond[A,B,D](fa: F[(D,A),(D,B)]): F[A,B]
}
