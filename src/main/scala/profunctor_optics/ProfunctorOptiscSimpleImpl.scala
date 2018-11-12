package profunctor_optics

import scala.language.higherKinds

/*
Mainline Profunctor Heirarchy for Optics
https://r6research.livejournal.com/27476.html
*/
object ProfunctorOpticsSimpleImpl {

  trait Functor[F[_]] {
    def map[A, B](x: F[A])(f: A => B): F[B]
  }

  trait Apply[F[_]] extends Functor[F] {
    def apply[A, B](ff: F[A => B])(fa: F[A]): F[B]
  }

  trait Applicative[F[_]] extends Apply[F] {
    def pure[A](value: A): F[A]
    def liftA2[A, B, Z](abc: (A, B) => Z)(fa: F[A], fb: F[B]): F[Z] = apply(map(fa)(abc.curried))(fb)
  }

  trait Foldable[F[_]] {
    def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B
  }

  trait Traverse[F[_]] extends Functor[F] with Foldable[F] {
    def traverse[G[_] : Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
  }

  trait Profunctor[P[_, _]] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor.html
    def dimap[A,B,C,D](ab: A => B, cd: C => D): P[B, C] => P[A, D]
  }

  trait Strong[P[_, _]] extends Profunctor[P] { // http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Strong.html
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

  // Profunctor Optics

  trait Iso[S, T, A, B] {
    def run[P[_, _]](pab: P[A, B])(implicit P: Profunctor[P]): P[S, T]
  }

  trait Lens[S, T, A, B] {
    def run[P[_, _]](pab: P[A, B])(implicit S: Strong[P]): P[S, T]
  }

  trait Prism[S, T, A, B] {
    def run[P[_, _]](pab: P[A, B])(implicit C: Choice[P]): P[S, T]
  }

  trait AffineTraversal[S, T, A, B] {
    def run[P[_, _]](pab: P[A, B])(implicit S: Step[P]): P[S, T]
  }

  trait Traversal[S, T, A, B] {
    def run[P[_, _]](pab: P[A, B])(implicit W: Walk[P]): P[S, T]
  }

  trait SEC[S, T, A, B] {
    def run[P[_, _]](pab: P[A, B])(implicit S: Settable[P]): P[S, T]
  }

  trait Grates[S, T, A, B] { // https://r6research.livejournal.com/28050.html
    def run[P[_,_]](pab: P[A, B])(implicit C: Closed[P]): P[S, T]
  }
}
