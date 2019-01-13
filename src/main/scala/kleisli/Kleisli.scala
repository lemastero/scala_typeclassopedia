package kleisli

import scala.language.higherKinds

object KleisliSimpleImpls {
  case class Kleisli[F[_], A, B](run: A => F[B])
}
