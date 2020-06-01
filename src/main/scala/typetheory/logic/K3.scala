package typetheory.logic

import typetheory.logic.Bool.{Bool, False, True}

/**
  * Kleene strong K3 logic
  * Priest P3 logic
  */
object K3 {
  sealed trait K3
  case object T extends K3
  case object F extends K3
  case object I extends K3

  type P3 = K3

  def not(a: K3): K3 = a match {
    case T => F
    case I => I
    case F => T
  }

  def and(a: K3, b: K3): K3 = (a,b) match {
    case (T, b) => b
    case (I,F) => F
    case (I,_) => I
    case (F,_) => F
  }

  def or(a: K3, b: K3): K3 = (a,b) match {
    case (T, _) => T
    case (I,T) => T
    case (I,_) => I
    case (F,b) => b
  }

  def impl(a: K3, b: K3): K3 = (a,b) match {
    case (T, b) => b
    case (I,T) => T
    case (I,_) => I
    case (F,_) => T
  }

  def bicond(a: K3, b: K3): K3 = (a,b) match {
    case (T, b) => b
    case (I,_) => I
    case (F,T) => F
    case (F,I) => I
    case (F,F) => T
  }

  // no tautologies
  def isKleeneTruth(a: K3): Bool =
    a match {
      case T => True
      case _ => False
    }

  // same tautologies as classic logic
  def isPriestTruth(a: K3): Bool =
    a match {
      case T => True
      case I => True
      case F => False
    }
}
