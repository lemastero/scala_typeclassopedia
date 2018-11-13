package semigroup

object MonoidSimpleImpl {

  trait Semigroup[M] {
    def append(lhs: M, rhs: M): M
  }

  trait Monoid[M] extends Semigroup[M] {
    def empty: M
    def append(lhs: M, rhs: M): M
  }
}
