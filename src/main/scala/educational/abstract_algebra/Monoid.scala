package educational.abstract_algebra

trait Monoid[M] extends Semigroup[M] {
  def empty: M
}

object MonoidInstances {
  def createMonoid[A](mempty: A, mapply: (A, A) => A): Monoid[A] = new Monoid[A] {
    def empty: A = mempty
    def combine(x: A, y: A): A = mapply(x, y)
  }

  implicit val multiplyIntMonoid: Monoid[Int] = createMonoid(1, _ * _)

  implicit def optionFirstNonEmpty[A]: Monoid[Option[A]] = createMonoid(None, _ orElse _)

  implicit val maxLongMonoid: Monoid[Long] = createMonoid(Long.MinValue, Math.max)

  implicit val minIntMonoid: Monoid[Int] = createMonoid(Int.MaxValue, Math.min)

  implicit val andBoolMonoid: Monoid[Boolean] = createMonoid(true, _ && _)

  implicit val orBoolMonoid: Monoid[Boolean] = createMonoid(false, _ || _)

  implicit def endoFunction[A]: Monoid[A => A] = createMonoid(identity[A], _ andThen _)

  /**
    * Create Monoid for tuple from two Monoids
    */
  implicit def createMonoidTuple[L, R](left: Monoid[L], right: Monoid[R]): Monoid[(L, R)] = new Monoid[(L, R)] {
    def empty: (L, R) = (left.empty, right.empty)
    def combine(x: (L, R), y: (L, R)): (L, R) =
      ( left.combine(x._1, y._1), right.combine(x._2, y._2) )
  }
}
