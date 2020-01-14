package educational.types

import educational.category_theory.Functor
import educational.category_theory.two.Bifunctor

object Const {
  type Const[A, B] = A

  def constFunctorR[A]: Functor[Const[A, *]] = new Functor[Const[A, *]] {
    def map[B, BB](fa: Const[A, B])(f: B => BB): Const[A, BB] = fa
  }

  def constFunctorL[B]: Functor[Const[*, B]] = new Functor[Const[*,B]] {
    override def map[A, AA](fa: Const[A, B])(f: A => AA): Const[AA, B] = f(fa)
  }

  val constBifunctor: Bifunctor[Const] = new Bifunctor[Const] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Const[A,C] => Const[B,D] = f
    override def first[A, B, C](f: A => B): Const[A, C] => Const[B, C] = f
    override def second[A, B, C](f: B => C): Const[A, B] => Const[A, C] = identity[Const[A,Nothing]]
  }
}
