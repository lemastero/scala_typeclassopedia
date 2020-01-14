package educational.data

import educational.category_theory.contra.Contravariant

final case class Predicate[-A](fun: A => Boolean)

object PredicateInstances {

  val predicateContravariant: Contravariant[Predicate] = new Contravariant[Predicate] {
    def contramap[A, B](pred: Predicate[A])(fba: B => A): Predicate[B] =
      Predicate[B](fba andThen pred.fun)
  }
}
