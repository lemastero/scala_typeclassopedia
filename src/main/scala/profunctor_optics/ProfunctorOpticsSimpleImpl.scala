package profunctor_optics

import educational.category_theory.{Functor, Traverse}

/*
Mainline Profunctor Heirarchy for Optics
https://r6research.livejournal.com/27476.html
*/
object ProfunctorOpticsSimpleImpl {

  trait Strong[P[_, _]] extends Profunctor[P] {
    def first[A,B,C](pab: P[A, B]): P[(A, C), (B, C)]

    def second[A,B,C](pab: P[A, B]): P[(C, A), (C, B)] = {
      val v1: P[(A, C), (B, C)] = first(pab)
      val v2: P[(A, C), (B, C)] => P[(C, A), (C, B)] = dimap(_.swap, _.swap)
      v2(v1)
    }
  }

  trait Choice[P[_, _]] extends Profunctor[P] {
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

  trait Walk[P[_,_]] extends Step[P] {
    def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
  }

  trait Settable[P[_,_]] extends Walk[P] {
    def mapping[A,B,F[_]](pab: P[A,B])(implicit FT: Functor[F]): P[F[A], F[B]]
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
