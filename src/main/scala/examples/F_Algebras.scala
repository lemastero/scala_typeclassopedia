package examples

object F_Algebras {

  type Algebra[F[_],A] = F[A] => A

  // F-Algebra for Monoid
  sealed trait MonF[+A]
  case object MEmpty extends MonF[Nothing]
  case class MAppend[A](lhs: A, rhs: A) extends MonF[A]

  // Int is a carrier object
  def monoidAlgebraMulti: MonF[Int] => Int = {
    case MEmpty => 1
    case MAppend(lhs, rhs) => lhs * rhs
  }

  def monoidAlgebraAdd: MonF[Int] => Int = {
    case MEmpty => 0
    case MAppend(lhs, rhs) => lhs + rhs
  }

  // F-Algebra for Ring
  sealed trait RingF[A]
  case object RZero extends RingF[Nothing]
  case object ROne extends RingF[Nothing]
  case class RNeg[A](a: A) extends RingF[A]
  case class RAdd[A](lhs: A, rhs: A) extends RingF[A]
  case class RMul[A](lhs: A, rhs: A) extends RingF[A]

  // recursion
  sealed trait Expr
  case object EZero extends Expr
  case object EOne extends Expr
  case class ENeg(a: Expr) extends Expr
  case class EAdd(lhs: Expr, rhs: Expr) extends Expr
  case class EMul(lhs: Expr, rhs: Expr) extends Expr

  // catamorphism

  // natural numbers + fibonacci as algebra
  sealed trait NatF[+A]
  case object NZero extends NatF[Nothing]
  case class NSucc[A](a: A) extends NatF[A]

  def fibAlgebra: NatF[(Int, Int)] => (Int, Int) = {
    case NZero => (1, 1)
    case NSucc((n,m)) => (n, n + m)
  }

  // TODO cata fib ?

  // List is initial algebra for following Functor on A, E is element type

  trait ListF[+E,+A]
  case object LNil extends ListF[Nothing, Nothing]
  case class LCons[E,A](e: E, a: A) extends ListF[E,A]

  def sumAlg: ListF[Int, Int] => Int = {
    case LNil => 0
    case LCons(e,a) => e + a
  }

  // cata sumAlg is a sum of list
  // sum = foldr (l e a -> e + a) 0
  // ss fold is a catamorphism for List
  // fold is a catamorphism for a fix point of a Functor

  case class StreamF[E,A](e: E, a: A) // but this is a trivial functor a product type
  // [2..]
  def era: StreamF[Int, List[Int]] => List[Int] = {
    case StreamF(e, a) => List(e) ++ a.filter(_ % e != 0)
  }

  // primes = ana era [2..]
}
