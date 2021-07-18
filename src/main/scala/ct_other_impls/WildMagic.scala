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
  case class ContraKleisli[-F[+_], -A, +B](run: F[A] => B) // Profunctor-ish?

  case class Kleisli2[+F[-_], -A, -B](run: A => F[B]) // Nifunctor-ish?

  case class CoKleisli[-F[-_], +A, +B](run: F[A] => B) // Bifunctor-ish?
  case class Zip[+F[+_], +A, +B](run: F[(A, B)]) // Bifunctor-ish?
  case class Alt[+F[+_], +A, +B](run: F[Either[A, B]]) // Bifunctor-ish?
}

object WizardTypes {
  type Arrow[A, B]           = A => B
  type Kleisli[F[_],A,B]     = A => F[B]
  type CoKleisli[F[_],A,B]   = F[A] => B
  type Ap[F[_], A, B]        = F[A => B]

  type Op[A, B]              = B => A
//  type OpKleisli[F[_],A,B]   = B => F[A]     there are no contravariant monads
//  type OpCoKleisli[F[_],A,B] = F[B] => A     there are no contravariant comonads
  type OpAp[F[_], A, B]      = F[B => A]

  type Zip[F[_], A, B]       = F[(A, B)]
  type Alt[F[_], A, B]       = F[Either[A, B]]

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
  type Spell[_[_], _[_], _, _] // type level function that transforms 2 type constructors and 2 regular types into type
  def doMagic[A, B](f: Spell[F, G, A, B]): F[A] => G[B] // function that for every A and B
}

trait Wizard2[F[_]] extends Wizard[F,F] {
  type Cat[AA, BB] = Spell[F,F,AA,BB] // type level function that transforms type constructor and 2 regular types into type
  override def doMagic[A, B](f: Cat[A,B]): F[A] => F[B] // function that for every A and B
}

trait Category[Cat[_,_]] {
  def id[A]: Cat[A, A]
  def compose[A, B, C]: Cat[A, B] => Cat[B, C] => Cat[A, C]
}

trait Wizard2Law[F[_]] extends Wizard2[F] {
  val cat: Category[Cat]

  def lawId[A](fa: F[A]): Boolean =
    doMagic[A,A](cat.id[A])(fa) == fa

  def lawComp[A,B,C](fa: F[A], f: Cat[A,B], g: Cat[B,C]): Boolean =
    doMagic[B,C](g)( doMagic[A,B](f)(fa) ) == doMagic[A,C]( cat.compose(f)(g) )(fa)
}

trait Functor[F[_]] extends Wizard2[F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Arrow[AA,BB] // AA => BB
  def map[A, B](fa: F[A])(f: A => B): F[B] = doMagic[A, B](f)(fa)
}

object FunctorCat extends Category[Function1] {
  def id[A]: A => A = identity[A]
  def compose[A, B, C]: (A => B) => (B => C) => (A => C) =
    f => g => f andThen g
}

trait FunctorLaws[F[_]] extends Wizard2Law[F] with Functor[F] {
  override val cat: Category[Cat] = FunctorCat
}

trait FlatMap[F[_]] extends Wizard2[F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Kleisli[FF,AA,BB] // AA => FF[BB]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = doMagic[A, B](f)(fa)
}

trait FlatMapLaws[F[_]] extends Wizard2Law[F] with FlatMap[F] {

  // TODO this is pure, looks like specifying law for FlatMap requires to insist Pointed as subclass
  def id[A]: A => F[A] = ???

  def compose[A,B,C]: (A => F[B]) => (B => F[C]) => (A => F[C]) =
    f => g => a => doMagic[B,C](g)(f(a))
}

trait Zip[F[_]] extends Wizard2[F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Zip[FF,AA,BB] // FF[(AA, BB)]
  def tuple2[A, B](fa: F[A])(fab: F[(A, B)]): F[B] = doMagic[A, B](fab)(fa)
}

trait ZipLaws[F[_]] extends Wizard2[F] with Zip[F] {

  // TODO
  def idSpell[A]: F[(A,A)] = ???

  def composeSpell[A,B,C]: Spell[F,F,A,B] => Spell[F,F,B,C] => Spell[F,F,A,C] =
    (f : F[(A,B)]) =>
      (g : F[(B,C)]) => ???
}

trait Alt[F[_]] extends Wizard[F, F] { // Alt
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Alt[FF,AA,BB] // FF[Either[AA, BB]]
  def either2[A, B](fa: F[A])(fab: F[Either[A, B]]): F[B] = doMagic[A, B](fab)(fa)

  def lawId[A](fa: F[A]): Boolean = {
    val lhs1: Spell[F,F,A,A] = ??? // F[Either[A,A]]
    val lhs2: F[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1: F[B] = doMagic[A,B](f)(fa)
    val lhs2: F[C] = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = ??? // F[Either[A,B]] F[Either[B,C]] =>  F[Either[A,C]]
    val rhs: F[C] = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
}

trait Ap[F[_]] extends Wizard[F, F] { // Apply
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Ap[FF,AA,BB] // FF[AA => BB]
  def ap[A, B](fa: F[A])(f: F[A => B]): F[B] = doMagic[A, B](f)(fa)

  def lawId[A](fa: F[A]): Boolean = {
    val lhs1: Spell[F,F,A,A] = ??? // F[A => A]
    val lhs2: F[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1: F[B] = doMagic[A,B](f)(fa)
    val lhs2: F[C] = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = ??? // F[A => B]  F[B => C] => F[A => C]
    val rhs: F[C] = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
}

trait CoFlatMap[F[_]] extends Wizard[F, F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.CoKleisli[FF,AA,BB] // FF[AA] => BB
  def extend[A, B](fa: F[A])(f: F[A] => B): F[B] = doMagic[A, B](f)(fa)

  def lawId[A](fa: F[A]): Boolean = {
    val lhs1: Spell[F,F,A,A] = ??? // F[A] => A
    val lhs2: F[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1: F[B] = doMagic[A,B](f)(fa)
    val lhs2: F[C] = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = ??? // F[A] => B  F[B] => C   F[A] => C
    val rhs: F[C] = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
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

  def lawId[A](fa: F[A]): Boolean = {
    val lhs1: Spell[F,F,A,A] = identity[A]
    val lhs2: F[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1: F[B] = doMagic[A,B](f)(fa)
    val lhs2: F[C] = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = f compose g
    val rhs: F[C] = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
}

trait Divide[F[_]] extends Wizard[F, F] {
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.OpAp[FF,AA,BB] // FF[BB => AA]
  def contraAp[A, B](fa: F[A])(f: F[B => A]): F[B] = doMagic[A, B](f)(fa)

  def lawId[A](fa: F[A]): Boolean = {
    val lhs1: Spell[F,F,A,A] = ??? // F[A => A]
    val lhs2: F[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1: F[B] = doMagic[A,B](f)(fa)
    val lhs2: F[C] = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = ??? // F[B => A]   F[C => B] =>  F[C => A]
    val rhs: F[C] = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
}

// TODO Pointed and CoPointed break symmetry

trait Pointed[F[_]] extends Wizard[ConstUnit, F] { // Applicative
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Pure[FF,BB] // BB
  def pure[B](value: B): F[B] = doMagic(value)(())

  def lawId[A](fa: F[A]): Boolean = {
    val lhs1: Spell[F,F,A,A] = ??? // A
    val lhs2: F[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1: F[B] = doMagic[A,B](f)(fa)
    val lhs2: F[C] = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = g // f: B  g: C =>  C
    val rhs: F[C] = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
}

trait CoPointed[F[_]] extends Wizard[ConstUnit, Id] { // CoApplicative
  type Spell[FF[_], GG[_], AA, BB] = WizardTypes.Effect[FF,BB] // FF[BB]

  def coPure[B](value: F[B]): Id[B] = doMagic(value)(42)

  def lawId[A](fa: Id[A]): Boolean = {
    val lhs1: Id[A] = fa
    val lhs2: Id[A] = doMagic[A,A](lhs1)(fa)
    lhs2 == fa
  }

  def lawComp[A,B,C](fa: F[A], f: Spell[F,F,A,B], g: Spell[F,F,B,C]): Boolean = {
    val lhs1 = doMagic[A,B](f)(fa)
    val lhs2 = doMagic[B,C](g)(lhs1)
    val fg: Spell[F,F,A,C] = g // f: F[B] g: F[C] => F[C]
    val rhs = doMagic[A,C](fg)(fa)
    lhs2 == rhs
  }
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
