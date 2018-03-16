package kan

import cats.Functor

import scala.language.higherKinds

/**
  * Right Kan Extension (Ran G H) of h along g
  *
  * Right Kan Extension in Haskell
  *
  * newtype Ran g h a = Ran
  *   { runRan :: forall b. (a -> g b) -> h b }
  *
  * Right Kan Extension in Category Theory:
  * * We have categories: A, B, C and functors: X : A -> C, F : A -> C
  *   Right Kan Extension of X along F is:
  *   - a functor R : B -> C
  *   - natural transformation (eta) n: RF -> X
  *
  *         X
  *    A ------> C
  *    |       /\
  *  F |      /
  *    |    /  R
  *   \/  /
  *    B
  *
  *   such that for every other candidate:
  *   - functor M : B -> C
  *   - natural transformation u : MF -> X
  *   then there is unique natural transformation d: M -> R such that following diagram commute:
  *
  *         RF
  *    n  /    /\
  *     /        \  dF
  *   \/          \
  *   X ----------> MF
  *         u
  *
  *  where for all a from A: dF(a) = d(Fa): MF(a) -> RF(a)
  *
  * * universal construction related to adjoints, limits and ends (TODO explain)
  * * Saunders Mac Lane in "Categories for the Working Mathematician" have written:
  *  "All Concepts Are Kan Extensions"
  *  "The notion of Kan extensions subsumes all the other fundamental concepts of category theory."
  * * generalize the notion of extending a function defined on a subset to a function defined on the whole set,
  *   in category of posets it is related to constrained optimization (TODO how?)
  *
  */
trait Ran[G[_], H[_], A] { thisRan =>

  def runRan[B](f: A => G[B]): H[B]

  def map[B](f: A => B): Ran[G, H, B] =
    new Ran[G, H, B] {
      def runRan[C](f2: B => G[C]): H[C] =
        thisRan.runRan(f andThen f2)
    }
}

object RanInstances {

  def ranFunctor[G[_], H[_]]: Functor[Ran[G, H, ?]] =
    new Functor[Ran[G, H, ?]] {
      def map[A, B](fa: Ran[G, H, A])(f: A => B): Ran[G, H, B] = fa.map(f)
    }
}
