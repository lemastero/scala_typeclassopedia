package profunctor_optics

import educational.category_theory.Applicative

// translation from @emilypi Haskell snippet
// https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb

// TODO Glassery http://oleg.fi/gists/posts/2017-04-18-glassery.html
// TODO https://github.com/mroman42/vitrea/blob/master/Vitrea.hs

trait Profunctor[=>:[_,_]] {
  def dimap[S,T,A,B](sa: S => A, bt: B => T): A=>:B => S=>:T

  def iso[A,B]: (A =>: B) => (A =>: B) = identity[A =>: B]
}

trait TambaraModule[=>:[_,_], ⊗[_,_]] {
  def runTambara[A,B,C]: A=>:B => (A⊗C) =>: (B⊗C)
}

trait Choice[=>:[_,_]] extends Profunctor[=>:] { // TambaraModule Either(_,C)
  def left[A,B,C]: (A =>: B) => (Either[A,C] =>: Either[B,C])

  def right[A,B,C]: A =>: B => (Either[C,A] =>: Either[C,B]) =
    dimap[Either[C,A], Either[C,B], Either[A,C], Either[B,C]](_.swap, _.swap) compose left[A,B,C]
}

trait Strong[=>:[_,_]] extends Profunctor[=>:] { // TambaraModule (_,C)
  def first[A,B,C]:  (A =>: B) => ((A,C) =>: (B,C))

  def second[A,B,C]: (A =>: B) => ((C,A) =>: (C,B)) =
    dimap[(C,A), (C ,B), (A, C), (B,C)](_.swap, _.swap) compose first[A,B,C]
}

trait Closed[=>:[_,_]] extends Profunctor[=>:] { // TambaraModule C => _
  def closed[A,B,C]: A=>:B => (C=>A) =>: (C => B)
}

trait Traversing[=>:[_,_]] extends Profunctor[=>:] { // TambaraModule (List[_],C)
  def strechL[A,B,C]: (A =>: B) => (List[A],C) =>: (List[B],C) // TODO replace List by Traverse
  def strechR[A,B,C]: (A =>: B) => (C, List[A]) =>: (C,List[B])
}

trait Polynodal[=>:[_,_]] extends Closed[=>:] with Traversing[=>:] { // TambaraModule D => (List[_],C)
  def griddedL[A,B,C,D]: A=>:B => (D => (List[A],C)) =>: (D => (List[B],C)) =
    closed compose strechL
  def griddedR[A,B,C,D]: A=>:B => (D => (C,List[A])) =>: (D => (C,List[B])) =
    closed compose strechR
}

trait Glassed[=>:[_,_]] extends Strong[=>:] with Closed[=>:] { // TambaraModule (T, U => _)
  def glassedL[A,B,U,T]: (A =>: B) => ((T, U => A) =>: (T, U => B)) =
    second compose closed
  def glassedR[A,B,U,T]: (A =>: B) => ((U => A,T) =>: (U => B,T)) =
    first compose closed
}

trait Magnified[=>:[_,_]] extends Strong[=>:] with Choice[=>:] { // TambaraModule (C, Either[_,D])
  def maginfyLS[A,B,C,D]: (A =>: B) => ((C, Either[A,D]) =>: (C, Either[B,D])) =
    second compose left
  def maginfyRS[A,B,C,D]: (A =>: B) => ((C, Either[D,A]) =>: (C, Either[D,B])) =
    second compose right
  def maginfyLF[A,B,C,D]: (A =>: B) => ((Either[A,D],C) =>: (Either[B,D],C)) =
    first compose left
  def maginfyRF[A,B,C,D]: (A =>: B) => ((Either[D,A],C) =>: (Either[D,B],C)) =
    first compose right
}

trait Telescoped[=>:[_,_]] extends Closed[=>:] with Choice[=>:] { // TambaraModule Either(C => _, D)
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

  type Iso             s t a b = forall p.   Profunctor p => Optic p s t a b  // isomorphism
  type Lens            s t a b = forall p.       Strong p => Optic p s t a b  // divide by
  type Prism           s t a b = forall p.       Choice p => Optic p s t a b  // less or equal
  type Grate           s t a b = forall p.       Closed p => Optic p s t a b  // logarithm
  type Glass           s t a b = forall p.      Glassed p => Optic p s t a b  //
  type AffineTraversal s t a b = forall p.    Magnified p => Optic p s t a b
  type AffineWindow    s t a b = forall p.   Telescoped p => Optic p s t a b
  type Window          s t a b = forall p.     Windowed p => Optic p s t a b  // exponent
  type Traversal       s t a b = forall p.   Traversing p => Optic p s t a b
  type Grid            s t a b = forall p.    Polynodal p => Optic p s t a b -- closed traversal
  type Kaleidescope    s t a b = forall p.        Polyp p => Optic p s t a b
   */

  type Optics[P[_,_],S,T,A,B] = P[A,B] => P[S,T]

  case class Iso[S,T,A,B](from: S => A, to: B => T)
  trait AdapterP[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Profunctor[P]): Optics[P,S,T,A,B]
  }

  // adapterC2P:: Adapter a b s t -> AdapterP a b s t
  // adapterC2P (Adapter o i) = dimap o i
  def isoC2P[S,T,A,B]: Iso[S,T,A,B] => AdapterP[S,T,A,B] = iso => new AdapterP[S,T,A,B] {
    override def apply[P[_,_]](implicit PP: Profunctor[P]): Optics[P,S,T,A,B] =
      (pab: P[A,B]) => PP.dimap(iso.from, iso.to)(pab)
  }

  // Profunctor Adapter
  def AdapterProfunctor[A,B] = new Profunctor[Iso[*,*,A,B]] {
    def dimap[S,T,SX,TX](f: S=>SX, g: TX=>T): Iso[SX,TX,A,B] => Iso[S,T,A,B] = {
      case Iso(from,to) => Iso(f andThen from, to andThen g)
    }
  }

  // adapterP2C :: AdapterP a b s t -> Adapter a b s t
  // adapterP2C l = l (Adapter id id)
  def adapterP2C[S,T,A,B]: AdapterP[S,T,A,B] => Iso[S,T,A,B] = l => {
    def isoOptics: Optics[Iso[*,*,A,B], S,T,A,B] = l(AdapterProfunctor[A,B]) // Optics[P[_,_],S,T,A,B] = P[A,B] => P[S,T]
    isoOptics(Iso[A,B,A,B](identity[A],identity[B]))
  }

  trait Lens[S,T,A,B] {
    def apply[P[_, _]](implicit PP: Strong[P]): Optics[P,S,T,A,B]
  }

  trait Prism[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Choice[P]): Optics[P,S,T,A,B]
  }

  trait Grate[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Closed[P]): Optics[P,S,T,A,B]
  }

  trait Glass[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Glassed[P]): Optics[P,S,T,A,B]
  }

  trait AffineTraversal[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Magnified[P]): Optics[P,S,T,A,B]
  }

  trait AffineWindow[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Telescoped[P]): Optics[P,S,T,A,B]
  }

  trait Window[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Windowed[P]): Optics[P,S,T,A,B]
  }

  trait Traversal[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Traversing[P]): Optics[P,S,T,A,B]
  }

  trait Grid[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Polynodal[P]): Optics[P,S,T,A,B]
  }

  trait Kaleidescope[S,T,A,B] {
    def apply[P[_,_]](implicit PP: Polyp[P]): Optics[P,S,T,A,B]
  }

  /*
  type Iso' s a = Iso s s a a
  type Prism' s a = Prism s s a a
  type Lens' s a = Lens s s a a
  type Traversal' s a = Traversal s s a a
  */
  type Iso1[S,A]       = AdapterP[S,S,A,A]
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
  type ~=[A,B] = AdapterP[B,A,B,A] // type Optic p s t a b = p a b -> p s t    p b a -> p b a
  type <=[A,B] = Prism1[B,A]
  type *|[A,B] = Lens1[B,A]
  type *&[A,B] = Traversal1[B,A]
  type Log[A,B] = Grate[B,A,B,A]
  type Logg[A,B] = Glass[B,A,B,A]
  type AutExp[B,A] = Window[B,A,B,A]

  type Zero = Unit
  type S[A] = Either[A,Zero]
  type One = S[Zero] // Either[Zero,Zero] //  Eiter[Unit,Unit]
  type Two = S[One] // Either[One,Unit] // Either[S[Unit],Unit] // Either[Either[Unit,Unit],Unit]

  // access left of tuple
  class PiOne[A, B, C] extends Lens[(A, C), (B, C), A, B] {

    override def apply[P[_, _]](implicit PP: Strong[P]): Optics[P, (A,C), (B,C), A, B] =
      new Optics[P, (A, C), (B, C), A, B] {
        override def apply(f: P[A, B]): P[(A, C), (B, C)] = {
          def view[X, Y]: Tuple2[X, Y] => X = { case (x, _) => x }
          def update[X, Y, Z]: Tuple2[Y, (X, Z)] => Tuple2[Y, Z] = { case (y, (_, z)) => (y, z) }

          def fork[X, Y, Z](f: X => Y)(g: X => Z): X => (Y, Z) = x => (f(x), g(x))

          def pre: ((A, C)) => (A, (A, C)) = fork(view[A, C])(identity)
          def post: ((B, (A, C))) => (B, C) = update[A, B, C]
          def pabc: P[(A, (A, C)), (B, (A, C))] = PP.first[A, B, (A, C)](f)

          PP.dimap(pre, post)(pabc)
        }
      }
  }

  class The[A, B] extends Prism[Option[A], Option[B], A, B] {

    override def apply[P[_, _]](implicit PP: Choice[P]): Optics[P, Option[A], Option[B], A, B] =
      new Optics[P, Option[A], Option[B], A, B] {
        override def apply(pab: P[A, B]): P[Option[A], Option[B]] = {
          def math[X, Y]: Option[X] => Either[Option[Y], X] = {
            case Some(a) => Right(a)
            case None => Left(None)
          }
          def build[Y]: Y => Option[Y] = b => Some(b)
          def either[X, Y, Z](f: X => Z)(g: Y => Z): Either[X, Y] => Z = {
            case Left(a) => f(a)
            case Right(b) => g(b)
          }

          def pre: Option[A] => Either[Option[B], A] = math[A, B]
          def post: Either[Option[B], B] => Option[B] =
            either(identity[Option[B]])(build[B])
          def pabc[C]: P[Either[C, A], Either[C, B]] = PP.right[A,B,C](pab)

          PP.dimap(pre, post)(pabc)
        }
      }
  }
}