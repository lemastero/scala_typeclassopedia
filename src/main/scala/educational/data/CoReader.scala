package educational.data

import educational.category_theory.Comonad

case class CoReader[R, A](extract: A, ask: R) // wrap value A with some context R

object CoReaderInstances {
  def coReaderComonad[R]: Comonad[CoReader[R, *]] = new Comonad[CoReader[R, *]] {
    def map[A, B](x: CoReader[R, A])(f: A => B): CoReader[R, B] = CoReader(f(x.extract), x.ask)
    def extract[A](w: CoReader[R, A]): A = w.extract
    def duplicate[A](wa: CoReader[R, A]): CoReader[R, CoReader[R, A]] = CoReader(wa, wa.ask)
  }
}
