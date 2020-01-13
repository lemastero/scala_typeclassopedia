package educational.data

import educational.category_theory.Applicative

trait Value[F[_],A] {
  def foo[C,D]: F[C] => FreeAp[F,D] => (C,D) => A => FreeAp[F,A]
}

sealed trait FreeAp[F[_],A]
case class Pure[F[_],A](f: A => F[A]) extends FreeAp[F,A]
case class SuspendAp[F[_],A](f: Value[F,A]) extends FreeAp[F,A]

object FreeApInstanes {
  def freeAppApplicative[F[_]]: Applicative[FreeAp[F, *]] = new Applicative[FreeAp[F, *]] {
    def pure[A](value: A): FreeAp[F, A] = ???
    def ap[A, B](ff: FreeAp[F, A => B])(fa: FreeAp[F, A]): FreeAp[F, B] = ???
  }
}