package profunctor

import traverse.TraverseSimpleImpl.Traverse

import scala.language.higherKinds

object ProfunctorSimpleImpl {

  trait Profunctor[P[_, _]] {
    def dimap[A,B,C,D](ab: A => B, cd: C => D): P[B, C] => P[A, D]
  }

  trait Strong[P[_, _]] extends Profunctor[P] {
    def first[A,B,C](pab: P[A, B]): P[(A, C), (B, C)]

    def second[A,B,C](pab: P[A, B]): P[(C, A), (C, B)] = {
      val v1: P[(A, C), (B, C)] = first(pab)
      val v2: P[(A, C), (B, C)] => P[(C, A), (C, B)] = dimap(_.swap, _.swap)
      v2(v1)
    }
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
  }

  trait Closed[P[_,_]] extends Profunctor[P] {
    def closed[A,B,X](pab: P[A,B]): P[X=>A, X=>B]
  }
}
