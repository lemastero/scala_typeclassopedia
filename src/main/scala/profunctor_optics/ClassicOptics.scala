package profunctor_optics

// Profunctor Optics
// Modular Data Accessors
// https://www.cs.ox.ac.uk/people/jeremy.gibbons/publications/poptics.pdf

import cats.{Applicative, Functor}
import educational.optics.~

object MonocleOptics {
  case class Iso[S,A](      from: S => A,          to:     A     => S)
  case class Prism[S,A](    get:  S => Option[A],  revGet: A     => S)
  case class Lens[S,A](     view: S => A,          update: (A,S) => S)
  case class Optional[S,A]( get:  S => Option[A],  set:    (A,S) => S)
}

object GeneralizedOptics {

  // optics
  // - adapters   - conversion
  // - lens       - access in product type (pair), n-th element of list, field in record
  // - prism      - access in sum type (either, sealed trait)
  // - traversals - iteration

  // not closed under heterogeneous composition (into a sum into a product - not lens, not prism)
  // homogeneous composition is possible (lens + lens) but not convenient

  // represent as getter setter
  // getter - view
  // setter - update

  // onto a component of type A within larger data structure of S
  case class LensSimple[A,S](
    view:   S => A,     // extract component A from context S
    update: (A,S) => S  // take new component A and old data structure S and yields a new structure
  )

  //    view     S => A
  // S ----------> A
  //  \
  //   |
  //  /
  // \/   update (B,S) => T
  // T <--------- B

  // data Lens a b s t = Lens { view:: s -> a, update :: b x s -> t }
  case class Lens[+A,-B,-S,+T](
    view: S => A,
    update: (B,S) => T
  )

  def tupleFoucsFirs[A,B,C]: Lens[A, B, (A,C), (B,C)] = {
    def getFst: ((A,C)) => A = { case(a,_)     => a }
    def overrideFst: (B,(A,C)) => (B,C) = { case(b,(_,c)) => (b,c) }

    Lens[A,B,(A,C),(B,C)](getFst, overrideFst)
  }

  val sign: Lens[Boolean, Boolean, Int, Int] = {
    def isNonNegative: Int => Boolean = _ >= 0
    def asNonNegative: (Boolean,Int) => Int = { case(b,n) =>
      if(b) Math.abs(n)
      else -Math.abs(n)
    }

    Lens[Boolean, Boolean, Int, Int](isNonNegative, asNonNegative)
  }

  // given compound data structure S that have variants A and B
  case class SimplePrism[A,S](
    matches: S => Either[S,A], // access variant A if possible or return original structure S if not
    build: A => S // upcast A into data structure S
  )

  // prisms are dual to lens
  // prism for coproduct
  // lens  for product

  //    match         S => Either[T,A]
  // S ----------> A
  //  \
  //   |
  //  /
  // \/   build        B => T
  // T <--------- B
  case class Prism[+A,-B,-S,+T](
    matsh: S => Either[T,A],
    build: B => T
  )

  def optionPrism[A,B]: Prism[A,B,Option[A],Option[B]] = {
    val upcast: B => Option[B] = Some.apply
    val downCast: Option[A] => Either[Option[B],A] = {
      case Some(a) => Right(a)
      case None => Left(None)
    }
    Prism[A,B,Option[A],Option[B]](
      matsh = downCast,
      build = upcast
    )
  }

  def intToDoublePrism: Prism[Int,Int,Double,Double] = {
    val asDouble: Int => Double = _.toDouble
    val asInt: Double => Either[Double,Int] = d => {
      if(d.isValidInt) Right(d.toInt)
      else Left(d)
    }
    Prism[Int,Int,Double,Double](
      matsh = asInt,
      build = asDouble
    )
  }

  //     from
  // S ----------> A
  //
  //      to        B => T
  // T <--------- B
  case class Iso     [-S,+T,+A,-B](  from:   S => A,            to:     B     => T )

  def tupleIso[A,B,C]: Iso[(A,B,C), (A,B,C), ((A,B),C), ((A,B),C)] = {
    def from1: ((A,B,C)) => ((A,B),C) = { case (x, y, z) => ((x, y), z) }
    def to1: (((A,B),C)) => (A,B,C) = { case ((x, y), z) => (x, y, z) }

    Iso[(A,B,C),(A,B,C),((A,B),C),((A,B),C)](from1, to1)
  }
}

object GeneralizedHaskellOptics {
  case class Iso       [-S,+T,+A,-B](       from:   S => A,            to:     B     => T )
  case class Prism     [-S,+T,+A,-B](       matsh:  S => Either[T,A],  revGet: B     => T )
  case class Lens      [-S,+T,+A,-B](       view:   S => A,            update: (B,S) => T )

  // isomorphic to exists n. A^n x (B^n => T)
  // https://twanvl.nl/blog/haskell/non-regular1
  // Holy Hand Grenade!

sealed trait FunList[A,B,T]
case class Done[A,B,T](v: T)
  extends FunList[A,B,T]
case class More[A,B,T](h: A, t: FunList[A,B,B=>T])
  extends FunList[A,B,T]

  // page 9
  // FunList[A,B,T] ~Either[T,(A,FunList[A,B,B=>])]
  def funEitherIso[A,B,T]: ~[FunList[A,B,T], Either[T,(A,FunList[A,B,B=>T])]] =
    new ~[FunList[A,B,T], Either[T,(A,FunList[A,B,B=>T])]] {
      override def to: Either[T, (A, FunList[A, B, B => T])] => FunList[A, B, T] = {
        case Left(t) => Done(t)
        case Right((x,l)) => More(x,l)
      }

      override def from: FunList[A, B, T] => Either[T, (A, FunList[A, B, B => T])] = {
        case Done(t) => Left(t)
        case More(x,l) => Right((x,l))
      }
    }

  def FunListFunctor[A,B]: Functor[FunList[A,B,*]] = new Functor[FunList[A,B,*]] {
    override def map[X,Y](fa: FunList[A,B,X])(f: X => Y): FunList[A,B,Y] = fa match {
      case Done(t) => Done(f(t))
      case More(x,l) =>
        val v1: FunList[A,B,B=>Y] = map(l)(bx => bx andThen f)
        More(x, v1)
    }
  }

  def FunListApplicative[A,B]: Applicative[FunList[A,B,*]] = new Applicative[FunList[A,B,*]] {
    override def pure[X](x: X): FunList[A,B,X] = Done(x)
    override def ap[X,Y](ff: FunList[A,B,X=>Y])(fa: FunList[A,B,X]): FunList[A,B,Y] = ff match {
      case Done(f) => map(fa)(f)
      case More(h, t) => ???
    }
  }

  case class Traversal[A,B,S,T](extract: S => FunList[A,B,T])
  // (A => F B) => (S => F T) is withenss traversability of S
  // contents: S => List[A]   yield elements of traversal
  // fill : (S,List[B]) => T  old container and new sequence of elems, and return new with replacing old ons by new ones

  case class Optional[S,T,A,B](      get:  S => Option[A],  set:    (A,S) => S )
}

object ProfunctorOptics {
  type \/[A,B] = Either[A,B]

  trait Profunctor[p[_,_]] {
    def dimap[a,b,c,d](f: c => a)(g: b => d)(h: p[a,b]): p[c,d]
  }

  trait Cartesian[p[_,_]] extends Profunctor[p] { // Strong
    def first[a,b,c](h: p[a,b]): p[(a,c),(b,c)]
  }

  trait CoCartesian[p[_,_]] extends Profunctor[p] { // Choice
    def right[a,b,c](h: p[a,b]): p[c \/ a, c \/ b]
  }

  trait Monoidal[p[_,_]] extends Profunctor[p] {
    def par[a,b,c,d](h: p[a,b])(k: p[c,d]): p[(a,c),(b,d)]
    def empty: p[Unit,Unit]
  }

  abstract class Adapter[p[_,_],a,b,s,t] {
    def apply(h:p[a,b])(implicit prof: Profunctor[p]): p[s,t]
  }

  abstract class Lens[p[_,_],a,b,s,t]{
    def apply(h:p[a,b])(implicit prof: Cartesian[p]): p[s,t]
  }

  abstract class Prism[p[_,_],a,b,s,t]{
    def apply(h:p[a,b])(implicit prof: CoCartesian[p]): p[s,t]
  }

  abstract class Traversal[p[_,_],a,b,s,t]{
    def apply(h:p[a,b])(implicit prof: Cartesian[p]
      with CoCartesian[p]
      with Monoidal[p]): p[s,t]
  }

  // access left of tuple
  class PiOne[P[_,_],A,B,C] extends Lens[P,A,B,(A,C),(B,C)] {
    override def apply(f: P[A,B])(implicit prof: Cartesian[P]): P[(A,C), (B,C)] = {
      def view[X,Y]: Tuple2[X,Y] => X = { case (x,_) => x }
      def update[X,Y,Z]: Tuple2[Y,(X,Z)] => Tuple2[Y,Z] = { case(y,(_,z)) => (y,z) }
      def fork[X,Y,Z](f: X => Y)(g: X => Z): X => (Y,Z) = x => (f(x),g(x))

      def foo: ((A, C)) => (A, (A, C)) = fork(view[A,C])(identity)
      def bar: ((B, (A, C))) => (B, C) = update[A,B,C]

      def quux: P[(A, (A,C)), (B, (A,C))] = prof.first(f)

      prof.dimap(foo)(bar)(quux)
    }
  }

  class The[P[_,_],A,B] extends Prism[P,A,B, Option[A], Option[B]] {
    override def apply(pab: P[A,B])(implicit prof: CoCartesian[P]): P[Option[A], Option[B]] = {
      def math[X,Y]: Option[X] => Either[Option[Y],X] = {
        case Some(a) => Right(a)
        case None => Left(None)
      }
      def build[Y]: Y => Option[Y] = b => Some(b)
      def either[X,Y,Z](f: X=>Z)(g: Y => Z): Either[X,Y] => Z = {
        case Left(a) => f(a)
        case Right(b) => g(b)
      }

      def foo: Option[A] => Either[Option[B], A] = math[A,B]
      def bar: Either[Option[B], B] => Option[B] =
        either(identity[Option[B]])(build[B])
      def quux[C]: P[Either[C,A], Either[C,B]] = prof.right(pab)

      prof.dimap(foo)(bar)(quux)
    }
  }

  // TODO isomorphism AdapterP to Adapter


  // data UpStar f a b = UpStar {unUpStar:: a → f b}
  case class UpStar[F[_],A,B](
    run: A => F[B]
  )

  def UpStarProfunctor[P[_]](MM: Functor[P]): Profunctor[UpStar[P,*,*]] = new Profunctor[UpStar[P,*,*]] {
    def dimap[a, b, c, d](f: c => a)(g: b => d)(pab: UpStar[P, a, b]): UpStar[P, c, d] =
      UpStar ( s =>
        MM.map((pab.run compose f)(s))(g)
      )
  }
}
