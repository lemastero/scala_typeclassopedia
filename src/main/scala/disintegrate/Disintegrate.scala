package disintegrate

import cats.FlatMap
import cats.syntax.flatMap._
import educational.abstract_algebra.Monoid
import educational.data.{Predicate, State}
import educational.category_theory.Functor
import educational.category_theory.contra.Contravariant

// https://twitter.com/sigfpe/status/1195783717655986176
// https://en.wikipedia.org/wiki/Disintegration_theorem
// http://homes.sice.indiana.edu/ccshan/rational/disintegrator.pdf
trait Disintegrate[M[_]] {
  def disintegrate[A,B](mab: M[(A,B)]): (M[A], A => M[B]) = {
    (disintegrate1(mab), disintegrate2(mab))
  }
  def disintegrate1[A,B](mab: M[(A,B)]): M[A]
  def disintegrate2[A,B](mab: M[(A,B)]): A => M[B]

  // https://twitter.com/Iceland_jack/status/1195787833203658752
  def law[A, B](pairs: M[(A, B)])(implicit FM: FlatMap[M]): Boolean = {
    val (a, amb): (M[A], A => M[B]) = disintegrate(pairs)
    pairs == a.mproduct(amb)
  }
}

// https://twitter.com/sigfpe/status/1195791272906084352
object Disintegrate {
  val disSet: Disintegrate[Set] = new Disintegrate[Set] {
    def disintegrate1[A, B](sab: Set[(A, B)]): Set[A] =
      sab.map(_._1)

    def disintegrate2[A, B](sab: Set[(A, B)]): A => Set[B] =
      a => sab.filter(_._1 == a).map(_._2)
  }

  val disThunk: Disintegrate[() => *] = new Disintegrate[() => *] {
    def disintegrate1[A, B](uab: () => (A, B)): () => A =
      () => uab()._1

    def disintegrate2[A, B](uab: () => (A, B)): A => () => B =
      _ => () => uab()._2
  }

  def disFunction[C]: Disintegrate[C => *] = new Disintegrate[C => *] {
    def disintegrate1[A, B](cab: C => (A, B)): C => A =
      c => cab(c)._1

    def disintegrate2[A, B](cab: C => (A, B)): A => C => B =
      _ => c => cab(c)._2
  }

  def disFunctor[F[_]](implicit FC: Functor[F]): Disintegrate[F] = new Disintegrate[F] {
    def disintegrate1[A, B](fab: F[(A, B)]): F[A] =
      FC.map(fab)(_._1)

    def disintegrate2[A, B](fab: F[(A, B)]): A => F[B] =
      _ => FC.map(fab)(_._2)
  }

  // https://twitter.com/polyaletheia/status/1195793040159305728
  def distState[S]: Disintegrate[State[S, *]] = new Disintegrate[State[S, *]] {
    def disintegrate1[A, B](mab: State[S, (A, B)]): State[S, A] = {
      State(b => {
        val (ab, b2) = mab.runState(b)
        (ab._1,b2)
      })
    }

    def disintegrate2[A, B](mab: State[S, (A, B)]): A => State[S, B] = _ => {
      State(b => {
        val (ab, b2) = mab.runState(b)
        (ab._2, b2)
      })
    }
  }
}

// https://twitter.com/Iceland_jack/status/1195800542557196290
trait DisintegrateMonoid[F[_]] {
  def disintegrate[A,B](fab: F[(A,B)])(implicit MB: Monoid[B]): (F[A], A => F[B]) =
    (disintegrate1(fab), disintegrate2(fab))

  def disintegrate1[A,B](fab: F[(A,B)])(implicit MB: Monoid[B]): F[A]
  def disintegrate2[A,B](fab: F[(A,B)]): A => F[B]
}

object DisintegrateMonoid {

  val disMonPredicate: DisintegrateMonoid[Predicate] = new DisintegrateMonoid[Predicate] {

    def disintegrate1[A, B](fab: Predicate[(A, B)])(implicit MB: Monoid[B]): Predicate[A] =
      Predicate(a => fab.fun((a, MB.empty)))

    def disintegrate2[A, B](fab: Predicate[(A, B)]): A => Predicate[B] =
      a => Predicate(b => fab.fun((a,b)))
  }

  def disMonFun[C]: DisintegrateMonoid[* => C] = new DisintegrateMonoid[* => C] {

    def disintegrate1[A, B](fab: ((A, B)) => C)(implicit MB: Monoid[B]): A => C =
      a => fab((a, MB.empty))

    def disintegrate2[A, B](fab: ((A, B)) => C): A => B => C = {
     a => b => fab((a,b))
    }
  }

  def disMonContra[F[_]](implicit C: Contravariant[F]): DisintegrateMonoid[F] = new DisintegrateMonoid[F] {
    def disintegrate1[A, B](fab: F[(A, B)])(implicit MB: Monoid[B]): F[A] =
      C.contramap(fab)(a => (a, MB.empty))

    def disintegrate2[A, B](fab: F[(A, B)]): A => F[B] =
      a => C.contramap(fab)(b => (a,b))
  }
}

// TODO what if
trait DisintegrateE[F[_]] {
  def disintegrate[X,Y](fab: F[Either[X,Y]])(implicit MB: Monoid[Y]): Either[F[X], X => F[Y]]
}

object DisintegrateE {
  def disEither[C]: DisintegrateE[* => C] = new DisintegrateE[Function[?, C]] {
    def disintegrate[X, Y](fab: Function[Either[X, Y], C])(implicit MB: Monoid[Y]): Either[Function[X, C], X => Function[Y, C]] = ???
  }
}