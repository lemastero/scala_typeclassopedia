package educational.data

import educational.category_theory.{Applicative, Functor}

sealed trait Maybe[+A]
case class MSome[A](a: A) extends Maybe[A]
case object MNone extends Maybe[Nothing]

object MaybeInstances {

  val optionFunctor: Functor[Maybe] = new Functor[Maybe] {
    def map[A, B](opt: Maybe[A])(f: A => B): Maybe[B] = opt match {
      case MNone => MNone
      case MSome(v) => MSome(f(v))
    }
  }

  val optionApplicative: Applicative[Maybe] = new Applicative[Maybe] {
    def pure[A](a: A): Maybe[A] = MSome(a)
    def ap[A, B](ff: Maybe[A => B])(fa: Maybe[A]): Maybe[B] = (ff,fa) match {
      case (MSome(fab), MSome(faa)) => MSome(fab(faa))
      case (_,_) => MNone
    }
  }
}
