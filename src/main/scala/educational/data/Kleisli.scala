package educational.data

import educational.category_theory.Functor
import educational.category_theory.two.profunctor.Profunctor

case class Kleisli[F[_],A,B](run: A => F[B])

object KleisliInstances {

  // TODO Haskell wants Monad https://hackage.haskell.org/package/profunctors-5.5.1/docs/src/Data.Profunctor.Unsafe.html#line-192
  def kleisliProfunctor[M[_]](MM: Functor[M]): Profunctor[Kleisli[M,*,*]] = new Profunctor[Kleisli[M,*,*]] {
    def dimap[S,T,A,B](pab: Kleisli[M,A,B])(f: S => A, g: B => T): Kleisli[M,S,T] = Kleisli { s =>
      MM.map((pab.run compose f)(s))(g)
    }
  }
}
