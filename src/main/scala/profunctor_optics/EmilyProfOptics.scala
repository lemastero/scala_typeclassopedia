package profunctor_optics

import educational.category_theory.Applicative

// translation from @emilypi Haskell snippet
// https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb

// TODO https://github.com/mroman42/vitrea/blob/master/Vitrea.hs

trait FreeTambara[P[_,_],A,B] {
  type U
  type V
  type C
  type D
  def _x1: A => (List[U], C)
  def _x2: P[U,V]
  def _x3: B => (List[V], D)
}

object FreeTambara {
  def apply[A, B, CC, DD, UU, VV, P[_, _]](
    x1: A => (List[UU], CC),
    x2: P[UU,VV],
    x3: B => (List[VV], DD)): FreeTambara[P, A, B] =
    new FreeTambara[P, A, B] {
      type U = UU
      type V = VV
      type C = CC
      type D = DD
      def _x1: A => (List[UU], CC) = x1
      def _x2: P[UU,VV] = x2
      def _x3: B => (List[VV], DD) = x3
    }
}

trait CofreeTambara[P,A,B] {
  type C
  def x1: (List[A],C)
  def x2: (List[B],C)
}

object CofreeTambara {
  def apply[P,A,AA,B,BB, C](t1: (List[AA], C), t2: (List[BB], C)): CofreeTambara[P,A,B] = ???
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

  /*
  type Z = ()
  type S a = Either a Z
  type One = S Z
  type Two = S One
  type Three = S Two
  type Four = S Three
  */
  type Zero = Unit
  type Succ[A] = Either[A,Zero]
  type One = Succ[Zero]
  type Two = Succ[One]
  type Three = Succ[Two]
  type Four = Succ[Three]
}