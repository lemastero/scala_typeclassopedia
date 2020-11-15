package contravariant

import zio.prelude._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ContravariantSpec extends AnyFunSpec with Matchers {

  case class Predicate[-A](fun: A => Boolean)

  describe("Contravariant") {
    it("split complex Predicate into simpler + function - custom contramap") {
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

      val getAccountBalance: Person => Balance =
        p => // that hows Banks works :)
          if (p.name.startsWith("A")) Balance(-1)
          else Balance(42)

      def contramap[A, B](pred: Predicate[A])(f: B => A): Predicate[B] =
        Predicate[B](f andThen pred.fun)

      val hasOverdrawnBalance =
        contramap(balanceOverdrawnPred)(getAccountBalance)

      hasOverdrawnBalance.fun(Person("Alice")) mustBe false
      hasOverdrawnBalance.fun(Person("Bob")) mustBe true
    }

    it("contramap with Contravariant instance") {
      implicit val predicateContravariant: Contravariant[Predicate] =
        new Contravariant[Predicate] {
          def contramap[A, B](ba: B => A): Predicate[A] => Predicate[B] = pred =>
            Predicate[B](ba andThen pred.fun)
        }

      case class Person(name: String)
      case class Balance(amount: Int)

      val balanceOverdrawn = Predicate[Balance](_.amount > 0)

      val getAccountBalance: Person => Balance = p =>
        if (p.name.startsWith("A")) Balance(-1)
        else Balance(42)

      val hasOverdrawnBalance: Predicate[Person] = Contravariant[Predicate]
        .contramap(getAccountBalance)(balanceOverdrawn)

      hasOverdrawnBalance.fun(Person("Alice")) mustBe false
      hasOverdrawnBalance.fun(Person("Bob")) mustBe true
    }

    it("contramap for custom Show") {
      // Show using declaration side variance
      trait Show[-T] {
        def show(a: T): String
      }

      object Show {
        def show[A,B](f: A => String): Show[A] = new Show[A] {
          override def show(a: A): String = f(a)
        }
      }

      implicit class ShowOpts[A](a: A) {
        def show(implicit ST: Show[A]): String = ST.show(a)
      }

      // adapted `from https://typelevel.org/cats/typeclasses/contravariant.html#contravariant-instance-for-show
      // do not works with cats.Show (multiple issues one issue is lack of variance variance)
      implicit val showContravariant: Contravariant[Show] =
        new Contravariant[Show] {
          def contramap[A, B](ba: B => A): Show[A] => Show[B] = s =>
            Show.show(ba andThen s.show)
        }

      case class Money(amount: Int)
      case class Salary(size: Money)

      implicit val showMoney: Show[Money] = Show.show(m => s"${m.amount}")

      implicit val showSalary: Show[Salary] = showMoney.contramap(_.size)

      Salary(Money(42)).show mustBe "42"
    }

    it("contramap Debug") {
      implicit val showContravariant: Contravariant[Debug] =
        new Contravariant[Debug] {
          def contramap[A, B](ba: B => A): Debug[A] => Debug[B] = s =>
            Debug.make[B](ba andThen s.debug)
        }

      case class Money(amount: Int)
      case class Salary(size: Money)

      implicit val showMoney: Debug[Money] = Debug.make(m => Debug.Repr.String(s"${m.amount}"))
      implicit val showSalary: Debug[Salary] = showMoney.contramap(_.size)

      Salary(Money(42)).debug.render mustBe """"42""""
    }

    // adapted from: https://typelevel.org/cats/typeclasses/contravariant.html#contravariant-instance-for-scalamathordering
    it("contramap for Ord") {
      // Ord, Ordering example
      val intOrd: Ord[Int] = Ord.make[Int] {
        case (lhs, rhs) if lhs < rhs => Ordering.LessThan
        case (lhs, rhs) if lhs > rhs => Ordering.GreaterThan
        case (lhs, rhs) if lhs == rhs => Ordering.Equals
      }
      intOrd.compare(2,1) mustBe Ordering.GreaterThan

      val intOrd2: Ord[Int] = Ord.fromScala
      intOrd2.compare(1,2) mustBe Ordering.LessThan

      // Ord contains already contramap:
      case class Money(amount: Int)
      case class Salary(size: Money)

      implicit val moneyOrd: Ord[Money] = intOrd2.contramap(_.amount)
      implicit val salaryOrd: Ord[Salary] = moneyOrd.contramap(_.size)

      Money(100) < Money(200) mustBe true
    }

    it("narrow") {
      // TODO
    }
  }
}
