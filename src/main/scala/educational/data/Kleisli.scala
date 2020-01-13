package educational.data

case class Kleisli[F[_], A, B](run: A => F[B])
