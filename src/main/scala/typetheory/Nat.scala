package typetheory

import scala.annotation.tailrec
import typetheory.logic.Bool._

object Nat {

  sealed trait Nat
  case object Zero extends Nat
  case class Succ(n: Nat) extends Nat

  // unary operations

  def inc(n: Nat): Nat = Succ(n)

  def dec(n: Nat): Nat = n match {
    case Zero => Zero
    case Succ(m) => m
  }

  // binary operators

  @tailrec
  def plus(lhs: Nat, rhs: Nat): Nat = lhs match {
    case Zero => rhs
    case Succ(m) => plus(m, Succ(rhs))
  }

  def times(lhs: Nat, rhs: Nat): Nat = (lhs, rhs) match {
    case (Zero, _) => Zero
    case (Succ(m), n) => plus(m, times(m, n))
  }

  @tailrec
  def monus(lhs: Nat, rhs: Nat): Nat = (lhs, rhs) match {
    case (Zero, _) => Zero
    case (Succ(m), Succ(n)) => monus(m,n)
  }

  // relations

  @tailrec
  def eq(lhs: Nat, rhs: Nat): Bool = (lhs, rhs) match {
    case (Zero, Succ(_)) => False
    case (Succ(m), Succ(n)) => eq(m,n)
  }

  @tailrec
  def lq(lhs: Nat, rhs: Nat): Bool = (lhs, rhs) match {
    case (Zero, Succ(_)) => True
    case (Succ(m), Succ(n)) => lq(m,n)
  }
}
