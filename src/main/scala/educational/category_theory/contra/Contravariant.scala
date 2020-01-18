package educational.category_theory.contra

/*
  class Contravariant f where
    contramap :: (b -> a) -> f a -> f b
 */
trait Contravariant[F[_]] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
}

trait ContravariantLaws[F[_]] extends Contravariant[F]{

  // contramap id = id
  def contramapIdentity[A](fa: F[A]): Boolean = {
    //         contramap(id)
    // F[A]  ================> F[A]
    contramap(fa)(identity[A]) == fa
  }

  // contramap f . contramap g = contramap (g . f)
  def contravariantComposite[A, B, C](fa: F[A], f: B => A, g: C => B): Boolean = {

    //        contramap f
    // F[A] ==============> F[B]
    val fb: F[B] = contramap(fa)(f)

    //        contramap g
    // F[B] ===============> F[C]
    val l: F[C] = contramap(fb)(g)

    //        contramap (g . f)
    // F[A] =====================> F[B]
    val r: F[C] = contramap(fa)(f compose g)

    l == r
  }
}

object Contravariant {

  def fun1Contravariant[R]: Contravariant[Function1[*, R]] = new Contravariant[* => R] {
    def contramap[A, AA](fa: A => R)(f: AA => A): AA => R = f andThen fa
  }

  val EquivContravariant: Contravariant[Equiv] = new Contravariant[Equiv] {
    def contramap[A, B](fa: Equiv[A])(f: B => A): Equiv[B] =
      (x: B, y: B) => fa.equiv(f(x), f(y))
  }

  val OrderingContravariant: Contravariant[Ordering] = new Contravariant[Ordering] {
    def contramap[A, B](fa: Ordering[A])(f: B => A): Ordering[B] =
      (x: B, y: B) => fa.compare(f(x), f(y))
  }

  val PartialOrderingContravariant: Contravariant[PartialOrdering] = new Contravariant[PartialOrdering] {
    override def contramap[A, B](fa: PartialOrdering[A])(f: B => A): PartialOrdering[B] = new PartialOrdering[B] {
      def tryCompare(x: B, y: B): Option[Int] = fa.tryCompare(f(x),f(y))
      def lteq(x: B, y: B): Boolean = fa.lteq(f(x),f(y))
    }
  }
}