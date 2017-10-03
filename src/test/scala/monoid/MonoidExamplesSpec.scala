package monoid

import org.scalatest.{FunSpec, MustMatchers}

class MonoidExamplesSpec
  extends FunSpec
  with MustMatchers {

  describe("combineAll") {
    it("invoke operation for all elements") {
      import cats.implicits._

      List(1, 2, 3).combineAll mustBe 6
      List[Int]().combineAll mustBe 0
    }
  }
}
