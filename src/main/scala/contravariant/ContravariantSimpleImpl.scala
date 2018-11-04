package contravariant

import scala.language.higherKinds

object ContravariantSimpleImpl {

  /*
  class Contravariant f where
    contramap :: (b -> a) -> f a -> f b
 */
  trait Contravariant[F[_]] {
    def contramap[A, B](fa: F[A])(f: B => A): F[B]
  }
}
