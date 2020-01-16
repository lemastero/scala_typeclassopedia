package ct_other_impls

import cats.MonadError
import cats.effect.IO

// CLowns to the Left, JokeRs to the right and Bards to the back. F[E,A,R] the fun trio!


// Clowns to the left of me, jokers to the right (pearl): dissecting data structures,
// Conor McBride
// https://personal.cis.strath.ac.uk/conor.mcbride/Dissect.pdf

// Haskell define wrappers that implement Functor map for different type:
// * Clown: https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Clown.html
// * Joker: https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Joker.html
// Cats allow you to convert Bifunctor int LeftFunctor or RightFunctor: https://github.com/typelevel/cats/pull/1847/files

// What if ... we actually have implementation on Functor for type constructor with 2 holes that ignore one hole?

// law: mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g
trait Clown[F[_,_]] {
  def mapLeft[A,AA,B](fa: F[A,B])(g: A => AA): F[AA,B]
}

// law: map (f compose g) ≡ map f compose map g
trait Joker[F[_,_]] {
  def map[A,B,BB](fa: F[A,B])(g: B => BB): F[A,BB]
}

// We can define using them Bifunctor

// laws:
// bimap  (f compose g) (h compose i) ≡ bimap f h compose bimap g i
// mapLeft  (f compose g) ≡ mapLeft  f compose mapLeft  g - inherited from Clown
// map (f compose g) ≡ map f compose map g - inherited from Joker
trait Bifunctor[F[_,_]] extends Joker[F] with Clown[F] {

  def bimap[AA,A,B,BB](fa: F[A,B])(f: A => AA, g: B => BB): F[AA, BB] = {
    val v1: F[A, BB] = map(fa)(g)
    val v2: F[AA, BB] = mapLeft(v1)(f)
    v2
  }
}

// to the clasic duet of Joker and Clown we can add a thrid player, that goes contra to the current music trends - Bard:

// law: contramap (f andThen g) ≡ contramap f andThen contramap g
trait Bard[F[_,_]] {
  def contramap[A,AA,B](fa: F[A,B])(f: AA => A): F[AA,B]
}

// now we can define Profunctor

// laws:
// dimap  (f andThen g) (h andThen i) ≡ dimap f h andThen bimap g i
// map (f compose g) ≡ map f compose map g -- from Joker
// contramap (f andThen g) ≡ contramap f andThen contramap g -- from Bard
trait Profunctor[F[_,_]] extends Joker[F] with Bard[F] {

  def dimap[AA,A,B,BB](fa: F[A,B])(f: AA => A, g: B => BB): F[AA, BB] =
    map(contramap(fa)(f))(g)
}

// Following implementation of Profunctor and Bifunctor in Scala have follwing benefits
// - modularity as we express two more complex structures using simpler ones
// - Joker definition refactors common part of Bifuctor and Profunctor
//   - common vocabulary for method that is common for Bifunctor and Profunctor
//   - common laws for Bifunctor and Profunctor
// - three new absractions with nice set of laws! we can use where we can't use Bifunctor or Profunctor

// when both types are on the covariant side:

trait Dijkstra[S,A] {
  def runWp[R](f: (S,A) => R): S => R
}

object SomeInstances {
  val dijkstraBard: Bard[Dijkstra] = new Bard[Dijkstra] {
    def contramap[S,SS,A](fa: Dijkstra[S, A])(f: SS => S): Dijkstra[SS,A] = ??? // TODO
  }
}

// or some are invariant

case class Writer[R, A](runWriter: A => (R, A))
case class State[S,A](runState: S => (A,S))

object SomeInstances2 {
  val writerClown: Clown[Writer] = new Clown[Writer] {
    def mapLeft[R, RR, A](fa: Writer[R, A])(g: R => RR): Writer[RR, A] = Writer{ a =>
      val (r,a2) = fa.runWriter(a)
      (g(r),a2)
    }
  }

  val stateJoker: Joker[State] = new Joker[State] {
    def map[A, B, BB](fa: State[A, B])(g: B => BB): State[A, BB] = ??? // TODO
  }

  case class Deamon()

  trait Warlock[A,B] {
    def summonDeamon(soulStone: B): Deamon
    def trapSoul(creature: Any): B
    def castSpell(): A
  }

  val warlockClown = new Clown[Warlock] {
    def mapLeft[A, AA, B](fa: Warlock[A, B])(g: A => AA): Warlock[AA, B] = new Warlock[AA,B] {
      def summonDeamon(soulStone: B): Deamon = ???
      def trapSoul(creature: Any): B = ???
      def castSpell(): AA = ???
    }
  }
}

// Emergence of abstractions with 3 type parameters
// in Haskell [snoyberg//trio](https://github.com/snoyberg/trio)
// in Scala ZIO: https://github.com/zio/zio
// encourage to explore type constructors with three holes

// laws in each case are similar like in versions with 2 holes

trait TriBard[F[_,_,_]] {
  def contramap[E,A,R,EE](fa: F[E,A,R])(f: EE => E): F[EE,A,R]
}

trait TriClown[F[_,_,_]] {
  def mapLeft[E,A,R,AA](fa: F[E,A,R])(g: A => AA): F[E,AA,R]
}

trait TriJoker[F[_,_,_]] {
  def map[A,E,R,RR](fa: F[A,E,R])(h: R => RR): F[A,E,RR]
}

// now we can express Biffunctor with 3-hole type constructor

trait TriBiff[F[_,_,_]] extends TriClown[F] with TriJoker[F] {

  def bimap[E,A,R,AA,RR](fa: F[E,A,R])(g: A => AA, h: R => RR): F[E,AA,RR]
}

// two different variants of Profunctor with 3 params:

trait TriProfJoker[F[_,_,_]] extends TriBard[F] with TriJoker[F] {

  def dimap[E,A,R,EE,RR](fa: F[E,A,R])(f: EE => E, h: R => RR): F[EE,A,RR]
}

trait TriProfClown[F[_,_,_]] extends TriBard[F] with TriClown[F] {

  def dimapLeft[E,A,R,EE,AA](fa: F[E,A,R])(f: EE => E, g: A => AA): F[EE,AA,R]
}

// and final abstraction FunTrio

trait FunTrio[F[_,_,_]]
  extends TriBard[F] with TriClown[F] with TriJoker[F]
    with TriBiff[F]
    with TriProfClown[F] with TriProfJoker[F] {

  def timap[E,A,R,EE,AA,RR](fa: F[E,A,R])(f: EE => E, g: A => AA, h: R => RR): F[EE,AA,RR]
}

// that happen to be the one abstraction to rule them all:

object FunTrioInstances {
  case class ZIO[A,E,R](aer: A=>Either[E,R]) // TODO use real ZIO

  type FunctionToEither[A,L,R] = A => Either[L,R]
  val funTrioFunctionToEither: FunTrio[FunctionToEither] = ???

  type FunctionTuple2[A,L,R] = A => (L,R)
  val funTrioFunctionToTuple: FunTrio[FunctionTuple2] = ???

  type LambdaABC[-B,+A,+C] = (C => B) => A // more on this on my rant on Twitter https://twitter.com/pparadzinski/status/1205202077238091776
  val funTrioFunctionABC: FunTrio[LambdaABC] = ??? // TODO is contravariant on second param not first, Scala 3 ?

  val funTrioZio: FunTrio[ZIO] = ??? // TODO embedding of ZIO into TRIO
  //val funTrioIO: FunTrio[PrIO] // TODO embedding of profunctor IO
  //val funTrioIO: FunTrio[PrIO] // TODO embedding of bifunctor IO

  type RIO[E,A,R] = A => Either[E,IO[R]]
  def funRIO[A,E]: FunTrio[RIO] = ??? // TODO embedding of cats IO/monix Task into FunTrio

  type MonadError3[F[_], E, A, R] = A => MonadError[F,E]
  def funTrioIO[F[_]]: FunTrio[MonadError3[F,*,*,*]] = ??? //TODO embedding cats-mtl into FunTrio
}


/*
BTW Here is my original idea, that have unfortunate drawback that do not compile in Scala :D

trait Functor[F[_]] extends RightCovariant[F[Nothing,_]] {
  def map[B,BB](fa: F[B])(g: B => BB): F[BB]
}

trait Contravariant[F[_]] extends ContravariantDi[F[_, Nothing]]{
  override def contramap[A,AA](fa: F[A])(f: AA => A): F[AA]
}
*/