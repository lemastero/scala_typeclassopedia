package typetheory

/**
  * Lambda calculus
  *
  * BNF:
  * M,N ::= x | λx.M | (M N)
  *
  * mathematical model:
  * M[X] = X + (X * M) + (M * M)
  */
sealed trait LambdaCalc[X]
case class Mention[X](x: X) extends LambdaCalc[X]
case class Abstraction[X](x: X, m : LambdaCalc[X]) extends LambdaCalc[X]
case class Application[X](m: LambdaCalc[X], n: LambdaCalc[X]) extends LambdaCalc[X]

object LambdaCalc {
  def freeNames[X](x: LambdaCalc[X]): Set[X] = x match {
    case Mention(lx) => Set(lx)
    case Abstraction(lx, lm) => freeNames(lm) - lx
    case Application(m, n) => freeNames(m) ++ freeNames(n)
  }
}
