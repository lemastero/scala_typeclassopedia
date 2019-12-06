package contravariant

import scalaz.Contravariant
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ContravariantSpec
  extends AnyFunSpec
    with Matchers {

  case class Predicate[A](fun: A => Boolean)

  describe("Contravariant") {

    it("split complex Predicate into simpler + function - csutom contramap") {
      val lenAbove5 = Predicate[String](_.length > 5)
      lenAbove5.fun("123456") mustBe true

      // predicate
      val above5 = Predicate[Int](_ > 5)
      above5.fun(42) mustBe true

      // function
      val len: String => Int = _.length

      // way to combine them
      def contramap(pred: Predicate[Int], f: String => Int): Predicate[String] =
        Predicate[String](f andThen pred.fun)

      // yeach !
      val pr = contramap(above5, len)
      pr.fun("123456") mustBe true
    }

    it("custom contramap for Predicate on Person and Balance") {
      case class Person(name: String)
      case class Balance(amount: Int)

      val balanceOverdrawnPred = Predicate[Balance](_.amount > 0)

      val getAccountBalance: Person => Balance = p => // that hows Banks works :)
        if(p.name.startsWith("A")) Balance(-1)
        else Balance(42)

      def contramap[A, B](pred: Predicate[A])(f: B => A): Predicate[B] =
        Predicate[B](f andThen pred.fun)

      val hasOverdrawnBalance = contramap(balanceOverdrawnPred)(getAccountBalance)

      hasOverdrawnBalance.fun(Person("Alice")) mustBe false
      hasOverdrawnBalance.fun(Person("Bob")) mustBe true
    }

    it("contramap with Contravariant instance") {

      implicit val predicateContravariant: Contravariant[Predicate] =
        new Contravariant[Predicate] {
          def contramap[A, B](pred: Predicate[A])(fba: B => A): Predicate[B] =
            Predicate[B](fba andThen pred.fun)
        }

      case class Person(name: String)
      case class Balance(amount: Int)

      val balanceOverdrawn = Predicate[Balance](_.amount > 0)

      val getAccountBalance: Person => Balance = p =>
        if(p.name.startsWith("A")) Balance(-1)
        else Balance(42)

      val hasOverdrawnBalance = Contravariant[Predicate]
        .contramap(balanceOverdrawn)(getAccountBalance)

      hasOverdrawnBalance.fun(Person("Alice")) mustBe false
      hasOverdrawnBalance.fun(Person("Bob")) mustBe true
    }
  }
}
