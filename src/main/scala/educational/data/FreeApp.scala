package educational.data

import educational.category_theory.Applicative

/**
  * Free Applicative Functor defined based on Haskell implementation
  * http://hackage.haskell.org/package/free/docs/Control-Applicative-Free.html
  */
sealed trait FreeAp[F[_],A]
case class Pure[F[_],A](a: A) extends FreeAp[F,A]
case class SuspendAp[F[_],B](f: Value[F,B]) extends FreeAp[F,B]

trait Value[F[_],B] {
  type A
  val fa: F[A]
  val fab: FreeAp[F,A => B]
}

object Value {
  def apply[F[_],B,AA](faa: F[AA], faab: FreeAp[F,AA => B]): Value[F,B] = new Value[F,B] {
    type A = AA
    val fa: F[AA] = faa
    val fab: FreeAp[F,A => B] = faab
  }
}

object FreeApInstanes {
  def freeAppApplicative[F[_]]: Applicative[FreeAp[F, *]] = new Applicative[FreeAp[F, *]] {
    override def map[X, Y](fa: FreeAp[F, X])(f: X => Y): FreeAp[F, Y] = fa match {
      case Pure(a) => Pure(f(a))
      case SuspendAp(v) => SuspendAp(
        Value(v.fa, map(v.fab)(_ andThen f))
      )
    }
    override def pure[A](a: A): FreeAp[F, A] = Pure(a)
    override def ap[A, B](ff: FreeAp[F, A => B])(fa: FreeAp[F, A]): FreeAp[F, B] = fa match {
      case Pure(a) => ??? // TODO
      case SuspendAp(v) => ??? // TODO
    }
  }
}