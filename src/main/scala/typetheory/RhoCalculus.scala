package typetheory

/**
  * Rho calculus
  *
  * BNF:
  * P,Q ::= 0 | for (y <- x)P | x!(Q) | P|Q | *x
  *
  * mathematical model:
  * P[X] =
  *
  */
sealed trait RhoCalc[X]
case object Zero extends RhoCalc[Nothing]
case class ForComprehension[X](y: X, x: X, p: RhoCalc[X]) extends RhoCalc[X]
case class Bang[X](x: X, q: RhoCalc[X]) extends RhoCalc[X]
case class Par[X](p: RhoCalc[X], q: RhoCalc[X]) extends RhoCalc[X]
case class Star[X](p: X) extends RhoCalc[X]
