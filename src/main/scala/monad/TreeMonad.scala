package monad

import cats.{Monad, StackSafeMonad}
import helper_implementations.{Branch, Leaf, Tree}

object TreeMonad {

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
