package educational.data

/**
 * Non exclusive two values
 *
  * (A + B + AB)
*/

sealed trait These[A,B]
case class This[A,B](a: A) extends These[A,B]
case class That[A,B](b: B) extends These[A,B]
case class Those[A,B](a: A, b: B) extends These[A,B]

object These {
  // Bitraverse
  // Bifoldable
  // Bifunctor
  // Bicrosswalk
  // Functor
  // Applicative (with Semigroup)
  // Foldable
  // Traversable
  // Apply (with Semigroup)
  // Crosswalk
  // Bind (with Semigroup)
  // Semigroup (with Semigroup a, Semigroup b) ?
  // Rep

  // TODO helpers: https://hackage.haskell.org/package/these/docs/Data-These.html
}