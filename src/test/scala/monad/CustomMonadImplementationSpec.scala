package monad

import educational.category_theory.Monad
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class CustomMonadImplementationSpec
  extends AnyFunSpec
  with Matchers {

  val listMonad: Monad[List] = new Monad[List] {
    def pure[A](a: A): List[A] = List(a)
    def flatMap[A, B](ma: List[A])(f: A => List[B]): List[B] = ma.flatMap(f)
  }

  val radiusList: List[Int] = (1 to 3).toList

  describe("Monad derived methods") {

    it("flatMap can be implemented using flatten and pure") {
      /*          pure
           A   ----------> M[A]
           |                |
      pure |                |  pure
           |                |
           \/               \/
          M[A] <---------- M[M[A]]
                 flatten                */

      listMonad.flatMap(radiusList)(a => List(a * Math.PI)) mustBe radiusList.map(_ * Math.PI)

      listMonad.flatten(
        listMonad.pure(
          listMonad.pure(42))) mustBe listMonad.pure(42)
    }
  }

  describe("Monad laws examples") {

    it("for Monad[List]") {
      def circleSize: Int => List[Double] = x => List(Math.PI * x * x, 2 * Math.PI * x)
      def show: Double => List[String] = x => List(x.toString)

      /* flatMap associativity:

              flatMap(f)          flatMap(g)
      M[A] --------------> M[B] --------------> M[C]
       |                                         |
       |                                         |
       |                                         |
        -----------------------------------------
          flatMap( f andThen flatMap(g) )            */

      listMonad
        .flatMap(radiusList)(circleSize)
        .flatMap(show) mustBe listMonad.flatMap(radiusList)( e => listMonad.flatMap(circleSize(e))(show) )

      /* left identity:

            pure
      A ----------> M[A]
      |            /
      |          /
   f  |        /
      |      /   flatMap(f)
      |    /
      |  /
      M[B]                               */

      listMonad.flatMap( listMonad.pure(42) )(circleSize) mustBe circleSize(42)

      /* right identity:

              flatMap( pure )
       F[A] --------------------> F[A]  */

      listMonad.flatMap(radiusList)(listMonad.pure) mustBe radiusList
    }
  }
}
