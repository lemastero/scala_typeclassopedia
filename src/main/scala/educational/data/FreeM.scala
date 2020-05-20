package educational.data

import educational.category_theory.{Functor, Monad}

/* from: http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/ */
sealed trait FreeM[F[_], A]
final case class Return[F[_], A](a: A) extends FreeM[F, A]
final case class Suspend[F[_], A](s: F[FreeM[F,A]]) extends FreeM[F,A]

object FreeM {
  def freeMonad[F[_]](implicit FF: Functor[F]): Monad[FreeM[F, *]] = new Monad[FreeM[F, *]] {
    def flatMap[A, B](ma: FreeM[F, A])(f: A => FreeM[F, B]): FreeM[F, B] =
      ma match {
        case Return(a) => f(a)
        case Suspend(m) => Suspend{
          def ff: FreeM[F, A] => FreeM[F, B] = x => flatMap(x)(f)
          FF.map(m)(ff)
        }
    }
    def pure[A](a: A): FreeM[F, A] = Return(a)
  }
}

// from https://www.youtube.com/watch?v=M258zVn4m2M
object free2 {
  sealed trait Free[F[_], A]
  case class Return[F[_], A](a: A) extends Free[F,A]
  case class Bind[F[_],I,A](i: F[I], k: I => Free[F,A]) extends Free[F,A]
}

object free3 {
  // https://www.youtube.com/watch?v=7xSfLPD6tiQ
  // Pure Functional Database Programming with Fixpoint Types — Rob Norris
  case class Free[F[_], A](resume: Either[A,F[Free[F,A]]])
}
