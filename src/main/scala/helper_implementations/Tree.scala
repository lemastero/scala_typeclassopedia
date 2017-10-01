package helper_implementations

/** data Tree a = Leaf a | Branch (Tree a) (Tree a) deriving (Eq, Show) */
sealed trait Tree[+T]
final case class Leaf[T](value: T) extends Tree[T]
final case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]

object Tree {

  /** Number each node (replace current value)

  number :: Num n => Tree t -> n -> (Tree n, n)
  number (Leaf a) s = (Leaf s, s+1)
  number (Branch l r) s =
    let  (l1, s1) = number l s
         (r2, s2) = number r s1
    in (Branch l1 r2, s2)
   */
  def number[A](tree: Tree[A], n: Int): (Tree[Int], Int) = {
    tree match {
      case Leaf(_) => (Leaf(n), n+1)
      case Branch(left, right) =>
        val (numberedLeft,  newN)   = number(left, n)
        val (numberedRight, newerN) = number(right, newN)
        ( Branch(numberedLeft, numberedRight), newerN )
    }
  }

  /** Number each node (replace current value) - using monads

  number :: Num n => Tree t -> n -> (Tree n, n)
  number (Leaf a) s = (Leaf s, s+1)
  number (Branch l r) s =
    let  (l1, s1) = number l s
         (r2, s2) = number r s1
    in (Branch l1 r2, s2)
    */
  def number2[A](tree: Tree[A])(n: Int): (Tree[Int], Int) = {
    tree match {
      case Leaf(_) => (Leaf(n), n+1)
      case Branch(left, right) =>
        val (numberedLeft,  newN)   = number2(left)(n)
        val (numberedRight, newerN) = number2(right)(newN)
        ( Branch(numberedLeft, numberedRight), newerN )
    }
  }

  /** Merge two Tree's if they have the same structure

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
   */
  def zip[A, B](lhs: Tree[A], rhs: Tree[B]): Option[Tree[(A, B)]] = {
    (lhs, rhs) match {
      case (Leaf(a), Leaf(b)) => Some( Leaf( (a, b) ) )
      case (Branch(fstLeft, fstRight), Branch(sndLeft, sndRight)) =>  zip(fstLeft, sndLeft) match {
          case None => None
          case Some( leftResult ) => zip(fstRight, sndRight) match {
            case None => None
            case Some(rightResult) => Some( Branch(leftResult, rightResult) )
          }
        }
      case (_, _) => None
    }
  }

  /** Merge two Tree's if they have the same structure - using monads

    zipTree :: Tree a -> Tree b -> Maybe (Tree (a, b))
    zipTree (Leaf a) (Leaf b) =
      return (Leaf (a,b))
    zipTree (Branch fstLeft fstRight) (Branch sndLeft sndRight) =
      zipTree fstLeft sndLeft   >>= \leftJoined  ->
      zipTree fstRight sndRight >>= \rightJoined ->
      return (Branch leftJoined rightJoined)
    zipTree _ _ = Nothing
    */
  def zip2[A, B](lhs: Tree[A], rhs: Tree[B]): Option[Tree[(A, B)]] = {
    (lhs, rhs) match {
      case (Leaf(a), Leaf(b)) => Some( Leaf( (a, b) ) )
      case (Branch(fstLeft, fstRight), Branch(sndLeft, sndRight)) =>
        zip(fstLeft, sndLeft).flatMap { leftJoined =>
        zip(fstRight, sndRight).flatMap { rightJoined =>
          Some( Branch(leftJoined, rightJoined) )
        }
      }
      case (_, _) => None
    }
  }
}