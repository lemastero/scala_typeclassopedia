package typetheory.logic

/** Bochvar's internal three-valued logic */
object B3 {

  sealed trait B3
  case object T extends B3
  case object F extends B3
  case object I extends B3

  def not(a: B3): B3 = a match {
    case T => F
    case I => I
    case F => T
  }

  def and(a: B3, b: B3): B3 = (a,b) match {
    case (T, b) => b
    case (I, _) => I
    case (F, I) => I
    case (F, _) => F
  }

  def or(a: B3, b: B3): B3 = (a,b) match {
    case (T, I) => I
    case (T, _) => T
    case (I, _) => I
    case (F, b) => b
  }

  def impl(a: B3, b: B3): B3 = (a,b) match {
    case (T, b) => b
    case (I, _) => I
    case (F, I) => I
    case (F, _) => T
  }

  def bicond(a: B3, b: B3): B3 = (a,b) match {
    case (T, b) => b
    case (I,_) => I
    case (F,T) => F
    case (F,I) => I
    case (F,F) => T
  }
}
