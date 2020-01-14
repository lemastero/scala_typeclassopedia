package educational.category_theory.contra

import educational.types.Void

/*
Decidable is contravariant for Alternative:
 https://hackage.haskell.org/package/contravariant-1.4/docs/Data-Functor-Contravariant-Divisible.html#t:Decidable
 https://www.reddit.com/r/haskell/comments/38o0f7/a_mixture_of_applicative_and_divisible/
 */
trait Decidable[F[_]] extends Divisible[F] with Decide[F] {
  def loose[A](f: A => Void): F[A]
}
