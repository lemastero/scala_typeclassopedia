package educational.category_theory.two.bifunctors

trait Biapply[F[A, B]] {
  def biApply[A, B, AA, BB](fa: F[A, B])(ff: F[A => AA, B => BB]): F[AA, BB]
}

trait Biapplicative[F[A, B]] {
  def bipure[A,B](a: A, b: B): F[A,B]
}