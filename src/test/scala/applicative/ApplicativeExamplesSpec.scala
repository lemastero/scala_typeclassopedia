package applicative

import org.scalatest.{FunSpec, MustMatchers}

class ApplicativeExamplesSpec
  extends FunSpec
  with MustMatchers {

  describe("derived methods") {
    it("map2 on composed Applicative") {
      import cats.Applicative
      import cats.implicits.catsStdInstancesForList
      import cats.implicits.catsStdInstancesForOption

      val listOpt = Applicative[List] compose Applicative[Option]

      val list1 = List(Some(2), None)
      val list2 = List(Some(10), Some(2))
      listOpt.map2(list1, list2)(_ + _) mustBe List(Some(12), Some(4), None, None)
    }
  }
}
