package ct_other_impls

import ct_other_impls.FPAbs.{ConstUnit, Id}
import educational.abstract_algebra.Monoid

/*
Are there any constructions in Category Theory something like:

Given: category C, D, E and two mappings F : C -> D and G : C => E
we will note D as F[C] and E as G[C].

  ------- CxC ----------
  |        |           |
  |        | Morphism  |
  |        |           |
F |        \/          | G
  |     Morphism[C]    |
  |        |           |
  |        | run       |
  |        |           |
  \/       \/          \/
  F[C] --------------> G[C]


             G     F
      G[C] <=== C ===> F[C]
      run: Morphism[A,B] => (F[A] => F[B])

|----------------|----------------|--------------|-------------------|
|                | Morphism[C]                                       |
|----------------|----------------|--------------|-------------------|
|  abstraction   | Morphism       |   identity   | composition       |
|----------------|----------------|--------------|-------------------|
| Functor        | function       | identity     | andThen           |
| FlatMap        | Kleisli        | pure         | kleisli comp.     |
| CoflatMap      | CoKleisli      | extract      | cokleisli comp.   |
| FlatMap op     | Kleisli op     | OK           | OK                | !!! contravariant Monad
| CpflatMap op   | CoKleisli op   | OK           | OK                | !!! contravariant Comonad
| Apply          | F[A => B]      | OK           | OK                |
| Contravariant  | inv. function  | identity     | compose           |
| Apply op       | F[B => A]      | ?            | ?                 | impossible TODO redefine laws
| Zip            | F[(A, B)]      | ?            | ?                 | impossible TODO redefine laws
| Alternative    | F[A \/ B]      | ?            | ?                 | impossible TODO redefine laws
| Pure           | B              | OK           | second            |
| Extract        | F[B]           | OK           | first             |
|----------------|----------------|--------------|-------------------|

 */

// F          F    A    => B     S[F[_], G[_], A, B] = A => B       Functor
// F          F    B    => A     S[F[_], G[_], A, B] = B => A       Contravariant

// F          F    A    => F[B]  S[F[_], G[_], A, B] = A => F[B]    FlatMap
// F          F    F[A] => B     S[F[_], G[_], A, B] = F[A] => B    CoflatMap

// F          F    F[(A, B)]     S[F[_], G[_], A, B] = F[(A, B)]    Zip
// F          F    F[A \/ B]     S[F[_], G[_], A, B] = F[A \/ B]]   Alt

// F          F    F[A => B]     S[F[_], G[_], A, B] = F[A => B]    Ap (Apply)
// F          F    F[B => A]     S[F[_], G[_], A, B] = F[B => A]    Divide

// ConstUnit  F    B             S[F[_], G[_], A, B] = B            Pointed (Pure, Applicative)
// ConstUnit  Id   F[B]          S[F[_], G[_], A, B] = F[B]         CoPointed (CoApplicative)

object FPAbs {
  type Id[A] = A // identity functor - what is it in functor category
  type ConstUnit[A] = Unit // terminal object in functor category
  type Void[A] = Nothing // initial object in functor category
}

object FPMorphism {
  type Function[A, B]          = A => B
  type Kleisli[F[_],A,B]       = A => F[B]
  type CoKleisli[F[_],A,B]     = F[A] => B
  type Ap[F[_], A, B]          = F[A => B]

  type Op[A, B]                = B => A
  type OpAp[F[_], A, B]        = F[B => A]

  type Zip[F[_], A, B]         = F[(A, B)]
  type Alt[F[_], A, B]         = F[Either[A, B]]

  type Value[B]                = B
  type Effect[F[_],B]          = F[B]

  type OpKleisli[F[_],A,B]     = B => F[A]
  type OpCoKleisli[F[_],A,B]   = F[B] => A
}

// TODO does FlatMap and Apply recovers Monad laws?
// TODO does CoFlatMap and CoApply recovers Comonad laws?
// TODO how to recover Applicative laws from Apply and Zip?
// TODO how to recover Alternative laws from Apply and Alt?
// TODO does Contravariant and Divide recovers Divide (ContravariantSemigroupal) laws?

trait FPAbs[F[_], G[_]] {
  type Morphism[_, _] // specify some transformation on types A, B possibly using F and G e.g. A => F[B]

  type BiKleisli[A,B] = F[A] => G[B]
  def run[A, B](f: Morphism[A, B]): BiKleisli[A,B]
}

trait EndoFPAbs[F[_]] extends FPAbs[F,F]

trait EndoFPAbsLaw[F[_]] extends FPAbs[F,F] {
  // define category structure on a Morphism
  def id[A]: Morphism[A, A]
  def compose[A, B, C]: Morphism[A, B] => Morphism[B, C] => Morphism[A, C]

  // laws for different FP abstractions (Functor, Monad, Comonad, Applicative, Divide) differs
  // only in the category definition

  def abstractionIdentityLaw[A](fa: F[A]): Boolean =
    run[A,A](id[A])(fa) == fa

  def abstractionCompositionLaw[A,B,C](fa: F[A], f: Morphism[A,B], g: Morphism[B,C]): Boolean =
    run[B,C](g)( run[A,B](f)(fa) ) == run[A,C]( compose(f)(g) )(fa)
}

trait Functor[F[_]] extends EndoFPAbs[F] {
  type Morphism[AA, BB] = FPMorphism.Function[AA,BB]
  def map[A, B](fa: F[A])(f: A => B): F[B] = run[A, B](f)(fa)
}

trait FunctorLaws[F[_]] extends EndoFPAbsLaw[F] with Functor[F] {
  def id[A]: A => A = identity[A]
  def compose[A, B, C]: (A => B) => (B => C) => (A => C) =
    f => g => f andThen g
}

trait FlatMap[F[_]] extends EndoFPAbs[F] {
  type Morphism[AA, BB] = FPMorphism.Kleisli[F,AA,BB]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = run[A, B](f)(fa)
}

abstract class FlatMapLaws[F[_]: Pure] extends EndoFPAbsLaw[F] with FlatMap[F] {

  // FlatMap and Pointed are connected, give raise to Monad
  def id[A]: A => F[A] = implicitly[Pure[F]].pure[A]

  def compose[A,B,C]: (A => F[B]) => (B => F[C]) => (A => F[C]) =
    f => g => a => run[B,C](g)(f(a))
}

trait Zip[F[_]] extends EndoFPAbs[F] {
  type Morphism[AA, BB] = FPMorphism.Zip[F,AA,BB]
  def tuple2[A, B](fa: F[A])(fab: F[(A, B)]): F[B] = run[A, B](fab)(fa)
}

//abstract class ZipLaws[F[_] : Pure, A : Monoid] extends EndoFPAbsLaw[F] with Zip[F] {
//  def pure[X]: X => F[X] = implicitly[Pure[F]].pure
//  val empty: A = implicitly[Monoid[A]].empty
//
//  def id: F[(A,A)] = pure((empty,empty))
//  def compose[B,C]: F[(A,B)] => F[(B,C)] => F[(A,C)] = fab => fbc => {
//    val foo = run(fab)
//    ???
//  }
//}

trait Alt[F[_]] extends FPAbs[F, F] {
  type Morphism[AA, BB] = FPMorphism.Alt[F,AA,BB]
  def either2[A, B](fa: F[A])(fab: F[Either[A, B]]): F[B] = run[A, B](fab)(fa)
}

//abstract class AltLaws[F[_]] extends EndoFPAbsLaw[F] with Alt[F] {
//  def id[A]: F[Either[A,A]] = ???
//  def compose[A, B, C]: F[Either[A,B]] => F[Either[B,C]] => F[Either[A,C]] = ???
//}

trait Apply[F[_]] extends FPAbs[F, F] {
  type Morphism[AA, BB] = FPMorphism.Ap[F,AA,BB]
  def ap[A, B](fa: F[A])(f: F[A => B]): F[B] = run[A, B](f)(fa)
}

abstract class ApplyLaws[F[_] : Pure] extends EndoFPAbsLaw[F] with Apply[F] {
  def pure[A]: A => F[A] = implicitly[Pure[F]].pure
  def id[A]: F[A => A] = pure(identity[A])
  def compose[A, B, C]: F[A => B] => F[B => C] => F[A => C] = fab => {
    val fabbcac: F[(A => B) => ((B => C) => (A => C))] = pure(ab => bc => ab andThen bc)
    val fbcac: F[(B => C) => (A => C)] = run[A => B, (B => C) => (A => C)](fabbcac)(fab)
    run[B => C,A => C](fbcac)
  }
}

trait CoFlatMap[F[_]] extends FPAbs[F, F] {
  type Morphism[AA, BB] = FPMorphism.CoKleisli[F,AA,BB]
  def extend[A, B](fa: F[A])(f: F[A] => B): F[B] = run[A, B](f)(fa)
}

abstract class CoFlatMapLaws[F[_]:Extract] extends EndoFPAbsLaw[F] with CoFlatMap[F] {
  def id[A]: F[A] => A = implicitly[Extract[F]].extract
  def compose[A, B, C]: (F[A] => B) => (F[B] => C) => F[A] => C = fab => fbc => fa =>
    implicitly[Extract[F]].extract(run(fbc)(run(fab)(fa)))
}

trait Contravariant[F[_]] extends EndoFPAbs[F] {
  type Morphism[AA, BB] = FPMorphism.Op[AA,BB]

  def contramap[A, B](fa: F[A])(f: B => A): F[B] = run[A, B](f)(fa)
}

trait ContravariantLaws[F[_]] extends EndoFPAbsLaw[F] with Contravariant[F] {
  def id[A]: A => A = identity[A]
  def compose[A, B, C]: (B => A) => (C => B) => (C => A) = f => g => f compose g
}

//trait Divide[F[_]] extends EndoFPAbs[F] {
//  type Morphism[AA, BB] = FPMorphism.OpAp[F,AA,BB]
//  def contraAp[A, B](fa: F[A])(fba: F[B => A]): F[B] = run[A, B](fba)(fa)
//}
//
//abstract class DivideLaws[F[_] : Pure : Contravariant] extends EndoFPAbsLaw[F] with Divide[F] {
//  def pure[A]: A => F[A] = implicitly[Pure[F]].pure
//  def contramap[A,B](fa: F[A], ba: B => A): F[B] = implicitly[Contravariant[F]].contramap(fa)(ba)
//  def id[A]: F[A => A] = pure[A => A](identity[A])
//  override def compose[A, B, C]: F[B => A] => F[C => B] => F[C => A] = fba => fcb => {
//    val fcabacb: ((C => A) => (B => A)) => (C => B) = ???
//    val fcaba: F[(C => A) => (B => A)] = contramap[C => B, (C => A) => (B => A)](fcb, fcabacb)
//    run[B => A,C => A](fcaba)(fba)
//  }
//}

// Pointed and CoPointed break symmetry

trait Pure[F[_]] extends FPAbs[ConstUnit, F] {
  type Morphism[AA, BB] = FPMorphism.Value[BB]
  def pure[B](b: B): F[B] = run(b)(())

  def id[A:Monoid]: Morphism[A, A] = implicitly[Monoid[A]].empty
  def compose[A, B, C]: Morphism[A, B] => Morphism[B, C] => Morphism[A, C] = _ => identity[C]

  def abstractionIdentityLaw[A:Monoid](fa: F[A]): Boolean =
    run[A,A](id[A])(fa) == fa

  def abstractionCompositionLaw[A,B,C](fa: F[A], f: Morphism[A,B], g: Morphism[B,C]): Boolean =
    run[B,C](g)( run[A,B](f)(fa) ) == run[A,C]( compose(f)(g) )(fa)
}

trait Extract[F[_]] extends FPAbs[ConstUnit, Id] {
  type Morphism[AA, BB] = FPMorphism.Effect[F,AA]

  def extract[B](value: F[B]): B = run(value)(())

  def id[A]: Morphism[A, A] = ???
  def compose[A, B, C]: Morphism[A, B] => Morphism[B, C] => Morphism[A, C] = fab => _ => fab

  def abstractionIdentityLaw[A](fa: F[A]): Boolean =
    run[A,A](id[A])(fa) == fa // TODO

  def abstractionCompositionLaw[A,B,C](fa: F[A], f: Morphism[A,B], g: Morphism[B,C]): Boolean =
    run[B,C](g)( run[A,B](f)(fa) ) == run[A,C]( compose(f)(g) )(fa)
}

// There are not contravariant monads ?

trait ContraCoflatMap[F[_]] extends FPAbs[F, F] {
  type Morphism[AA, BB] = FPMorphism.OpKleisli[F,AA,BB]
  def contraExtend[A, B](fa: F[A])(f: B => F[A]): F[B] = run[A, B](f)(fa)
}

abstract class ContraCoflatMapLaws[F[_] : Pure : Extract] extends EndoFPAbsLaw[F] with ContraCoflatMap[F] {
  def id[A]: A => F[A] = a => implicitly[Pure[F]].pure(a)
  def compose[A, B, C]: (B => F[A]) => (C => F[B]) => C => F[A] = bfa => cfb => c =>
    bfa(implicitly[Extract[F]].extract(cfb(c)))
}

// There are not contravariant comonads ?

trait ContraFlatMap[F[_]] extends FPAbs[F, F] {
  type Morphism[AA, BB] = FPMorphism.OpCoKleisli[F,AA,BB]
  def contraExtend[A, B](fa: F[A])(f: F[B] => A): F[B] = run[A, B](f)(fa)
}

abstract class ContraFlatMapLaws[F[_] : Pure : Extract] extends EndoFPAbsLaw[F] with ContraFlatMap[F] {
  def id[A]: F[A] => A = implicitly[Extract[F]].extract
  def compose[A, B, C]: (F[B] => A) => (F[C] => B) => F[C] => A = fba => fcb => fc =>
    fba(implicitly[Pure[F]].pure(fcb(fc)))
}

// TODO you could expand on different shapes but they do not make sense ?

// ConstUnit  F    B             Pointed    Applicative
// ConstUnit  Id   F[B]          CoPointed  CoApplicative

// Id ConstUnit   F[B]
// Id ConstUnit   B
// ConstUnit Id   B

// F ConstUnit    B
// F ConstUnit    F[B]
// ConstUnit F    F[B]

// Id F  F[B]
// Id F  B
// F Id  F[B]
// F Id  B
