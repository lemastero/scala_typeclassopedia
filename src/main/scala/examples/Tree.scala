package examples

import cats.Monad
import cats.instances.option.catsStdInstancesForOption
import cats.syntax.option._
import educational.collections.{Branch, Leaf, Tree}
import IntState.instance._

object Tree {

  /** Number each node (replace current value)

  Haskell version:

  number :: Num n => Tree t -> n -> (Tree n, n)
  number (Leaf a) s = (Leaf s, s+1)
  number (Branch l r) s =
    let  (l1, s1) = number l s
         (r2, s2) = number r s1
    in (Branch l1 r2, s2)

  Haskell version using monad:

  tick s = (s, s+1)

  replaceByIndex2 (Leaf a) = tick >>>= \s -> return2 (Leaf s)
  replaceByIndex2 (Branch l r) =
    replaceByIndex2 l >>>= \l1 ->
    replaceByIndex2 r >>>= \r1 ->
    return2 (Branch l1 r1)
    */
  def number1[A](tree: Tree[A], n: Int): (Tree[Int], Int) = {
    tree match {
      case Leaf(_) => (Leaf(n), n+1)
      case Branch(left, right) =>
        val (numberedLeft,  newN)   = number1(left, n)
        val (numberedRight, newerN) = number1(right, newN)
        ( Branch(numberedLeft, numberedRight), newerN )
    }
  }

  def number[A]: Tree[A] => Int => (Tree[Int], Int) = {
    case Branch(l, r) => map2(number(l), number(r))(Branch.apply)
    case Leaf(_)      => map(tick)(Leaf.apply)
  }

  val tick: Int => (Int, Int) = s => (s, s+1)

  /** Merge two Tree's if they have the same structure

    Haskell version:

    zipTree :: Tree a -> Tree b -> Maybe (Tree (a, b))
    zipTree (Leaf a) (Leaf b) =
      Just (Leaf (a,b))
    zipTree (Branch fstLeft fstRight) (Branch sndLeft sndRight) =
      case zipTree fstLeft sndLeft of
        Nothing -> Nothing
        Just leftJoined ->
          case zipTree fstRight sndRight of
            Nothing -> Nothing
            Just rightJoined -> Just (Branch leftJoined rightJoined)
    zipTree _ _ = Nothing

    Haskell version using monads:

    zipTree :: Tree a -> Tree b -> Maybe (Tree (a, b))
    zipTree (Leaf a) (Leaf b) =
      return (Leaf (a,b))
    zipTree (Branch fstLeft fstRight) (Branch sndLeft sndRight) =
      zipTree fstLeft sndLeft   >>= \leftJoined  ->
      zipTree fstRight sndRight >>= \rightJoined ->
      return (Branch leftJoined rightJoined)
    zipTree _ _ = Nothing
    */
  def zip1[A, B]: (Tree[A], Tree[B]) => Option[Tree[(A, B)]] = {
    case (Leaf(a), Leaf(b)) => Leaf((a, b)).some
    case (Branch(fstLeft, fstRight), Branch(sndLeft, sndRight)) =>
      zip1(fstLeft, sndLeft) match {
        case None => None
        case Some( leftResult ) => zip1(fstRight, sndRight) match {
          case None => None
          case Some(rightResult) => Branch(leftResult, rightResult).some
        }
      }
    case (_, _) => None
  }

  def zip[A, B]: (Tree[A], Tree[B]) => Option[Tree[(A, B)]] = {
    case (Leaf(a), Leaf(b))               => Leaf((a, b)).some
    case (Branch(l1, r1), Branch(l2, r2)) => Monad[Option].map2(zip(l1,l2), zip(r1,r2))(Branch.apply) // is it lazy?
    case (_, _)                           => None
  }
}
