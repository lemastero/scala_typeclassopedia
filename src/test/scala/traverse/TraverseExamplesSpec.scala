package traverse

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class TraverseExamplesSpec
  extends AnyFunSpec
  with Matchers {

  describe("Traversing List ") {
    it("traverse") {
      import cats.implicits._

      val xs1: Vector[Int] = Vector(1, 2, 3)
      xs1.traverse(a => Option(a)) mustBe Some(Vector(1, 2, 3))
    }

    it("sequence") {
      import cats.implicits._
      val xs1: List[Option[Int]] = List(Some(1), Some(2), None)
      xs1.sequence mustBe None

      val xs2: List[Option[Int]] = List(Some(1), Some(2), Some(42))
      xs2.sequence mustBe Some(List(1,2,42))
    }
  }
}
