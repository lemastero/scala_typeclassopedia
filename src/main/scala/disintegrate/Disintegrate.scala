package disintegrate

import contravariant.InstancesForContravariantFunctor.Predicate
import educational.{Contravariant, Functor, State}
import semigroup.MonoidSimpleImpl.Monoid

import scala.language.higherKinds

// https://twitter.com/sigfpe/status/1195783717655986176
// https://en.wikipedia.org/wiki/Disintegration_theorem
// http://homes.sice.indiana.edu/ccshan/rational/disintegrator.pdf
trait Disintegrate[M[_]] {
  def disintegrate[A,B](mab: M[(A,B)]): (M[A], A => M[B])

  // TODO https://twitter.com/Iceland_jack/status/1195787833203658752 you need Monad instance
//  def law[A,B](pairs: M[(A,B)])(implicit FM: FlatMap[M]) = {
//    val (as,bs) = disintegrate(pairs)
//    for {
//      a <- as
//      b <- bs(a)
//    } yield (a,b)
//  }
}

// https://twitter.com/sigfpe/status/1195791272906084352
object Disintegrate {
  val disSet: Disintegrate[Set] = new Disintegrate[Set] {
    def disintegrate[A, B](sab: Set[(A, B)]): (Set[A], A => Set[B]) = {
      val sa: Set[A] = sab.map(_._1)
      val asb: A => Set[B] = a => sab.filter(_._1 == a).map(_._2)
      (sa, asb)
    }
  }

  val disThunk: Disintegrate[() => *] = new Disintegrate[() => *] {
    override def disintegrate[A, B](uab: () => (A, B)): (() => A, A => () => B) = {
      val ua: () => A = () => uab()._1
      val aub: A => () => B = _ => () => uab()._2
      (ua, aub)
    }
  }

  def disFunction[C]: Disintegrate[C => *] = new Disintegrate[C => *] {
    def disintegrate[A, B](cab: C => (A, B)): (C => A, A => C => B) = {
      val ca: C => A = c => cab(c)._1
      val acb: A => C => B = _ => c => cab(c)._2
      (ca, acb)
    }
  }

  def disFunctor[F[_]](implicit FC: Functor[F]): Disintegrate[F] = new Disintegrate[F] {
    def disintegrate[A, B](fab: F[(A, B)]): (F[A], A => F[B]) = {
      val fa: F[A] = FC.map(fab)(_._1)
      val afb: A => F[B] = _ => FC.map(fab)(_._2)
      (fa, afb)
    }
  }

  // https://twitter.com/polyaletheia/status/1195793040159305728
  def distState[S]: Disintegrate[State[S, *]] = new Disintegrate[State[S, *]] {
    def disintegrate[A, B](mab: State[S, (A, B)]): (State[S, A], A => State[S, B]) = {
      val sba: State[S, A] = {
        val f: S => (A,S) = b => {
          val (ab, b2) = mab.runState(b)
          (ab._1,b2)
        }
        State(f)
      }
      val asbb: A => State[S, B] = _ => {
        val g: S => (B, S) = b => {
          val (ab,b2) = mab.runState(b)
          (ab._2, b2)
        }
        State(g)
      }
      (sba, asbb)
    }
  }
}

// https://twitter.com/Iceland_jack/status/1195800542557196290
trait DisintegrateMonoid[F[_]] {
  def disintegrate[A,B](fab: F[(A,B)])(implicit MB: Monoid[B]): (F[A], A => F[B])
}

object DisintegrateMonoid {
  val disMonPredicate: DisintegrateMonoid[Predicate] = new DisintegrateMonoid[Predicate] {

    def disintegrate[A, B](fab: Predicate[(A, B)])(implicit MB: Monoid[B]): (Predicate[A], A => Predicate[B]) = {
      val fa: A => Boolean = a => fab.fun((a,MB.empty))
      val pa: Predicate[A] = Predicate(fa)
      val apb: A => Predicate[B] = a => Predicate(b => fab.fun((a,b)))
      (pa, apb)
    }
  }

  def disMonFun[C]: DisintegrateMonoid[* => C] = new DisintegrateMonoid[* => C] {
    def disintegrate[A, B](fab: ((A, B)) => C)(implicit MB: Monoid[B]): (A => C, A => B => C) = {
      val ac: A => C = a => fab((a, MB.empty))
      val abc: A => B => C = a => b => fab((a,b))
      (ac, abc)
    }
  }

  def disMonContra[F[_]](implicit C: Contravariant[F]): DisintegrateMonoid[F] = new DisintegrateMonoid[F] {
    def disintegrate[A, B](fab: F[(A, B)])(implicit MB: Monoid[B]): (F[A], A => F[B]) = {
      val ma: F[A] = C.contramap(fab)(a => (a, MB.empty))
      val amb: A => F[B] = a => C.contramap(fab)(b => (a,b))
      (ma, amb)
    }
  }
}
