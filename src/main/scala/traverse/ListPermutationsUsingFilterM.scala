package traverse


import cats.implicits._

/**
  * Generate all permutations of the list using filterM
  *
  * MonadFilter filterM moved to TraverseFilter filterA: https://github.com/typelevel/cats/blob/master/CHANGES.md
  *
  * Explanations:
  *   http://stackoverflow.com/questions/28872396/haskells-filterm-with-filterm-x-true-false-1-2-3
  *   (Brent Yorgey in Haskell) https://byorgey.wordpress.com/2007/06/26/deducing-code-from-types-filterm/
  */
object ListPermutationsUsingFilterM extends App {
  val res: List[List[Int]] = List(1, 2, 3).filterA(_ => List(true, false))
  println(res)
}
