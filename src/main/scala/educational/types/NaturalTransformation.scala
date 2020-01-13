package educational.types

object NaturalTransformation {
  type ~>[F[_],G[_]] = F[_] => G[_]
}
