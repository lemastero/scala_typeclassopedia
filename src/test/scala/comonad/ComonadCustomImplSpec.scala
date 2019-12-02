package comonad

import educational.Functor
import simple.Id
import semigroup.MonoidSimpleImpl.Monoid

import scala.language.higherKinds
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ComonadCustomImplSpec
  extends AnyFunSpec
    with Matchers {

  /* Comonads are dual to Monads
   ------------------------------------------
   |  Monad            |    Comonad         |
   |----------------------------------------|
   | put valus inside  | get out value      |
   | remove one layer  | add one more layer |
   ------------------------------------------

   trait Monad[M[_]] extends Functor[M] {
    def pure[A](a: A): M[A]
    def flatten[A](mm: M[M[A]]): M[A]
  }
   */

  trait Comonad[W[_]] extends Functor[W] {
    def extract[A](w: W[A]): A
    def duplicate[A](wa: W[A]): W[W[A]]
    def extend[A, B](w: W[A])(f: W[A] => B): W[B] = map(duplicate(w))(f) // coKleisi composition
  }

  describe("Comonad") {

    val idComonad = new Comonad[Id] {
      def map[A, B](x: Id[A])(f: A => B): Id[B] = Id(f(x.value))
      def extract[A](w: Id[A]): A = w.value
      def duplicate[A](wa: Id[A]): Id[Id[A]] = Id(wa)
    }

    it("Identity behaves as comonad") {
      idComonad.extract(Id(42)) mustBe 42
      idComonad.map(Id("foo"))(_.length) mustBe Id(3)
      idComonad.duplicate(Id(42)) mustBe Id(Id(42))
    }

    case class CoReader[R, A](extract: A, ask: R) // wrap value A with some context R

    def coReader[R] = new Comonad[CoReader[R, ?]] {
      def map[A, B](x: CoReader[R, A])(f: A => B): CoReader[R, B] = CoReader(f(x.extract), x.ask)
      def extract[A](w: CoReader[R, A]): A = w.extract
      def duplicate[A](wa: CoReader[R, A]): CoReader[R, CoReader[R, A]] = CoReader(wa, wa.ask)
    }

    it("CoReader behaves as comonad") {
      val cor = CoReader(extract = 42, ask = "foo")
      coReader.extract(cor) mustBe 42
      coReader.map(cor)(_ * 10) mustBe CoReader(extract = 420, ask = "foo")
      coReader.duplicate(cor) mustBe CoReader(extract = CoReader(extract = 42, ask = "foo"), ask = "foo")
    }

    case class Cowriter[W, A](tell: W => A)(implicit m: Monoid[W]) {
      def extract: A = tell(m.empty)
      def duplicate: Cowriter[W, Cowriter[W, A]] = Cowriter( w1 =>
        Cowriter( w2 =>
          tell(m.append(w1, w2))
        )
      )
      def map[B](f: A => B) = Cowriter(tell andThen f)
    }

    it("CoWriter behaves as comonad") {
      // TODO
    }

    case class NEL[A](head: A, tail: Option[NEL[A]]) {
      def extract: A = head
      def duplicate: NEL[NEL[A]] = NEL(this, tail.map(_.duplicate))
      def map[B](f: A => B): NEL[B] = NEL(f(head), tail.map(in => in.map(f)))
      def extend[B](f: NEL[A] => B): NEL[B] = duplicate.map(f)
    }

    val nelComonad: Comonad[NEL] = new Comonad[NEL] {
      def extract[A](na: NEL[A]): A = na.extract
      def duplicate[A](na: NEL[A]): NEL[NEL[A]] = na.duplicate
      def map[A, B](na: NEL[A])(f: A => B): NEL[B] = na.map(f)
    }

    it("NonEmptyList is a comonad") {
      val nel = NEL(1, Some(NEL(2, Some(NEL(3, None)))))
      nelComonad.extract(nel) mustBe 1
      nelComonad.map(nel)(_ * 10) mustBe NEL(10, Some(NEL(20, Some(NEL(30, None)))))

      val n2 = NEL(2, Some(NEL(3, None)))
      val n3 = NEL(3, None)
      nelComonad.duplicate(nel) mustBe NEL(nel, Some(NEL(n2, Some(NEL(n3, None)))))
    }

    it("Comonad laws for coreader - from Higher order blog") {
      val cor = CoReader(extract = 42, ask = "foo")

      /* Left identity law: wa.duplicate.extract == wa

            duplicate
          ------------>
    W[A]                 W[W[A]]
          <------------
             extract                           */

      val corDuplicated = coReader.duplicate(cor) // CoReader(CoReader(42,foo),foo)
      val dupAndExtract = coReader.extract(corDuplicated)
      dupAndExtract mustBe cor

      /* Right identity law: wa.extend(extract) == wa

                      extend(extract)
              W[A]  ------------------> W[A]         */

      coReader.extend(cor)(coReader.extract) mustBe cor

      /* Associativity law: wa.duplicate.duplicate == wa.extend(duplicate)

                 duplicate
          W[A] ------------> W[W[A]]
            \                    |
                \                |  duplicate
                    \            |
    extend(duplicate)   \        |
                           \|    \/
                           W[W[W[A]]]

       */

      val corDuplicated2 = coReader.duplicate(cor)                // CoReader(CoReader(42,foo),foo)
      val codDuplicatedTwice = coReader.duplicate(corDuplicated2) // CoReader(CoReader(CoReader(42,foo),foo),foo)
      val extendedDuplicate = coReader.extend(cor)(coReader.duplicate)
      codDuplicatedTwice mustBe extendedDuplicate
    }

    it("Comonad laws from Haskell source") {
      // https://hackage.haskell.org/package/comonad/docs/Control-Comonad.html

      val fa = NEL(1, Some(NEL(2, Some(NEL(3, None)))))

      def f: NEL[Int] => String = _.toString
      def f2: Int => String = _.toString
      def g: NEL[String] => Int = fa => fa.extract.length

      fa.extend(_.extract) == fa
      fa.extend(f).extract == f(fa)
      fa.extend(f).extend(g) == fa.extend(a => g(a.extend(f)))

      fa.duplicate.extract == fa
      fa.duplicate.map(_.extract) == fa
      fa.duplicate.duplicate == fa.duplicate.map(_.duplicate)

      fa.extend(f) == fa.duplicate.map(f)
      fa.duplicate == fa.extend(identity)
      fa.map(f2) == fa.extend(faInner => f2(faInner.extract))
    }

  }

  describe("Cons NEL") {

    sealed trait NEL[A]{
      def head: A

      def tailOpt: Option[NEL[A]] = this match {
        case PredNel(_, tail) => Some(tail)
        case _ => None
      }
    }

    case class TailNel[A](head: A) extends NEL[A] {
      def +(other: NEL[A]): NEL[A] = PredNel(head, other)
    }

    case class PredNel[A](head: A, tail: NEL[A]) extends NEL[A]

    val nelComonad = new Comonad[NEL] {
      def extract[A](na: NEL[A]): A = na.head

      def duplicate[A](na: NEL[A]): NEL[NEL[A]] = na match {
        case p @ PredNel(_, tail) => PredNel(p, duplicate(tail))
        case other => TailNel(other)
      }

      def map[A, B](na: NEL[A])(f: A => B): NEL[B] = na match {
        case TailNel(head) => TailNel(f(head))
        case PredNel(head, tail) => PredNel(f(head), map(tail)(f))
      }
    }

    it("NonEmptyList is a comonad") {
      val nel: NEL[Int] = TailNel(1) + (TailNel(2) + TailNel(3))

      nelComonad.extract(nel) mustBe 1
      nelComonad.map(nel)(_ * 10) mustBe TailNel(10) + (TailNel(20) + TailNel(30))

      val n2: NEL[Int] = TailNel(2) + TailNel(3)
      val n3: NEL[Int] = TailNel(3)
      nelComonad.duplicate(nel) mustBe TailNel(nel) + (TailNel(n2) + TailNel(n3))
    }
  }

  describe("Rose Tree") {

    object RoseTree {
      def apply[A](a: A): RoseTree[A] = RoseTree(a, Nil)
    }

    case class RoseTree[A](tip: A, subTrees: List[RoseTree[A]])

    val nelComonad = new Comonad[RoseTree] {
      def extract[A](na: RoseTree[A]): A = na.tip

      def duplicate[A](na: RoseTree[A]): RoseTree[RoseTree[A]] = RoseTree(na, na.subTrees.map(duplicate))

      def map[A, B](na: RoseTree[A])(f: A => B): RoseTree[B] = RoseTree(f(na.tip), na.subTrees.map(s => map(s)(f)))
    }

    it("is a comonad") {
      val tree: RoseTree[Int] = RoseTree(1, List(RoseTree(2), RoseTree(3), RoseTree(4)))

      nelComonad.extract(tree) mustBe 1
      nelComonad.map(tree)(_ * 10) mustBe RoseTree(10, List(RoseTree(20), RoseTree(30), RoseTree(40)))
      nelComonad.duplicate(tree) mustBe RoseTree(
        tree,
        List(
          RoseTree(RoseTree(2)),
          RoseTree(RoseTree(3)),
          RoseTree(RoseTree(4))))
    }
  }
}
