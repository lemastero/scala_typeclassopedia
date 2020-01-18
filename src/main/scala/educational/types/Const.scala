package educational.types

import educational.category_theory.Functor
import educational.category_theory.two.bifunctors.Bifunctor

object Const {
  type Const[A, B] = A

  def constFunctorR[A]: Functor[Const[A, *]] = new Functor[Const[A, *]] {
    def map[B, BB](fa: Const[A, B])(f: B => BB): Const[A, BB] = fa
  }

  def constFunctorL[B]: Functor[Const[*, B]] = new Functor[Const[*,B]] {
    override def map[A, AA](fa: Const[A, B])(f: A => AA): Const[AA, B] = f(fa)
  }

  val constBifunctor: Bifunctor[Const] = new Bifunctor[Const] {
    def bimap[A,AA,B,BB](fa: Const[A,B])(f: A => AA, g: B => BB): Const[AA,BB] = f(fa)
  }
}
