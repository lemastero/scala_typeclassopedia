package contravariant

import org.scalatest.{FunSpec, MustMatchers}
import scalaz.Contravariant

class ContravariantSpec
  extends FunSpec
    with MustMatchers {

  case class Predicate[A](fun: A => Boolean)

  describe("Contravariant") {

    describe("custoom contramap for Predicate on String and Int") {
      it("apply ") {
        val pl = Predicate[String](_.length > 5)
        pl.fun("Contravariant") mustBe true

        val pr1 = Predicate[Int](_ > 5)
        pr1.fun(42) mustBe true

        val len: String => Int = _.length

        def contramap(pred: Predicate[Int], f: String => Int): Predicate[String] =
          Predicate[String](f andThen pred.fun)

        val pr = contramap(pr1, len)
        pr.fun("Contravariant") mustBe true
      }

      it("custom contramap for Predicate on Person and Balance") {
        case class Person(name: String)
        case class Balance(amount: Int)

        val balanceOverdrawn = Predicate[Balance](_.amount > 0)

        val getAccountBalance: Person => Balance = p =>
          if(p.name.startsWith("A")) Balance(-1)
          else Balance(42)

        def contramap[A, B](pred: Predicate[A])(f: B => A): Predicate[B] =
          Predicate[B](f andThen pred.fun)

        val hasOverdrawnBalance = contramap(balanceOverdrawn)(getAccountBalance)

        hasOverdrawnBalance.fun(Person("Alice")) mustBe false
        hasOverdrawnBalance.fun(Person("Bob")) mustBe true
      }

      it("contramap with Contravariant instance") {

        implicit val predicateContravariantFunctor: Contravariant[Predicate] = new Contravariant[Predicate] {
          def contramap[A, B](pred: Predicate[A])(fba: B => A): Predicate[B] = Predicate[B](fba andThen pred.fun)
        }

        case class Person(name: String)
        case class Balance(amount: Int)

        val balanceOverdrawn = Predicate[Balance](_.amount > 0)

        val getAccountBalance: Person => Balance = p =>
          if(p.name.startsWith("A")) Balance(-1)
          else Balance(42)

        val hasOverdrawnBalance = Contravariant[Predicate].contramap(balanceOverdrawn)(getAccountBalance)

        hasOverdrawnBalance.fun(Person("Alice")) mustBe false
        hasOverdrawnBalance.fun(Person("Bob")) mustBe true
      }
    }
  }
}
