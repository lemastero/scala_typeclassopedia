package educational.category_theory.two.profunctor.choice

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.ProfunctorInstance.Function1Profunctor
import educational.category_theory.two.profunctor.ProfunctorLaws

// class Profunctor p => Choice p where
//    left'  :: p a b -> p (Either a c) (Either b c)
//    right' :: p a b -> p (Either c a) (Either c b)
trait Choice[=:>[_,_]] extends Profunctor[=:>] {
  def left[A,B,C](pab: A =:> B):  Either[A,C] =:> Either[B,C]

  def right[A,B,C](pab: A =:> B): Either[C,A] =:> Either[C,B] = {
    val v1: Either[A,C] =:> Either[B,C] = left(pab)
    dimap(v1)(_.swap, _.swap)
  }
}

object ChoiceInstances {
  val Function1Chocice: Choice[Function1] = new Choice[Function1] with Function1Profunctor {
    def left[A, B, C](f: A => B): Either[A, C] => Either[B, C] =
      a => a.swap.map(f).swap // TODO reuse Bifunctor on Either
  }
}

trait ChoiceLaws[=:>[_,_]] extends Choice[=:>] with ProfunctorLaws[=:>] {
  // left' ≡ dimap swapE swapE . right' where
  //   swapE :: Either a b -> Either b a
  //   swapE = either Right Left
  def leftIsDimapWithRight[A,B,C](p: A =:> B): Boolean = {
    val l: Either[A,C] =:> Either[B,C] = left(p)
    val r1: Either[C,A] =:> Either[C,B] = right(p)
    val r2: Either[A,C] =:> Either[B,C] = dimap(r1)(_.swap,_.swap)
    l == r2
  }

  // rmap Left ≡ lmap Left . left'
  def rmapLeftIsLmapLeftLeft[A,B,C](p: A =:> B): Boolean = {
    val l1: A =:> Either[B,C] = rmap(p)(Left.apply)
    val r1: Either[A,C] =:> Either[B,C] = left(p)
    val r2: A =:> Either[B,C] = lmap(r1)(Left.apply)
    r2 == l1
  }

  // lmap (right f) . left' ≡ rmap (right f) . left'
  def lmapRightLeftEqRmapRightleft[A,B,C,D,E](
      p: A =:> B,
      f: Either[D,Either[A,C]] => Either[D,Either[A,C]],
      g: Either[D,Either[B,C]] => Either[D,Either[B,C]]): Boolean = {
    val lep: Either[A,C] =:> Either[B,C] = left(p)
    val rlp: Either[D,Either[A,C]] =:> Either[D,Either[B,C]] = right(lep)
    val lhs: Either[D,Either[A,C]] =:> Either[D,Either[B,C]] = lmap(rlp)(f)
    val rhs: Either[D,Either[A,C]] =:> Either[D,Either[B,C]] = rmap(rlp)(g)
    lhs == rhs // TODO why there is only f in Haskell laws?
  }

  //  left' . left' ≡ dimap assocE unassocE . left' where
  //      assocE :: Either (Either a b) c -> Either a (Either b c)
  //      assocE (Left (Left a)) = Left a
  //      assocE (Left (Right b)) = Right (Left b)
  //      assocE (Right c) = Right (Right c)
  //      unassocE :: Either a (Either b c) -> Either (Either a b) c
  //      unassocE (Left a) = Left (Left a)
  //      unassocE (Right (Left b)) = Left (Right b)
  //      unassocE (Right (Right c)) = Right c
  def leftLeftEqDimapAssocLeft[A,B,C,D](p: A =:> B): Boolean = {
    def assocE[X,Y,Z]: Either[Either[X,Y],Z] => Either[X,Either[Y,Z]] = {
      case Left(Left(a))  => Left(a)
      case Left(Right(b)) => Right(Left(b))
      case Right(c)       => Right(Right(c))
    }
    def unassocE[X,Y,Z]: Either[X,Either[Y,Z]] => Either[Either[X,Y],Z] = {
      case Left(a)         => Left(Left(a))
      case Right(Left(b))  => Left(Right(b))
      case Right(Right(c)) => Right(c)
    }
    val lp2: Either[A,C] =:> Either[B,C] = left(p)
    val l1: Either[Either[A,C],D] =:> Either[Either[B,C],D] = left(lp2)

    val lp: Either[A,Either[C,D]] =:> Either[B,Either[C,D]] = left(p)
    val r:  Either[Either[A,C],D] =:> Either[Either[B,C],D] = dimap(lp)(assocE,unassocE)
    l1 == r
  }

  // TODO laws for the right
}
