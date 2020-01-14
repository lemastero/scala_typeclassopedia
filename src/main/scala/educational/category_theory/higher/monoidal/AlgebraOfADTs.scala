package educational.category_theory.higher.monoidal

object AlgebraOfADTs {

  // Algebra of ADT's - Tuple and Unit

  val isAssociative: Boolean = (("a", "b"), "c") != ("a", ("b", "c"))

  def assoc1[A,B,C]: ((A,B), C) => (A, (B,C)) = {
    case((a,b), c) => (a, (b,c))
  }

  def assoc2[A,B,C]: (A, (B,C)) => ((A,B), C) = {
    case(a, (b,c)) => ((a,b), c)
  }

  val isAssociativeUpToIsomorphism: Boolean = (("a", "b"), "c") == assoc2("a", ("b", "c"))

  def leftId[A](x: (A, Unit)): A = x._1
  def leftId_inv[A](a: A): (A, Unit) = (a, ())

  def rihtId[A](x: (Unit, A)): A = x._2
  def rihtId_inv[A](a: A): (Unit, A) = ((), a)

  val isLeftIdentity = leftId_inv(42) == (42, ())
  val isRightIdentity = rihtId_inv(42) == ((), 42)

  def swap[A,B]: (A,B) => (B,A) = { case(a,b) => (b, a) }

  // Algebra of ADT's - Either and Void
}
