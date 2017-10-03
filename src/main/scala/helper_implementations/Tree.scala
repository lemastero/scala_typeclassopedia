package helper_implementations

/** data Tree a = Leaf a | Branch (Tree a) (Tree a) deriving (Eq, Show) */
sealed trait Tree[+T]
final case class Leaf[T](value: T) extends Tree[T]
final case class Branch[T](left: Tree[T], right: Tree[T]) extends Tree[T]

object Tree {
  import monad.IntState.instance._

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
  def number[A](tree: Tree[A], n: Int): (Tree[Int], Int) = {
    tree match {
      case Leaf(_) => (Leaf(n), n+1)
      case Branch(left, right) =>
        val (numberedLeft,  newN)   = number(left, n)
        val (numberedRight, newerN) = number(right, newN)
        ( Branch(numberedLeft, numberedRight), newerN )
    }
  }

  def numberUsingMonads[A]: Tree[A] => Int => (Tree[Int], Int) = {
    case Branch(left, right) =>
      flatMap(numberUsingMonads(left)) { numberedLeft =>
        flatMap(numberUsingMonads(right)) { numberedRight =>
          pure(Branch(numberedLeft, numberedRight))
        }
      }
    case Leaf(_) => flatMap(tick) { i => pure(Leaf(i)) }
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
  def zip[A, B]: (Tree[A], Tree[B]) => Option[Tree[(A, B)]] = {
    case (Leaf(a), Leaf(b)) => Some( Leaf( (a, b) ) )
    case (Branch(fstLeft, fstRight), Branch(sndLeft, sndRight)) =>
      zip(fstLeft, sndLeft) match {
        case None => None
        case Some( leftResult ) => zip(fstRight, sndRight) match {
          case None => None
          case Some(rightResult) => Some( Branch(leftResult, rightResult) )
        }
      }
    case (_, _) => None
  }

  def zipUsingMonads[A, B]: (Tree[A], Tree[B]) => Option[Tree[(A, B)]] = {
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
