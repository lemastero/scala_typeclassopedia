package contravariant

import educational.category_theory.Functor
import educational.category_theory.contra.Contravariant

/**
  * Exploration of the structure that forms covariant and contravariant functors.
  * ---
  *
  * Intro
  * ---
  * Currying produce functions in form:
  * (a -> b)
  * a -> (b -> c)
  * a -> ((b -> c) -> d)
  * ...
  *
  * All types are in negative position (they are input-like) except the last one - on positive position.
  * -A +B
  * -A -B +C
  * -A -B -C +D
  * -A -B -C -D +E
  * ...
  * or as Bartosz Milewski pointed it follows from fact that currying basically change:
  * (A B C D) => E
  * into
  * A => (B => (C => (D => E)))
  * etc.
  *
  * For types on positive position - you could define Functor (if it does not occures on negative position as well)
  *
  * Exploration
  * ---
  * In here I explore what would happen if you align braces in different direction:
  * (a -> b)
  * (a -> b) -> c
  * ((a -> b) -> c) -> d
  * ...
  *
  * It looks like variance switch from negative to positive when you add one more bracket.
  * -A +B
  * +A -B +C
  * -A +B -C +D
  * +A -B +C -D +E
  * ...
  *
  * If you reverse the types (here LambdaXYZ type aliases):
  * +B -A
  * +C -B +A
  * +D -C +B -A
  * +E -D +C -B +A
  * ...
  *
  * Further development
  * ---
  *
  * TODO
  * - examples for (consider Reader, thunk, IO, functions)
  * - test Functor/Contravariant laws
  * - re-use implementation of methods
  * - every paif of contravariant and functor form a prounctor/arrow - encode it
  * - new ways to compose profunctors?
  * - derive automatically Functor, Contravariant instances or methods itself
  *
  * Theory:
  * - relation to SKI combinators
  * - already in combinators from: To Mock a Mockingbird Paperback - Raymond Smullyan?
  * - formalize in Idris/Agda/Coq
  */
class HoFStructure {

  type LambdaAB[-B,+A] = B => A
  implicit def funB[B]: Functor[B => *] = new Functor[B => *] {
    def map[A, AA](fa: B => A)(f: A => AA): B => AA =
      fa andThen f
  }
  implicit def contraA[A]: Contravariant[* => A] = new Contravariant[* => A] {
    def contramap[B, BB](fa: B => A)(f: BB => B): BB => A =
      f andThen fa
  }

  type LambdaABC[+C,-B,+A] = (C => B) => A
  def funBA[B,A]: Functor[LambdaABC[*, B, A]] = new Functor[LambdaABC[*,B,A]] {
    def map[C, CC](fa: (C => B) => A)(f: C => CC): (CC => B) => A =
      g => fa(f andThen g)
  }
  def contraCA[C,A]: Contravariant[LambdaABC[C, *, A]] = new Contravariant[LambdaABC[C,*,A]] {
    def contramap[B, BB](fa: LambdaABC[C, B, A])(f: BB => B): LambdaABC[C, BB, A] =
      g => fa(g andThen f)
  }

  type LambdaABCD[-D,+C,-B,+A] = ((D => C) => B) => A
  def contraCBA[C,B,A]: Contravariant[LambdaABCD[*,C,B,A]] = new Contravariant[LambdaABCD[*,C,B,A]] {
    def contramap[D, DD](fa: LambdaABCD[D, C, B, A])(f: DD => D): LambdaABCD[DD, C, B, A] =
      h => fa(g => h(f andThen g))
  }
  def funDBA[D,B,A]: Functor[LambdaABCD[D,*,B,A]] = new Functor[LambdaABCD[D,*,B,A]] {
    def map[C, CC](fa: LambdaABCD[D, C, B, A])(f: C => CC): LambdaABCD[D, CC, B, A] =
      h => fa(g => h(g andThen f))
  }
  // ...
}
