package mtl

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

/**
  * Generate all permutations of the list using filterM
  *
  * MonadFilter filterM moved to TraverseFilter filterA: https://github.com/typelevel/cats/blob/master/CHANGES.md
  *
  * Explanations:
  *   http://stackoverflow.com/questions/28872396/haskells-filterm-with-filterm-x-true-false-1-2-3
  *   (Brent Yorgey in Haskell) https://byorgey.wordpress.com/2007/06/26/deducing-code-from-types-filterm/
  */
class TraverseEmptyListPermutationsSpec
  extends AnyFunSpec
  with Matchers {

  describe("filterA") {
    it("compute all permutations of the lit if given List(true, false)") {
      import cats._
      import cats.implicits._

      val allPermutations = List(
        List(1, 2, 3),
        List(1, 2),
        List(1, 3),
        List(2, 3),
        List(1),
        List(2),
        List(3),
        Nil
      )
      val result: List[List[Int]] = List(1, 2, 3).filterA(_ => List(true, false))
      result contains theSameElementsAs (allPermutations)
    }
  }
}