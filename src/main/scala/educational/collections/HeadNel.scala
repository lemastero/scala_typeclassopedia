package educational.collections

import educational.category_theory.Comonad

sealed trait AbstractNel[+A]{
  def head: A

  def tailOpt: Option[AbstractNel[A]] = this match {
    case HeadNel(_, tail) => Some(tail)
    case _ => None
  }
}

case class TailNel[A](head: A) extends AbstractNel[A] {
  def +(other: AbstractNel[A]): AbstractNel[A] = HeadNel(head, other)
}

case class HeadNel[+A](head: A, tail: AbstractNel[A]) extends AbstractNel[A]

case class Nel[+A](head: A, tail: Nel[A])

object NelInstances {
  val nelComonad: Comonad[Nel] = new Comonad[Nel] {
    def extract[A](w: Nel[A]): A = ???
    def duplicate[A](wa: Nel[A]): Nel[Nel[A]] = ???
    def map[A, B](fa: Nel[A])(f: A => B): Nel[B] = ???
  }
}
