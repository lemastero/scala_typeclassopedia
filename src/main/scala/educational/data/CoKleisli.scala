package educational.data

import educational.category_theory.Functor
import educational.category_theory.two.profunctor.Profunctor

case class CoKleisli[F[_],A,B](run: F[A] => B)

object CoKleisliInstances {
  def coKleisliProfunctor[F[_]](implicit FF: Functor[F]): Profunctor[CoKleisli[F,*,*]] = new Profunctor[CoKleisli[F,*,*]] {
    def dimap[S,T,A,B](pab: CoKleisli[F,A,B])(ab: S => A, cd: B => T): CoKleisli[F,S,T] =
      CoKleisli{ FF.lift(ab) andThen (pab.run andThen cd) }
  }
}
