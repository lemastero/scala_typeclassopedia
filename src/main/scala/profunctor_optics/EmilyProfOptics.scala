package profunctor_optics

import educational.Applicative

import scala.language.higherKinds

// translation from @emilypi Haskell snippet
// https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb

// TODO https://github.com/mroman42/vitrea/blob/master/Vitrea.hs

// free tambara monad
// TODO specify monad instance
// TODO replace List => Traversable
trait Phi[P[_,_],A,B] {
  type U
  type V
  type C
  type D
  def _x1: A => (List[U], C)
  def _x2: P[U,V]
  def _x3: B => (List[V], D)
}

object Phi {
  def apply[A, B, CC, DD, UU, VV, P[_, _]](
    x1: A => (List[UU], CC),
    x2: P[UU,VV],
    x3: B => (List[VV], DD)): Phi[P, A, B] =
    new Phi[P, A, B] {
      type U = UU
      type V = VV
      type C = CC
      type D = DD
      def _x1: A => (List[UU], CC) = x1
      def _x2: P[UU,VV] = x2
      def _x3: B => (List[VV], DD) = x3
    }
}

trait Theta1[P,A,B]

object Theta1 {
  def apply[P,A,AA,B,BB, C](t1: (List[AA], C), t2: (List[BB], C)): Theta1[P,A,B] = ???
}

trait Theta[P[_,_],A,B]

object Theta {
  def apply[P[_,_],A,AA,B,BB, C](p: P[(List[AA], C), (List[BB], C)]): Theta[P,A,B] = ???
}

trait Profunctor[P[_,_]] {
  def dimap[S,T,A,B](sa: S => A, bt: B => T): P[A,B] => P[S,T]
}

trait Choice[P[_,_]] extends Profunctor[P] {
  def left[A,B,C]:  P[A,B] => P[Either[A, C], Either[B, C]]
  def right[A,B,C]: P[A,B] => P[Either[C, A], Either[C ,B]] =
    dimap[Either[C,A], Either[C ,B], Either[A, C], Either[B,C]](_.swap, _.swap) compose left[A,B,C]
}

trait Strong[P[_,_]] extends Profunctor[P] {
  def first[A,B,C]:  P[A,B] => P[(A, C), (B, C)]
  def second[A,B,C]: P[A,B] => P[(C, A), (C, B)] =
    dimap[(C,A), (C ,B), (A, C), (B,C)](_.swap, _.swap) compose first[A,B,C]
}

trait Closed[P[_,_]] extends Profunctor[P] {
  def closed[A,B,C]: P[A,B] => P[C => A,C => B]
}

trait Traversing[P[_,_]] extends Profunctor[P] {
  def strechL[A, B, C]: P[A, B] => P[(List[A], C), (List[B], C)]
  def strechR[A, B, C]: P[A, B] => P[(C, List[A]), (C, List[B])]
}

trait Polynodal[P[_,_]] extends Closed[P] with Traversing[P] {
  def griddedL[A,B,C,D]: P[A,B] => P[D => (List[A],C), D => (List[B],C)] =
    closed compose strechL
  def griddedR[A,B,C,D]: P[A,B] => P[D => (C,List[A]), D => (C,List[B])] =
    closed compose strechR
}

trait Glassed[P[_,_]] extends Strong[P] with Closed[P] {
  def glassedL[A,B,U,T]: P[A,B] => P[(T, U => A), (T, U => B)] =
    second compose closed
  def glassedR[A,B,U,T]: P[A,B] => P[(U => A,T), (U => B,T)] =
    first compose closed
}

trait Magnified[P[_,_]] extends Strong[P] with Choice[P] {
  def maginfyLS[A,B,C,D]: P[A,B] => P[(C, Either[A,D]),(C, Either[B,D])] =
    second compose left
  def maginfyRS[A,B,C,D]: P[A,B] => P[(C, Either[D,A]),(C, Either[D,B])] =
    second compose right
  def maginfyLF[A,B,C,D]: P[A,B] => P[(Either[A,D],C),(Either[B,D],C)] =
    first compose left
  def maginfyRF[A,B,C,D]: P[A,B] => P[(Either[D,A],C),(Either[D,B],C)] =
    first compose right
}

trait Telescoped[P[_,_]] extends Closed[P] with Choice[P] {
  def telescopeL[A,B,C,D]: P[A,B] => P[Either[C => A, D],Either[C => B, D]] =
    left compose closed
  def telescopeR[A,B,C,D]: P[A,B] => P[Either[D, C => A],Either[D, C => B]] =
    right compose closed
}

trait Windowed[P[_,_]] extends Choice[P] with Glassed[P] {
  def windowedRGL[A,B,U,V,T]: P[A,B] => P[Either[V, (T,U=>A)], Either[V, (T,U=>B)]] =
    right compose glassedL
  def windowedRGR[A,B,U,V,T]: P[A,B] => P[Either[V, (U=>A,T)], Either[V, (U=>B,T)]] =
    right compose glassedR
  def windowedLGL[A,B,U,V,T]: P[A,B] => P[Either[(U=>A,T), V], Either[(U=>B,T), V]] =
    left compose glassedR
  def windowedLGR[A,B,U,V,T]: P[A,B] => P[Either[(U=>A,T),V], Either[(U=>B,T),V]] =
    left compose glassedR
}

trait Polyp[P[_,_]] extends Profunctor[P] {
  def polyper[A,B,F[_]](implicit A: Applicative[F]): P[A,B] => P[F[A],F[B]]
}