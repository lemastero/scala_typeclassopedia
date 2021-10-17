package monad

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import zio.prelude._

class CustomMonadImplementationSpec extends AnyFunSpec with Matchers {

  trait Maybe[+A]
  case object Empty extends Maybe[Nothing]
  case class Value[+A](a: A) extends Maybe[A]

  implicit val maybeCovariant: Covariant[Maybe] = new Covariant[Maybe] {
    override def map[A, B](f: A => B): Maybe[A] => Maybe[B] = {
      case Empty => Empty
      case Value(a) => Value(f(a))
    }
  }

  implicit val maybeMonad: IdentityFlatten[Maybe] = new IdentityFlatten[Maybe] {
    override def any: Maybe[Any] = Value(())

    override def flatten[A](ffa: Maybe[Maybe[A]]): Maybe[A] = ffa match {
      case Empty => Empty
      case Value(a) => a
    }
  }

  val radiusList: Maybe[Int] = Value(42)

  def pure[A](a: A): Maybe[A] = IdentityFlatten[Maybe].any.map(Function.const(a))

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

      radiusList.flatMap(a => Value(a * Math.PI)) mustBe radiusList.map(_ * Math.PI)

      pure(pure(42)).flatten mustBe pure(42)
    }
  }

  describe("Monad laws examples") {

    it("tests Monad laws") {
      def circleArea: Int => Maybe[Double] =
        x => Value(Math.PI * x * x)
      def show: Double => Maybe[String] = x => Value(x.toString)

      /* flatMap associativity:

              flatMap(f)          flatMap(g)
      M[A] --------------> M[B] --------------> M[C]
       |                                         |
       |                                         |
       |                                         |
        -----------------------------------------
          flatMap( f andThen flatMap(g) )            */

      val result1 = for {
        radius <- radiusList
        result <- circleArea(radius)
        resultStr <- show(result)
      } yield resultStr

      val result2 = radiusList.flatMap( e =>
        for {
          a <- circleArea(e)
          r <- show(a)
        } yield r
      )

      result1 mustBe result2

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


      pure(42).flatMap(circleArea) mustBe circleArea(42)

      /* right identity:

              flatMap( pure )
       F[A] --------------------> F[A]  */

      radiusList.flatMap(pure) mustBe radiusList
    }
  }
}
