package educational

import scala.language.higherKinds

trait Divide[F[_]] extends Contravariant[F] {
  def divide[A,B,C](f: A => (B,C), fb: F[B], fc: F[C]): F[A]
}
