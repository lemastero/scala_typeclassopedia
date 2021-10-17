package profunctor

import cats.arrow.Profunctor
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ClosedProfunctorSpec
  extends AnyFunSpec
  with Matchers {

  describe("Closed Profunctor") {
    trait Closed[=>:[_,_]] extends Profunctor[=>:] {
      def closed[A,B,C]: A=>:B => (C=>A) =>: (C => B)
    }

    it("for Function1 closed is just composition of arguments") {
      val function1Closed: Closed[Function1] = new Closed[Function] {
        def closed[A,B,C]: (A => B) => ((C => A) => (C => B)) = ab => _ andThen ab
        def dimap[A,B,C,D](ab: A => B)(ca: C => A)(bd: B => D): C => D = (ca andThen ab) andThen bd
      }

      def len: String => Int = _.length
      def res[A]: (A => String) => A => Int = function1Closed.closed[String,Int,A](len)

      case class Person(name: String)
      def getName: Person => String = _.name
      val getNameLength: Person => Int = res(getName)
      getNameLength(Person("Fred")) mustBe 4
    }

    it("closed profunctor is a TambaraModule with tensor inverted Funcion1") {
      trait TambaraModule[=>:[_,_], ⊗[_,_]] {
        def runTambara[A,B,C]: A=>:B => (A⊗C) =>: (B⊗C)
      }

      type Function1Op[A,B] = B => A
      def closedTambara[P[_,_]](implicit CP: Closed[P]) = new TambaraModule[P, Function1Op] {
        def runTambara[A, B, C]: P[A, B] => P[C => A, C => B] = CP.closed
      }
    }
  }
}
