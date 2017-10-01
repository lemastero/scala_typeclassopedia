package functor

import cats.Functor
import helper_implementations.{Branch, Leaf, Tree}

object TreeFunctor {

  /** Haskell - instance for Functor type class

  instance Functor Tree where
    -- fmap :: (a -> b) -> f a -> f b
    fmap f (Leaf a) = Leaf (f a)
    fmap f (Branch l r) = Branch (fmap f l) (fmap f r)

   Haskell typeclass for Functor is in [[InstancesForBuiltInTypes]]
   */
  implicit val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =  fa match {
      case Leaf(v) => Leaf(f(v))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    }
  }
}
