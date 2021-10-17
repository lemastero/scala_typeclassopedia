package educational.category_theory

trait Invariant[F[_]] {
  def imap[A, B](fa: F[A])(f: A => B, g: B => A): F[B]
}
