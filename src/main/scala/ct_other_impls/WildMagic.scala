package ct_other_impls

import ct_other_impls.Wizard.{ConstUnit, Id}

/*
Are there any constructions in Category Theory something like:

Given:
C category
F, G : functors in C
M functor category of category C

P morphism that map product of any two objects a and b from Category C into object a in M
 */

object Wizard {
  type Id[A] = A // identity functor - what is it in functor category
  type ConstUnit[A] = Unit // terminal object in functor category

  type Void[A] = Nothing // initial object in functor category
}

object WizardData {
  case class Reader[-A, +B](run: A => B) // Profunctor-ish?
  case class Op[+A, -B](run: B => A) // Profunctor-ish?
  case class Ap[+F[+_], -A, +B](run: F[A => B]) // Profunctor-ish?
  case class ContraAp[+F[-_], +A, -B](run: F[A => B]) // Profunctor-ish?
  case class ApOp[+F[+_], +A, -B](run: F[B => A]) // Profunctor-ish?
  case class Kleisli[+F[+_], -A, +B](run: A => F[B]) // Profunctor-ish?
  case class Kleisli2[+F[-_], -A, -B](run: A => F[B]) // Nifunctor-ish?
  case class ContraKleisli[-F[+_], -A, +B](run: F[A] => B) // Profunctor-ish?
  case class CoKleisli[-F[-_], +A, +B](run: F[A] => B) // Bifunctor-ish?
  case class Zip[+F[+_], +A, +B](run: F[(A, B)]) // Bifunctor-ish?
  case class Alt[+F[+_], +A, +B](run: F[Either[A, B]]) // Bifunctor-ish?
}

object WizardTypes {
  type Arrow[A, B]           = A => B          // = Function1[A,B]
  type Kleisli[F[_],A,B]     = A => F[B]       // = Function1[A,F[B]]
  type CoKleisli[F[_],A,B]   = F[A] => B       // = Function1[F[A],B]
  type Ap[F[_], A, B]        = F[A => B]       // = F[Function1[A,B]]

  type Op[A, B]              = B => A          // = Function1[B,A]
//  type OpKleisli[F[_],A,B]   = B => F[A]       // = Function1[B,F[A]]
//  type OpCoKleisli[F[_],A,B] = F[B] => A       // = Function1[F[B],A]
  type OpAp[F[_], A, B]      = F[B => A]       // = F[Function1[B,A]]

  type Zip[F[_], A, B]       = F[(A, B)]       // = F[Tuple2[A,B]]
  type Alt[F[_], A, B]       = F[Either[A, B]] // = F[Either[A,B]]

  type Pure[F[_], B]         = B
  type Effect[F[_],B]        = F[B]
}

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

trait Wizard[F[_], G[_]] {
  type Spell[_[_], _[_], _, _] // transform 2 type constructors and 2 regular types into sth
  def doMagic[A, B](f: Spell[F, G, A, B]): F[A] => G[B]
}

trait Functor[F[_]] extends Wizard[F, F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Arrow[AA,BB] // AA => BB

  // map(fa)(identity) == fa                       doMagic(identity)(fa)      == fa
  // map(map(fa)(f))(g) == map(fa)(f andThen g)    doMagic(g)(doMagic(f)(fa)) == doMagic(f andThen g)(fa)
  def map[A, B](fa: F[A])(f: A => B): F[B] = doMagic[A, B](f)(fa)
}

trait FlatMap[F[_]] extends Wizard[F, F] { // Monad
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Kleisli[FF,AA,BB] // AA => FF[BB]

  // flatMap(flatMap(fa)(f))(g) == flatMap(fa)(a => flatMap(f(a))(g))
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = doMagic[A, B](f)(fa)
}

trait Zip[F[_]] extends Wizard[F, F] { // Apply
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Zip[FF,AA,BB] // FF[(AA, BB)]
  def tuple2[A, B](fa: F[A])(fab: F[(A, B)]): F[B] = doMagic[A, B](fab)(fa)
}

trait Alt[F[_]] extends Wizard[F, F] { // Alt
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Alt[FF,AA,BB] // FF[Either[AA, BB]]
  def either2[A, B](fa: F[A])(fab: F[Either[A, B]]): F[B] = doMagic[A, B](fab)(fa)
}

trait Ap[F[_]] extends Wizard[F, F] { // Apply
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Ap[FF,AA,BB] // FF[AA => BB]
  def ap[A, B](fa: F[A])(f: F[A => B]): F[B] = doMagic[A, B](f)(fa)
}

trait CoFlatMap[F[_]] extends Wizard[F, F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.CoKleisli[FF,AA,BB] // FF[AA] => BB
  def extend[A, B](fa: F[A])(f: F[A] => B): F[B] = doMagic[A, B](f)(fa)
}

// TODO below 2 do not make any sense
//trait ContraCoflatMap[F[_]] extends Wizard[F, F] {
//  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.OpKleisli[FF,AA,BB] // BB => F[AA]
//  def contraExtend[A, B](fa: F[A])(f: B => F[A]): F[B] = doMagic[A, B](f)(fa)
//}
//trait OpCoKleisliWizard[F[_]] extends Wizard[F, F] {
//  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.OpCoKleisli[FF,AA,BB] // F[BB] => AA
//  def contraExtend[A, B](fa: F[A])(f: F[B] => A): F[B] = doMagic[A, B](f)(fa)
//}

trait Contravariant[F[_]] extends Wizard[F, F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Op[AA,BB] // BB => AA

  // doMagic(identity)(fa) == fa
  // contramap( contrampa(fa)(f) )(g) == contramap(fa)(f andThen g)
  def contramap[A, B](fa: F[A])(f: B => A): F[B] = doMagic[A, B](f)(fa)
}

trait Divide[F[_]] extends Wizard[F, F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.OpAp[FF,AA,BB] // FF[BB => AA]
  def contraAp[A, B](fa: F[A])(f: F[B => A]): F[B] = doMagic[A, B](f)(fa)
}

// TODO Pointed and CoPointed break symmetry

trait Pointed[F[_]] extends Wizard[ConstUnit, F] { // Applicative
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Pure[FF,BB] // BB
  def pure[B](value: B): F[B] = doMagic(value)(())
}

trait CoPointed[F[_]] extends Wizard[ConstUnit, Id] { // CoApplicative
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Effect[FF,BB]

  def coPure[B](value: F[B]): Id[B] = doMagic(value)(42)
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


// TODO
//trait Foldable[F[_]] {
//  def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B
//}

// TODO can we express duplicate and flatten somehow ?

// TODO what would happen with when combining different types of F like Id ConstUnit with F[A] => F[B]
// a lot of different ways to specify identity (if FF == F and GG == G)

// TODO Traversable, Distributive, MonadTrans do not fit this framework
// what it tells us about them ?

// TODO what about Selective
