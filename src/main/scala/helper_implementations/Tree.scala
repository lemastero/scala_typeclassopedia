package helper_implementations

import cats.Functor

sealed trait Tree[T]
final case class Leaf[T](value: T) extends Tree[T]
final case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]

object Tree {
  val treeFunctor: Functor[Tree] = new Functor[Tree] {
    override def map[A, B](fa: Tree[A])(f: A => B): Tree[B] =  fa match {
      case Leaf(v) => Leaf(f(v))
      case Branch(l, r) => Branch(map(l)(f), map(r)(f))
    }
  }
}

object InstanceForCustomTree extends App {
  val tree = Branch(Leaf("a"), Branch(Leaf("b"), Leaf("c")))
  println(tree)

  import Tree.treeFunctor.map

  val treeUpper = map(tree)(_.toUpperCase())
  println(treeUpper)
}
