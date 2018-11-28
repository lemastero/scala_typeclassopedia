package natural_transformation

import scala.language.higherKinds

object NaturalTransf {

  type ~>[F[_],G[_]] = F[_] => G[_]

  trait NaturalTransf[F[_], G[_]] {
    def apply[A](fa: F[A]): G[A]
  }
}
