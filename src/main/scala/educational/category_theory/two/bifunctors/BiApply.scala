package educational.category_theory.two.bifunctors

trait BiApply[F[A,B]] {
  def biApply[A,B,AA,BB](fa: F[A,B])(ff: F[A => AA,B => BB]): F[AA,BB]
}
