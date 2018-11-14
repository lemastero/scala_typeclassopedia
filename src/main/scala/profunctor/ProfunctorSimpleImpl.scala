package profunctor

import functor.FunctorSimpleImpl.Functor
import traverse.TraverseSimpleImpl.Traverse

import scala.language.higherKinds

object ProfunctorSimpleImpl {

  trait Profunctor[P[_, _]] {
    def dimap[X,Y,Z,W](ab: X => Y, cd: Z => W): P[Y, Z] => P[X, W]

    // derived methods
    def lmap[A,B,C](f: A => B): P[B,C] => P[A,C] = dimap[A,B,C,C](f,identity[C])
    def rmap[A,B,C](f: B => C): P[A,B] => P[A,C] = dimap[A,A,B,C](identity[A], f)
  }

  trait ProfunctorYoneda[P[_,_],A,B] {
    def runYoneda[X,Y](f: X => A, g: B => Y): P[X,Y]

    def extractYoneda: P[A,B] = runYoneda(identity[A], identity[B])
  }

  trait Strong[P[_, _]] extends Profunctor[P] {
    def first[X,Y,Z](pab: P[X, Y]): P[(X, Z), (Y, Z)]

    def second[X,Y,Z](pab: P[X, Y]): P[(Z, X), (Z, Y)] = {
      val ds: P[(X, Z), (Y, Z)] => P[(Z, X), (Z, Y)] = dimap(_.swap, _.swap)
      ds(first(pab): P[(X, Z), (Y, Z)])
    }
  }

  object Strong {
    def uncurryStrong[P[_,_],A,B,C](pa: P[A, B => C])(S: Strong[P]): P[(A,B),C] = {
      S.rmap{bc:(B => C, B) => bc._1(bc._2)}(S.first(pa))
    }
  }

  trait StrongLaws[P[_,_]] extends Strong[P] {
    def firstIsSwappedSecond[X, Y, Z](a: P[X, Y]): Boolean = {
      first(a) == dimap((xz: (X, Z)) => xz.swap, (zy: (Z, Y)) => zy.swap)(second(a))
    }

    // lmap fst ≡ rmap fst . first'
    // lmap (second f) . first' ≡ rmap (second f) . first
    // first' . first' ≡ dimap assoc unassoc . first' where
    //   assoc ((a,b),c) = (a,(b,c))
    //   unassoc (a,(b,c)) = ((a,b),c)

    def rightAlignedWithDimapFirst[X, Y, Z](a: P[X, Y]): Boolean = {
      second(a) == dimap((xz: (Z, X)) => xz.swap, (zy: (Y, Z)) => zy.swap)(first(a))
    }

    // lmap snd ≡ rmap snd . second'
    // lmap (first f) . second' ≡ rmap (first f) . second'
    // second' . second' ≡ dimap unassoc assoc . second' where
    //    assoc ((a,b),c) = (a,(b,c))
    //    unassoc (a,(b,c)) = ((a,b),c)
  }

  trait Tambara[P[_,_],A,B]{
    def runTambara[C]: P[(A,C),(B,C)]
  }

  trait Choice[P[_, _]] extends Profunctor[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Choice.html
    def left[A,B,C](pab: P[A, B]):  P[Either[A, C], Either[B, C]]
    def right[A,B,C](pab: P[A, B]): P[Either[C, A], Either[C, B]] = {
      val v1: P[Either[A, C], Either[B, C]] = left(pab)
      val v2: P[Either[A, C], Either[B, C]] => P[Either[C, A], Either[C, B]]  = dimap(_.swap, _.swap)
      v2(v1)
    }
  }

  trait Step[P[_,_]] extends Choice[P] with Strong[P] {
    def step[A,B,C,D](pab: P[A,B]): P[Either[D, (A,C)], Either[D, (B,C)]] = right(first(pab))
  }

  trait Walk[P[_,_]] extends Step[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Traversing.html
    def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
  }

  trait Settable[P[_,_]] extends Walk[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Mapping.html
    def mapping[A,B,F[_]](pab: P[A,B])(implicit FT: Functor[F]): P[F[A], F[B]]
  }

  trait Closed[P[_,_]] extends Profunctor[P] {
    def closed[A,B,X](pab: P[A,B]): P[X=>A, X=>B]
  }
}
