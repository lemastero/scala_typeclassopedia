package cayley

import cats.MonoidK

object CayleyTheorem {

  /* law: abs compose rep == id */
  type DifferenceList[A] = List[A] => List[A]
  def rep[A](xs: List[A]): DifferenceList[A] = ys => xs ++ ys
  def abs[A](xs: DifferenceList[A]): List[A] = xs(Nil)

  /**
    * Laws:
    * rep(abs(a)) == a
    * abs(rep(a)) == a
    */
  trait CayleyTheoremForMonoid[M[_]] extends MonoidK[M] {
    type MonoidEndomorphism[A] = M[A] => M[A]
    def rep[A](xs: M[A]): MonoidEndomorphism[A] = ys => combineK(xs, ys)
    def abs[A](xs: MonoidEndomorphism[A]): M[A] = xs(empty)
  }
}
