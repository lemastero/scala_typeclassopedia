package educational.category_theory.higher.monoidal

trait MonoidObject[F[_,_], M, I] {
  def eta: I => M
  def mu: F[M,M] => M

  // TODO laws
}
