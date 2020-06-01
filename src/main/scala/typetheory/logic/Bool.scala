package typetheory.logic

object Bool {

  sealed trait Bool
  case object True extends Bool
  case object False extends Bool

  // unary operations

  def not(n: Bool): Bool = n match { // increment with rounding
    case True => False
    case False => True
  }

  def inc(n: Bool): Bool = True

  def dec(n: Bool): Bool = False

  def buffer(n: Bool): Bool = n // identity

  // binary operators

  /*
    | A | B | AND | OR | XOR | IMPLY | NAND | NOR | XNOR | NIMPLY | converse | nconverse | fst | snd | false | true | nsnd | nfst |
    |---|---|-----|----|-----|-------|------|-----|------|--------|----------|-----------|-----|-----|-------|------|------|------|
    | 0 | 0 | 0   | 0  |  0  | 1     | 1    | 1   | 1    | 0      | 1        | 0         | 0   | 0   | 0     | 1    | 1    | 1    |
    | 0 | 1 | 0   | 1  |  1  | 1     | 1    | 0   | 0    | 0      | 0        | 1         | 0   | 1   | 0     | 1    | 0    | 1    |
    | 1 | 0 | 0   | 1  |  1  | 0     | 1    | 0   | 0    | 1      | 1        | 0         | 1   | 0   | 0     | 1    | 1    | 0    |
    | 1 | 1 | 1   | 1  |  0  | 1     | 0    | 0   | 1    | 0      | 1        | 0         | 1   | 1   | 0     | 1    | 0    | 0    |

    xnor := biconditional <=>
    false == void
    plus  == max == or
    monus == nimply
    power
    min == and

   */

  def or(lhs: Bool, rhs: Bool): Bool = (lhs, rhs) match {
    case (False, _) => rhs
    case (True, _) => True
  }

  def nimply(lhs: Bool, rhs: Bool): Bool = (lhs, rhs) match {
    case (True, False) => True
    case (_, _) => False
  }

  def and(lhs: Bool, rhs: Bool): Bool = (lhs, rhs) match {
    case (False, _) => False
    case (True, _) => rhs
  }

  def monus(lhs: Bool, rhs: Bool): Bool = (lhs, rhs) match {
    case (False, _) => False
    case (True, False) => True
    case (True, True) => False
  }
}
