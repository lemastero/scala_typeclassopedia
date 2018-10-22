package natural_transformation

import scala.language.higherKinds

object NaturalTransf {

  trait NaturalTransf[F[_], G[_]] {
    def apply[A](fa: F[A]): G[A]
  }
}
