package ct_other_impls

object DeclarativeVarianceFun {
  case class Reader[-A, +B](run: A => B) // Profunctor-ish?
  case class Op[+A, -B](run: B => A) // Profunctor-ish?
  case class Ap[+F[+_], -A, +B](run: F[A => B]) // Profunctor-ish?
  case class ContraAp[+F[-_], +A, -B](run: F[A => B]) // Profunctor-ish?
  case class ApOp[+F[+_], +A, -B](run: F[B => A]) // Profunctor-ish?
  case class Kleisli[+F[+_], -A, +B](run: A => F[B]) // Profunctor-ish?
  case class ContraKleisli[-F[+_], -A, +B](run: F[A] => B) // Profunctor-ish?

  case class Kleisli2[+F[-_], -A, -B](run: A => F[B]) // Nifunctor-ish?

  case class CoKleisli[-F[-_], +A, +B](run: F[A] => B) // Bifunctor-ish?
  case class Zip[+F[+_], +A, +B](run: F[(A, B)]) // Bifunctor-ish?
  case class Alt[+F[+_], +A, +B](run: F[Either[A, B]]) // Bifunctor-ish?
}
