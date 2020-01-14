package educational.data

import educational.category_theory.contra.Contravariant

sealed trait Ordering
case object LT extends Ordering
case object EQ extends Ordering
case object GT extends Ordering

final case class Comparison[A](
  runComparison: (A, A) => Ordering
)

object Comparison {

  val comparisonContravariant: Contravariant[Comparison] = new Contravariant[Comparison] {
    def contramap[A, B](fa: Comparison[A])(f: B => A): Comparison[B] = {
      Comparison[B]( (lhs, rhs) =>
        fa.runComparison(f(lhs), f(rhs))
      )
    }
  }
}
