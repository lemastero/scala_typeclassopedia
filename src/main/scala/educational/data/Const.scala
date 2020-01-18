package educational.data

import educational.category_theory.Functor
import educational.category_theory.two.bifunctors.Bifunctor

final case class Const[A, B](a: A)

object ConstInstances {
  def constFunctorR[X]: Functor[Const[X, *]] = new Functor[Const[X, *]] {
    override def map[A, B](fa: Const[X, A])(f: A => B): Const[X, B] = Const[X,B](fa.a)
  }

  def constFunctorL[X]: Functor[Const[*, X]] = new Functor[Const[*,X]] {
    override def map[A, B](fa: Const[A, X])(f: A => B): Const[B, X] = Const(f(fa.a))
  }

  val constBifunctor: Bifunctor[Const] = new Bifunctor[Const] {
    override def bimap[A, B, C, D](fa: Const[A,C])(f: A => B, g: C => D): Const[B,D] =
      Const[B,D](f(fa.a))
  }
}
