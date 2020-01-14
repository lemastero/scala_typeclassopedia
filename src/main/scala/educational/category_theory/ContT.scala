package educational.category_theory

/** Continutation monad transformer */
trait ContT[R, M[_], A] {
  def runContT(amr: A => M[R]): M[R]
}
