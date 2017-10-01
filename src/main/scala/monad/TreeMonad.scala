package monad

import cats.Monad
import helper_implementations.{Branch, Leaf, Tree}

object TreeMonad {

  implicit val instance: Monad[Tree] = new Monad[Tree] {
    override def flatMap[A, B](fa: Tree[A])(f: A => Tree[B]): Tree[B] = fa match {
      case Leaf(v) => f(v)
      case Branch(l: Tree[A], r: Tree[B])=>
        val l2: Tree[B] = flatMap(l)(f)
        val r2: Tree[B] = flatMap(r)(f)
        Branch( l2, r2 )
    }

    override def tailRecM[A, B](a: A)(f: A => Tree[Either[A, B]]): Tree[B] = ???

    override def pure[A](x: A): Tree[A] = Leaf(x)
  }
}
