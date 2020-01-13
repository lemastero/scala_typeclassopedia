package educational.collections

import cats.{Functor, Monad, StackSafeMonad}

/** data Tree a = Leaf a | Branch (Tree a) (Tree a) deriving (Eq, Show) */
sealed trait Tree[+T]
final case class Leaf[T](value: T) extends Tree[T]
final case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]

object TreeInstances {
  /**
  Haskell:

  instance Functor Tree where
    fmap :: (a -> b) -> f a -> f b
    fmap f (Leaf a) = Leaf (f a)
    fmap f (Branch l r) = Branch (fmap f l) (fmap f r)
  */
  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =  fa match {
      case Leaf(v) => Leaf(f(v))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    }
  }

  implicit val instance: Monad[Tree] = new StackSafeMonad[Tree] {
    def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
      case Leaf(v) => f(v)
      case Branch(l: Tree[A], r: Tree[B])=>
        val l2: Tree[B] = flatMap(l)(f)
        val r2: Tree[B] = flatMap(r)(f)
        Branch( l2, r2 )
    }

    def pure[A](x: A): Tree[A] = Leaf(x)
  }
}
