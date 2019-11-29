package comonad

import educational.Functor

import scala.language.higherKinds

object ComonadSimpleImpl {

  trait Comonad[W[_]] extends Functor[W] {
    def extract[A](w: W[A]): A
    def duplicate[A](wa: W[A]): W[W[A]]
    def extend[A, B](w: W[A])(f: W[A] => B): W[B] = map(duplicate(w))(f) // coKleisi composition
  }
}
