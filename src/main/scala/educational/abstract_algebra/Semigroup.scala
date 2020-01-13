package educational.abstract_algebra

trait Semigroup[M] {
  def combine(lhs: M, rhs: M): M
}
