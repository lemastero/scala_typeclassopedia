package monoid

import cats.Monoid

object AlternativeMonoidInstances {
  def createMonoid[A](emptyVal: A, comineFun: (A, A) => A): Monoid[A] = new Monoid[A] {
    override def empty: A = emptyVal
    override def combine(x: A, y: A) = comineFun(x, y)
  }

  implicit val multiplyIntMonoid: Monoid[Int] = createMonoid(1, _ * _)

  implicit def optionFirstNonEmpty[A]: Monoid[Option[A]] = createMonoid(None, _ orElse _)
}
