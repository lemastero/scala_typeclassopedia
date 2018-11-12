package traverse

import applicative.ApplicativeSimpleImpl.Applicative
import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

object TraverseSimpleImpl {

  trait Foldable[F[_]] {
    def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B
  }

  trait Traverse[F[_]] extends Functor[F] with Foldable[F] {
    def traverse[G[_] : Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
  }
}
