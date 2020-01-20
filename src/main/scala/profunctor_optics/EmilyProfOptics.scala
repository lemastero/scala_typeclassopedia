package profunctor_optics

import educational.category_theory.Applicative

// translation from @emilypi Haskell snippet
// https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb

// TODO https://github.com/mroman42/vitrea/blob/master/Vitrea.hs

trait Profunctor[=>:[_,_]] {
  def dimap[S,T,A,B](sa: S => A, bt: B => T): A=>:B => S=>:T
}

trait Choice[=>:[_,_]] extends Profunctor[=>:] {
  def left[A,B,C]: A =>: B => (Either[A,C] =>: Either[B,C])
  def right[A,B,C]: A =>: B => (Either[C,A] =>: Either[C,B]) =
    dimap[Either[C,A], Either[C,B], Either[A,C], Either[B,C]](_.swap, _.swap) compose left[A,B,C]
}

trait Strong[=>:[_,_]] extends Profunctor[=>:] {
  def first[A,B,C]:  (A =>: B) => ((A,C) =>: (B,C))
  def second[A,B,C]: (A =>: B) => ((C,A) =>: (C,B)) =
    dimap[(C,A), (C ,B), (A, C), (B,C)](_.swap, _.swap) compose first[A,B,C]
}

trait Closed[=>:[_,_]] extends Profunctor[=>:] {
  def closed[A,B,C]: A=>:B => (C=>A) =>: (C => B)
}

trait Traversing[=>:[_,_]] extends Profunctor[=>:] {
  def strechL[A,B,C]: (A =>: B) => (List[A],C) =>: (List[B],C) // TODO replace List by Traverse
  def strechR[A,B,C]: (A =>: B) => (C, List[A]) =>: (C,List[B])
}

trait Polynodal[=>:[_,_]] extends Closed[=>:] with Traversing[=>:] {
  def griddedL[A,B,C,D]: A=>:B => (D => (List[A],C)) =>: (D => (List[B],C)) =
    closed compose strechL
  def griddedR[A,B,C,D]: A=>:B => (D => (C,List[A])) =>: (D => (C,List[B])) =
    closed compose strechR
}

trait Glassed[=>:[_,_]] extends Strong[=>:] with Closed[=>:] {
  def glassedL[A,B,U,T]: (A =>: B) => ((T, U => A) =>: (T, U => B)) =
    second compose closed
  def glassedR[A,B,U,T]: (A =>: B) => ((U => A,T) =>: (U => B,T)) =
    first compose closed
}

trait Magnified[=>:[_,_]] extends Strong[=>:] with Choice[=>:] {
  def maginfyLS[A,B,C,D]: (A =>: B) => ((C, Either[A,D]) =>: (C, Either[B,D])) =
    second compose left
  def maginfyRS[A,B,C,D]: (A =>: B) => ((C, Either[D,A]) =>: (C, Either[D,B])) =
    second compose right
  def maginfyLF[A,B,C,D]: (A =>: B) => ((Either[A,D],C) =>: (Either[B,D],C)) =
    first compose left
  def maginfyRF[A,B,C,D]: (A =>: B) => ((Either[D,A],C) =>: (Either[D,B],C)) =
    first compose right
}

trait Telescoped[=>:[_,_]] extends Closed[=>:] with Choice[=>:] {
  def telescopeL[A,B,C,D]: (A =>: B) => (Either[C => A, D] =>: Either[C => B, D]) =
    left compose closed
  def telescopeR[A,B,C,D]: (A =>: B) => (Either[D, C => A] =>: Either[D, C => B]) =
    right compose closed
}

trait Windowed[=>:[_,_]] extends Choice[=>:] with Glassed[=>:] {
  def windowedRGL[A,B,U,V,T]: (A =>: B) => (Either[V, (T,U=>A)] =>: Either[V, (T,U=>B)]) =
    right compose glassedL
  def windowedRGR[A,B,U,V,T]: (A =>: B) => (Either[V, (U=>A,T)] =>: Either[V, (U=>B,T)]) =
    right compose glassedR
  def windowedLGL[A,B,U,V,T]: (A =>: B) => (Either[(U=>A,T), V] =>: Either[(U=>B,T), V]) =
    left compose glassedR
  def windowedLGR[A,B,U,V,T]: (A =>: B) => (Either[(U=>A,T),V] =>: Either[(U=>B,T),V]) =
    left compose glassedR
}

trait Polyp[=>:[_,_]] extends Profunctor[=>:] {
  def polyper[A,B,F[_]](implicit A: Applicative[F]): A =>: B => F[A] =>: F[B]
}

object Optics {
  /*
  type Optic p s t a b = p a b -> p s t

  type Iso             s t a b = forall p.   Profunctor p => Optic p s t a b
  type Lens            s t a b = forall p.       Strong p => Optic p s t a b
  type Prism           s t a b = forall p.       Choice p => Optic p s t a b
  type Grate           s t a b = forall p.       Closed p => Optic p s t a b
  type Glass           s t a b = forall p.      Glassed p => Optic p s t a b
  type AffineTraversal s t a b = forall p.    Magnified p => Optic p s t a b
  type AffineWindow    s t a b = forall p.   Telescoped p => Optic p s t a b
  type Window          s t a b = forall p.     Windowed p => Optic p s t a b
  type Traversal       s t a b = forall p.   Traversing p => Optic p s t a b
  type Grid            s t a b = forall p.    Polynodal p => Optic p s t a b -- closed traversal
  type Kaleidescope    s t a b = forall p.        Polyp p => Optic p s t a b
   */

  type Optics[P[_,_],S,T,A,B] = P[A,B] => P[S,T]

  trait Iso[S,T,A,B] {
    def apply[P[_,_]]: Profunctor[P] => Optics[P,S,T,A,B]
  }

  trait Lens[S,T,A,B] {
    def apply[P[_,_]]: Strong[P] => Optics[P,S,T,A,B]
  }

  trait Prism[S,T,A,B] {
    def apply[P[_,_]]: Choice[P] => Optics[P,S,T,A,B]
  }

  trait Grate[S,T,A,B] {
    def apply[P[_,_]]: Closed[P] => Optics[P,S,T,A,B]
  }

  trait Glass[S,T,A,B] {
    def apply[P[_,_]]: Glassed[P] => Optics[P,S,T,A,B]
  }

  trait AffineTraversal[S,T,A,B] {
    def apply[P[_,_]]: Magnified[P] => Optics[P,S,T,A,B]
  }

  trait AffineWindow[S,T,A,B] {
    def apply[P[_,_]]: Telescoped[P] => Optics[P,S,T,A,B]
  }

  trait Window[S,T,A,B] {
    def apply[P[_,_]]: Windowed[P] => Optics[P,S,T,A,B]
  }

  trait Traversal[S,T,A,B] {
    def apply[P[_,_]]: Traversing[P] => Optics[P,S,T,A,B]
  }

  trait Grid[S,T,A,B] {
    def apply[P[_,_]]: Polynodal[P] => Optics[P,S,T,A,B]
  }

  trait Kaleidescope[S,T,A,B] {
    def apply[P[_,_]]: Polyp[P] => Optics[P,S,T,A,B]
  }

  /*
  type Iso' s a = Iso s s a a
  type Prism' s a = Prism s s a a
  type Lens' s a = Lens s s a a
  type Traversal' s a = Traversal s s a a
  */
  type Iso1[S,A]       = Iso[S,S,A,A]
  type Prism1[S,A]     = Prism[S,S,A,A]
  type Lens1[S,A]      = Lens[S,S,A,A]
  type Traversal1[S,A] = Traversal[S,S,A,A]

  /*
  type a ~= b = Iso b a b a
  type a <= b = Prism' b a
  type a .| b = Lens' b a
  type a .& b = Traversal' b a
  type Log a b = Grate b a b a
  type Logg a b = Glass b a b a
  type AutExp b a = Window b a b a
   */
  type ~=[A,B] = Iso[B,A,B,A]
  type <=[A,B] = Prism1[B,A]
  type *|[A,B] = Lens1[B,A]
  type *&[A,B] = Traversal1[B,A]
  type Log[A,B] = Grate[B,A,B,A]
  type Logg[A,B] = Glass[B,A,B,A]
  type AutExp[B,A] = Window[B,A,B,A]
}