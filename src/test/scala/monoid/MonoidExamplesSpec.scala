package monoid

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class MonoidExamplesSpec
  extends AnyFunSpec
  with Matchers {

  describe("combineAll") {
    it("invoke operation for all elements") {
      import cats.implicits._

      List(1, 2, 3).combineAll mustBe 6
      List[Int]().combineAll mustBe 0
    }
  }
}
