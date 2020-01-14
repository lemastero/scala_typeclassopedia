package educational.data

import educational.category_theory.Comonad

case class Id[A](value: A)

object IdInstances {
  val idComonad: Comonad[Id] = new Comonad[Id] {
    def map[A, B](x: Id[A])(f: A => B): Id[B] = Id(f(x.value))
    def extract[A](w: Id[A]): A = w.value
    def duplicate[A](wa: Id[A]): Id[Id[A]] = Id(wa)
  }
}