package comonad

import educational.abstract_algebra.Monoid
import educational.category_theory.Comonad
import educational.collections.{AbstractNel, HeadNel, RoseTree, TailNel}
import educational.data.{CoReader, Id}
import educational.data.CoReaderInstances.coReaderComonad
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ComonadCustomImplSpec
  extends AnyFunSpec
    with Matchers {

  describe("Comonad") {

    import educational.data.IdInstances.idComonad

    it("Identity behaves as comonad") {
      idComonad.extract(Id(42)) mustBe 42
      idComonad.map(Id("foo"))(_.length) mustBe Id(3)
      idComonad.duplicate(Id(42)) mustBe Id(Id(42))
    }

    it("CoReader behaves as comonad") {
      val cor = CoReader(extract = 42, ask = "foo")
      coReaderComonad.extract(cor) mustBe 42
      coReaderComonad.map(cor)(_ * 10) mustBe CoReader(extract = 420, ask = "foo")
      coReaderComonad.duplicate(cor) mustBe CoReader(extract = CoReader(extract = 42, ask = "foo"), ask = "foo")
    }

    case class Cowriter[W, A](tell: W => A)(implicit m: Monoid[W]) {
      def extract: A = tell(m.empty)
      def duplicate: Cowriter[W, Cowriter[W, A]] = Cowriter( w1 =>
        Cowriter( w2 =>
          tell(m.combine(w1, w2))
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

      val corDuplicated = coReaderComonad.duplicate(cor) // CoReader(CoReader(42,foo),foo)
      val dupAndExtract = coReaderComonad.extract(corDuplicated)
      dupAndExtract mustBe cor

      /* Right identity law: wa.extend(extract) == wa

                      extend(extract)
              W[A]  ------------------> W[A]         */

      coReaderComonad.extend(cor)(coReaderComonad.extract) mustBe cor

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

      val corDuplicated2 = coReaderComonad.duplicate(cor)                // CoReader(CoReader(42,foo),foo)
      val codDuplicatedTwice = coReaderComonad.duplicate(corDuplicated2) // CoReader(CoReader(CoReader(42,foo),foo),foo)
      val extendedDuplicate = coReaderComonad.extend(cor)(coReaderComonad.duplicate)
      codDuplicatedTwice mustBe extendedDuplicate
    }

    it("Comonad laws from Haskell source") {

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
    val nelComonad = new Comonad[AbstractNel] {
      def extract[A](na: AbstractNel[A]): A = na.head

      def duplicate[A](na: AbstractNel[A]): AbstractNel[AbstractNel[A]] = na match {
        case p @ HeadNel(_, tail) => HeadNel(p, duplicate(tail))
        case other => TailNel(other)
      }

      def map[A, B](na: AbstractNel[A])(f: A => B): AbstractNel[B] = na match {
        case TailNel(head) => TailNel(f(head))
        case HeadNel(head, tail) => HeadNel(f(head), map(tail)(f))
      }
    }

    it("NonEmptyList is a comonad") {
      val nel: AbstractNel[Int] = TailNel(1) + (TailNel(2) + TailNel(3))

      nelComonad.extract(nel) mustBe 1
      nelComonad.map(nel)(_ * 10) mustBe TailNel(10) + (TailNel(20) + TailNel(30))

      val n2: AbstractNel[Int] = TailNel(2) + TailNel(3)
      val n3: AbstractNel[Int] = TailNel(3)
      nelComonad.duplicate(nel) mustBe TailNel(nel) + (TailNel(n2) + TailNel(n3))
    }
  }

  describe("Rose Tree") {
    it("is a comonad") {
      import educational.collections.RoseTreeInstances.roseTreeComonad
      def rt[A](a: A): RoseTree[A] = RoseTree(a, Nil)

      val tree: RoseTree[Int] = RoseTree(1, List(rt(2), rt(3), rt(4)))

      roseTreeComonad.extract(tree) mustBe 1
      roseTreeComonad.map(tree)(_ * 10) mustBe RoseTree(10, List(rt(20), rt(30), rt(40)))
      roseTreeComonad.duplicate(tree) mustBe RoseTree(
        tree,
        List(
          rt(rt(2)),
          rt(rt(3)),
          rt(rt(4))))
    }
  }
}
