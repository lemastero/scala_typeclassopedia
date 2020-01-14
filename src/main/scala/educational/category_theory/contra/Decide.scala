package educational.category_theory.contra

trait Decide[F[_]] extends Divisible[F] { // or Divisible
  def choose[A,B,C](f: A => Either[B,C], fb: F[B], fc: F[C]): F[A]
}
