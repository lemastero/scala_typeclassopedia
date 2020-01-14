package educational.collections

import educational.category_theory.Comonad

case class RoseTree[A](tip: A, subTrees: List[RoseTree[A]] = Nil)

object RoseTreeInstances {

  val roseTreeComonad: Comonad[RoseTree] = new Comonad[RoseTree] {
    def extract[A](na: RoseTree[A]): A = na.tip
    def duplicate[A](na: RoseTree[A]): RoseTree[RoseTree[A]] = RoseTree(na, na.subTrees.map(duplicate))
    def map[A, B](na: RoseTree[A])(f: A => B): RoseTree[B] = RoseTree(f(na.tip), na.subTrees.map(s => map(s)(f)))
  }
}