[![Build Status](https://travis-ci.org/lemastero/scala_typeclassopedia.svg?branch=master)](https://travis-ci.org/lemastero/scala_typeclassopedia)

# Scala typeclassopedia

[Abstract Algebra](./AbstractAlgebra.MD)

[Category Theory](#category-theory)

* Covariant Functors
  * [Functor](#functor-covariant-functor)
  * [Apply](#apply)
  * [Applicative](#applicative-applicative-functor)
  * [Selective](#selective-selective-applicative-functors)

* [Monad](#monad)
  * [Reader](#reader)
  * [Writer](#writer)
  * [State](#state)
  * [RWS Monad](#rws-monad)
  * [Update Monad](#update-monad)
  * [Logic Monad, Prompt Monad, Failure Monad](#logic-monad-prompt-monad-failure-monad)
  * [Type-Indexed Monads](#type-indexed-monads)
  * [ContT (Continuation Monad)](#contt-continuation-monad)
  * [Reverse State Monad](#reverse-state-monad)
  * [Tardis (Bidirectional State Monad)](#tardis-bidirectional-state-monad)
  * [Chronicle Monad](#chronicle-monad)
  * [Bimonad](#bimonad)
  * [Dijkstra monad](#dijkstra-monad)
  * [Hoare Monad](#hoare-monad)

* [IO related monads](#io-related-monads)
  * [IO](#io-monad)
  * [Bifunctor IO (BIO)](#bifunctor-io-bio)
  * [RIO Monad (Reader + IO)](#rio-monad-reader--io)
  * [TRIO (RIO Monad + Bifunctor IO)](#trio-rio-monad--bifunctor-io)

* [Commutative](#commutative)
  * [CommutativeArrow](#commutativearrow)

* Contravariant functors
  * [Contravariant](#contravariant-contravariant-functor)
  * [Divide (Contravariant Apply)](#divide-contravariant-apply)
  * [Divisible (Contravariant Applicative)](#divisible-contravariant-applicative)

* Contravariant Adjuctions & Representable
  * [Contravariant Adjunction](#contravariant-adjunction)
  * [Contravariant Rep](#contravariant-rep)

* [Contravariant Kan Extensions](#contravariant-kan-extensions)
  * [Contravariant Yoneda](#contravariant-yoneda)
  * [Contravariant Coyoneda](#contravariant-coyoneda)
  * [Contravariant Day](#contravariant-day)
  * [Invariant Day](#invariant-day)

* Invariant Functors
  * [Invariant (Invariant Functor, Exponential Functor)](#invariant-invariant-functor-exponential-functor)
  * [Invariant Day](#invariant-day)

* [Natural transformation (FunctionK)](#natural-transformation-functionk)

* Bifunctors
  * [Bifunctor](#bifunctor)
  * [Bifunctor Join](#bifunctor-join)
  * [Bifunctor Wrap](#bifunctor-wrap)
  * [Bifunctor Flip](#bifunctor-flip)
  * [Bifunctor Joker](#bifunctor-joker)
  * [Bifunctor Clown](#bifunctor-clown)
  * [Bifunctor Product](#bifunctor-product)
  * [Bifunctor Sum](#bifunctor-sum)
  * [Bifunctor Tannen](#bifunctor-tannen)
  * [Bifunctor Biff](#bifunctor-biff)
  * [Bitraverse](#bitraverse)
  * [Bifoldable](#bifoldable)

* Comonads
  * [Comonad](#comonad)
    * [Coreader (Env comonad, Product comonad)](#coreader-env-comonad-product-comonad)
    * [Cowriter](#cowriter)
    * [Cofree](#cofree)
    * [Cokleisli](#cokleisli)
  * [Bimonad](#bimonad)

* Traversing Folding Filtering
  * [Monoid](./AbstractAlgebra.MD#monoid)
  * [Foldable](#foldable)
  * [Traverse](#traverse)
  * [Bitraverse](#bitraverse)
  * [Bifoldable](#bifoldable)
  * [FunctorFilter](#functorfilter)
  * [TraverseFilter](#traversefilter)
  * [Distributive](#distributive)

* Monads not compose - solutions
  * [Monad Transformers](#monad-transformers-optiont-eithert-readert)
  * [Free Monads](#free-monads)
  * Tagless Final
  * [Extensible effects](#extensible-effects)

* [Free constructions](#free-constructions)
  * [Free Applicative](#free-applicative)
  * [Free Monads](#free-monads)
  * [Cofree](#cofree)
  * [Free Alternative](#free-alternative)
  * [Free Arrow](#free-arrow)
  * [Free Monad transformers](#free-monad-transformers)
 
* [Representable & Adjunctions](#representable--adjunctions)
  * [Representable](#representable)
  * [Corepresentable](#corepresentable)
  * [Adjunction](#adjunction)
  * [Adjoint Triples](#adjoint-triples)

* [(Co)Yoneda & (Co)Density & Kan Extensions](#coyoneda--codensity--kan-extensions)
  * [Yoneda](#yoneda)
  * [Coyoneda](#coyoneda)
  * [Right Kan extension](#right-kan-extension)
  * [Left Kan Extension](#left-kan-extension)
  * [Density Comonad](#density-comonad)
  * [Codensity](#codensity)
  * [Day Convolution](#day-convolution)

* Profunctors
  * [Profunctor](#profunctor)
  * [Star](#star)
  * [CoStar](#costar)
  * [Strong Profunctor](#strong-profunctor)
  * [Tambara](#tambara)
  * [Choice Profunctor](#choice-profunctor)
  * [Extranatural Transformation](#extranatural-transformation)
  * [Profunctor Functor](#profunctor-functor)
  * [Profunctor Monad](#profunctor-monad)
  * [Profunctor Comonad](#profunctor-comonad)
  * [Procompose](#procompose)
  * [ProductProfunctor](#roductprofunctor)
  * [SumProfunctor](#sumprofunctor)

* Profunctor Adjuctions & Representable
  * [Profunctor Adjunction](#profunctor-adjunction)
  * [Profunctor Rep](#profunctor-rep)

* Profunctor Kan Extensions
  * [Profunctor Yoneda](#profunctor-yoneda)
  * [Profunctor CoYoneda](#profunctor-coyoneda)
  * [Profunctor Ran](#profunctor-ran)
  * [Profunctor Codensity](#profunctor-codensity)

* [Arrows](#arrows)
  * [Category](#category)
  * [Arrow](#arrow)
  * [CommutativeArrow](#commutativearrow)
  * [Arrow Choice](#arrow-choice)
  * [Arrow Apply, Arrow Monad](#arrow-apply-arrow-monad)
  * [Arrow Loop](#arrow-loop)
  * [Arrow Zero](#arrow-zero)
  * [Free Arrow](#free-arrow)
  * [Kleisli](#kleisli)
  * [Cokleisli](#cokleisli)
  * [BiArrow](#biarrow)
  * [BiKleisli](#bikleisli)

* [Cayley representations](#cayley-representations)
  * [Difference Lists](#difference-lists)
  * [Codensity](#codensity)
  * [Double Cayley Representation](#double-cayley-representation)

* [ADT (Algebra of types)](#adt-algebra-of-types)
  * [Unit](#unit)
  * [Void](#void)
  * [Product](#product)
  * [Sum (Coproduct)](#sum-coproduct)
  * [These](#These)

* Higher kinded & exotic abstractions
  * [Monoidal Category, Monoid Object](#monoidal-categories-monoid-object)
  * [Cartesian Closed Category](#cartesian-closed-category)
  * [Day Convolution](#day-convolution)
  * [Functor Functor (FFunctor)](#functor-functor-ffunctor)
  * [Monad morphisms](#monad-morphisms)
  * [higher kinded category theory](#higher-kinded-category-theory)
  * [SemigroupK (Plus)](#semigroupk-plus)
  * [MonoidK (PlusEmpty)](#monoidk-plusempty)
  * [Dinatural Transformation](#dinatural-transformation)
  * [Ends & Coends](#ends--coends)
  * [Align](#align)
  * [Task](#andrey-mokhov-task)
  * [Transducers](#transducers)
  * [Relative monads](#relative-monads)

* Limits
  * [Cone](#cone)
  * [Cocone](#cocone)
  * [Diagonal Functor](#diagonal-functor)
  * [Limit](#limit)
  * [Colimit](#colimit)
  * [Ends & Coends](#ends--coends)

* Topoi
  * [Topos](#topos)

* [Other Encodings of Category Theory](./OtherEncodingsOfCT.MD)
  * [data-category by Sjoerd Visscher](./OtherEncodingsOfCT.MD#encoding-of-category-theory-by-sjoerd-visscher)
  * [Formalizations of Category Theory in proof assistants (Coq)](./OtherEncodingsOfCT.MD#formalizations-of-category-theory-in-proof-assistants)

* [Recursion schemas](RecursionSchemas.MD)
  * [Unfoldable](#unfoldable)

* [Optics](./Optics.MD)

* [Resource About Category Theory](#resource-about-category-theory)
  * [Mathematical resources about Category Theory](ComputationalTrinitarianism.MD)

* [Functor Oriented Programming](#functor-oriented-programming)

* [Functional data structures](./FunctionalDataStructures.MD)

## Category Theory

![](img/covariant_contravariant_functors.svg)

### Functor (Covariant Functor)

Type constructor `F[_]` that requires single type with function `map` - ability to transform its content, without changing the structure.

You can think of Functor using following intuitions:
- containers (Id, List, Vector, Tree, Option) can apply given function to every element in the collection
- computational context - `map` applies function to a value inside this effect, without changing the effect
    - Id - no effects
    - Const - always return the same value (ignore changes)
    - Option - may not have value,
    - List/Vector - may have multiple values,
    - Either/ValidatedNel/Try - may contain value or error(s) 
    - Reader - require some context to be computed
    - Writer - while computing value, generate some description
    - State, IO - computing changes a state
    - Monix Task/Future/Twitter Future/Scalaz Task - needs time to be computed
    - Map - is indexed with other type (see Controversies about Map)

```scala
trait Functor[F[_]] {
  def map[A,B](a: F[A])(f: A => B): F[B]
}
```

* Functor Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Functor.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/functor.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Functor.scala), [Idris](https://github.com/idris-lang/Idris-dev/blob/master/libs/prelude/Prelude/Functor.idr) [Purescript](https://github.com/purescript/purescript-prelude/blob/master/src/Data/Functor.purs) [Haskell base](http://hackage.haskell.org/package/base/docs/Data-Functor.html), [Haskell data-category](http://hackage.haskell.org/package/data-category/docs/Data-Category-Functor.html#g:2), [nLab](https://ncatlab.org/nlab/show/functor), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Core/Functors.v), [HoTT](https://github.com/HoTT/HoTT/blob/master/theories/Categories/Functor/Core.v), [CubicalTT](https://github.com/mortberg/cubicaltt/blob/master/examples/category.ctt), [Java Mojang/DataFixerUpper](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/kinds/Functor.java)

* Functor Laws ([Cats](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/FunctorLaws.scala), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Functor.scala#L98-L107) [Haskell](http://hackage.haskell.org/package/base/docs/Data-Functor.html#t:Functor)):
  1. identify: `xs.map(identity) == xs`
  2. composition: `xs.map(f).map(g) == xs.map(x => g(f(x))`

![](img/functor_laws.svg)

If Functor satisfies first law, then it also satisfies second: [(Haskell) The second Functor law is redundant - David Luposchainsky](https://github.com/quchen/articles/blob/master/second_functor_law.md)
if we don't include `bottom values` - [(Haskell) contrexample using undefined](https://stackoverflow.com/questions/8305949/haskell-functor-implied-law/8323243#8323243).

* In Category Theory, functor is a mapping of:
   * objects (`F[_]` maps types e.g. `Int` to `List[Int]`) and
   * morphisms (if `f` is mapping between `A` and `B` then we can think of `map(f)` as mapping between `F[A]` and `F[B]`) that
   * preserves composition and identity - this is ensured by the Functor Laws

But in general, functor maps two arbitrary categories. Functor, that maps the same category to itself, is called `endo functor`.
So strictly speaking, Functor in programming is `Endofunctor` in Category theory.

* Derived methods of Functor:

```scala
def lift[A, B](f: A => B): F[A] => F[B] // lift regular function to function inside container
def fproduct[A, B](fa: F[A])(f: A => B): F[(A, B)] // zip elements with result after applying f
def as[A, B](fa: F[A], b: B): F[B] // replace every element with b
def void[A](fa: F[A]): F[Unit] // clear preserving structure
def tupleLeft[A, B](fa: F[A], b: B): F[(B, A)]
def tupleRight[A, B](fa: F[A], b: B): F[(A, B)]
def widen[A, B >: A](fa: F[A]): F[B]
```

* Functors can compose: Cats [ComposedFunctor](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Composed.scala#L20-L26) [compose](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Functor.scala#L147-L151), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Functor.scala#L61-L66)

* An examples for [instances for built in types](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/InstancesForBuiltInTypes.scala),
[function1](https://www.youtube.com/watch?v=Dsd4pc99FSY&t=1075),
and [custom Tree type](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/TreeFunctor.scala).
An examples for [usage of map, derived methods, compose](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/functor/FunctorExamplesSpec.scala).

Functor definition does not require any conditions on type constructor `F` or any other operations (unlike Applicative, Monad).
Therefore pretty much everything is a Functor. Notable exceptions are:
  1. function input arguments (they are in `negative position`) or any input like type - see [Contravariant](#contravariant-contravariant-functor) We can define Functor only for return type - because type is in `positive position`.
  2. abstractions where type can be both input and output, see [Invariant](#invariant-invariant-functor-exponential-functor) and blog post [Rotten Bananas by Edward Kmett](http://comonad.com/reader/2008/rotten-bananas/)
  3. abstractions that behave like a Functor but not there are some controversies:

* Controversies:
  1. Set: [Twitter discussion](https://twitter.com/MikeMKH/status/1073950622301503488) with explanation done by Mark Seemann [Set is not a functor](http://blog.ploeh.dk/2018/12/03/set-is-not-a-functor/). [Comments in alleycats](https://github.com/typelevel/cats/blob/master/alleycats-core/src/main/scala/alleycats/std/set.scala). [PR in Scalaz explaining why Set is not a Functor but is a Foldable](https://github.com/scalaz/scalaz/pull/276)
  2. Map: [Cats Issue #1831](https://github.com/typelevel/cats/issues/1831)
  3. Try: [Comments in alleycats](https://github.com/typelevel/cats/blob/master/alleycats-core/src/main/scala/alleycats/std/try.scala)

* Many abstractions have enough structure, so we can define `map` that obeys the laws. Such as `Monad` defined using `pure` and `flatMap`.
Another notable example is `Coyoneda` that wraps any type constructor and allows to use `map` on it. Functor instance is neccessary only when we want to extract the end result.
See [Free constructions](#free-constructions) for `Free functors`. 

* Resources:
  * herding cats - Functor - @eed3si9n [(blog post)](http://eed3si9n.com/herding-cats/Functor.html)
  * FSiS 1, Type Constructors, Functors, and Kind Projector - Michael Pilquist [(video)](https://www.youtube.com/watch?v=Dsd4pc99FSY)
  * [Cats docs](https://typelevel.org/cats/typeclasses/functor.html)
  * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/FunctorUsage.scala)
  * (Haskell) The Extended Functor Family - George Wilson [(video)](https://www.youtube.com/watch?v=JUVMiRRq6wU)
  * Functional Patterns in C++, 1. Functors - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=ph7qt0pkPkc)
  * Understanding Data.Functor.Constant constructor and applicative laws
 - [(SO)](https://stackoverflow.com/questions/21169943/understanding-data-functor-constant-constructor-and-applicative-laws)

### Apply

Apply is a Functor with superpower to apply function already inside container to container of arguments.

```scala
trait Apply[F[_]] extends Functor[F] {
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}
```

* Apply Implementations: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Apply.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/apply.scala) [Java Mojang/DataFixerUpper](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/functions/Apply.java)

* Apply Laws ([Cats](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ApplyLaws.scala), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala#L203-L208)):
  1. functor laws
  2. `ap` composition

```scala
def apComposition[A, B, C](fa: F[A], fab: F[A => B], fbc: F[B => C]): Boolean = {

  //        ap F[A => B]              ap F[B => C]
  // F[A] ==================> F[B] =================> F[C]
  val fb: F[B] = ap(fab)(fa)
  val left: F[C] = ap(fbc)(fb)

  val expand: (B => C) => ((A => B) => (A => C)) =
    (bc: B => C) =>
      (ab: A => B) =>
        bc compose ab

  //               map( A=>B => B=>C => A=>C )
  // F[B => C] ======================================> F[A=>B => A=>C]
  val fabac: F[(A => B) => (A => C)] = map(fbc)(expand)

  //              ap F[A=>B => A=>C]
  // F[A => B] ==============================> F[A => C]
  val fac: F[A => C] = ap(fabac)(fab)

  //           ap F[A=>C]
  // F[A] =========================> F[C]
  val right: F[C] = ap(fac)(fa)

  left == right
}
```

* Derived methods (there are version defined from `xyz2` until `xyz22`)

```scala
def apply2[A, B, Z]   (fa: F[A], fb: F[B])          (ff: F[(A,B) => Z]): F[Z]
def apply3[A, B, C, Z](fa: F[A], fb: F[B], fc: F[C])(ff: F[(A,B,C) => Z]): F[Z]
// ...

def map2[A , B, Z]  (fa: F[A], fb: F[B])          (f: (A, B) => Z):    F[Z]
def map3[A, B, C, Z](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => Z): F[Z]
// ...

def tuple2[A, B]   (fa: F[A], fb: F[B]):           F[(A, B)]
def tuple3[A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)]
// ...

def product[A,B](fa: F[A], fb: F[B]): F[(A, B)]
def flip[A, B](ff: F[A => B]): F[A] => F[B]
```

* Apply was extracted from Applicative definition and usually is defined as a weaker version of Applicative that cannot put value inside effect F.
So, instances for Apply are the same as for Applicative.

* Apply can compose: [Cats compose](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Apply.scala#L210-L214) [ComposedApply](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Composed.scala#L28-L37), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala#L52-L57))

* Resources:
  * herding cats - Apply - @eed3si9n [(blog post)](http://eed3si9n.com/herding-cats/Apply.html)
  * [Cats docs](https://typelevel.org/cats/typeclasses/applicative.html#apply---a-weakened-applicative)
  * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/ApplyUsage.scala)
  * John DeGoes explains why `ap` is useful from Haskell perspective, but in Scala better would be to use `product` (named `zip`) [(reddit)](https://www.reddit.com/r/scala/comments/afor0h/scalaz_8_timeline/eeac71m/) 

### Applicative (Applicative Functor)

Applicative Functor is a Functor that can:
- apply function already inside container to container of arguments (so it is Apply)
- put value into container (lift into effect)

```scala
trait Applicative[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A] // point
}
```

* Applicative Implementations: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Applicative.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Applicative.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/applicative.scala) [Idris](https://github.com/idris-lang/Idris-dev/blob/master/libs/prelude/Prelude/Applicative.idr) [Java](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/kinds/Applicative.java)

* Applicative Laws ([Cats](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ApplicativeLaws.scala), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Applicative.scala#L118-L134)):

1 identify: `xs.ap(pure(identity)) == xs` apply identify function lifted inside effect does nothing

```scala
def apIdentityLaw[A](fa: F[A]): Boolean = {
  val l1: F[A => A] = pure(identity[A])
  val l2: F[A] = ap(l1)(fa)

  //         ap F[identity]
  //  F[A] ==================> F[A]
  l2 == fa
}
```

2 homomorphism: `pure(a).ap(pure(f)) == pure(f(a))` lifting value a and applying lifted function f is the same as apply function to this value and then lift result

```scala
def homomorphismLaw[A, B](ab: A => B, a: A): Boolean = {
  val fab: F[A => B] = pure(ab)
  val fa: F[A] = pure(a)

  //         F[A => B]
  // F[A] =============> F[B]
  val l: F[B] = ap(fab)(fa)

  val r: F[B] = pure(ab(a))

  l == r
}
```

3 interchange: `pure(a).ap(ff) == ff.ap(pure(f => f(a)))` where `ff: F[A => B]`

```scala
def interchangeLaw[A, B](fab: F[A => B], a: A): Boolean = {
  //       ap F[A => B]
  // F[A] ==============> F[B]
  val l: F[B] = ap(fab)(pure(a))

  val fabb: F[(A => B) => B] = pure((f: A => B) => f(a))

  //              ap F[(A => B) => B]
  // F[A => B] ========================> F[B]
  val r: F[B] = ap(fabb)(fab)

  l == r
}
```

4 map: `fa.map(f) == fa.ap(pure(f))`

```scala
def mapLikeDerivedLaw[A, B](f: A => B, fa: F[A]): Boolean = {
  val l: F[B] = map(fa)(f)
  val r: F[B] = ap(pure(f))(fa)
  l == r
}
```

* Derived methods - see Apply

* Applicatives can be composed (Cats [compose](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Applicative.scala#L86-L90) [ComposedApplicative](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Composed.scala#L39-L45), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Applicative.scala#L93-L98))

* Minimal set of methods to implement Applicative (other methods can be derived from them):
  * map2, pure
  * apply, pure

* Resources:
  * herding cats - Applicative: [(blog post)](http://eed3si9n.com/herding-cats/Applicative.html)
  * FSiS 2 - Applicative type class - Michael Pilquist: [(video)](https://www.youtube.com/watch?v=tD_EyIKqqCk)
  * FSiS 3 - Monad type class - Michael Pilquist: [(video)](https://www.youtube.com/watch?v=VWCtLhH815M)
  * Cats: [docs](https://typelevel.org/cats/typeclasses/applicative.html) 
  * Applicative programming with effects - Conor McBride, Ross Paterson [(shorter)](http://strictlypositive.org/IdiomLite.pdf) [longer](http://strictlypositive.org/Idiom.pdf)
  * The Essence of the Iterator Pattern - Jeremy Gibbons, Bruno C. d. S. Oliveira: [(paper)](https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf)
  * The Essence of the Iterator Pattern - Eric Torreborre [(blog post)](http://etorreborre.blogspot.com/2011/06/essence-of-iterator-pattern.html)
  * Static analysis with Applicatives - Gergő Érdi [(blog post)](https://gergo.erdi.hu/blog/2012-12-01-static_analysis_with_applicatives/)
  * Lifting - Tony Morris [(blog post)](http://blog.tmorris.net/posts/lifting/index.html)
  * (Haskell) Abstracting with Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2012/abstracting-with-applicatives/)
  * (Haskell) Algebras of Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2013/algebras-of-applicatives/)
  * Functional Patterns in C++, 2. Currying, Applicative - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=Hx9jojeBj6w)

### Selective (Selective applicative functors)

"Extend the Applicative type class with a single method
that makes it possible to be selective about effects."

"handle is a selective function application:
you apply a handler function of type A => B when given a value of type Left(a),
but can skip the handler (along with its effects) in the case of Right(b)."

Andrey Mokhov

```scala
trait Selective[F[_]] extends Applicative[F] {
  def handle[A, B](fab: F[Either[A, B]], ff: F[A => B]): F[B]
  def select[A, B, C](fab: F[Either[A, B]], fac: F[A => C], fbc: F[B => C]): F[C]
}
```

* Implementations: [Haskell](http://hackage.haskell.org/package/selective/docs/Control-Selective.html) [cb372/cats-selective](https://github.com/cb372/cats-selective/blob/master/core/src/main/scala/cats/Selective.scala)

* Resources:
  * (Haskell) Selective applicative functors - Andrey Mokhov [(blog post)](https://blogs.ncl.ac.uk/andreymokhov/selective/), [(video)](https://www.youtube.com/watch?v=7vruj4gj38Q)
  * (Haskell) Selective Applicative Functors Declare Your Effects Statically, Select Which to Execute Dynamically - Andrey Mokhov, Georgy Lukyanov, Simon Marlow, Jeremie Dimino [(paper)](https://www.staff.ncl.ac.uk/andrey.mokhov/selective-functors.pdf)
  * (OCaml) [snowleopard/selective-ocaml](https://github.com/snowleopard/selective-ocaml)
  * (Coq) [tuura/selective-theory-coq](https://github.com/tuura/selective-theory-coq)
  * (Haskell) Haskell Cafe: Applicative functors with branch/choice? [(mail archive)](https://mail.haskell.org/pipermail/haskell-cafe/2012-July/102518.html)
  * [copumpkin/SelectiveSigma.hs](https://gist.github.com/copumpkin/d5bdbc7afda54ff04049b6bdbcffb67e#file-selectivesigma-hs-L38-L39)

### Monad

We add to Apply ability `flatMap` that can join two computations
and use the output from previous computations to decide what computations to run next.

```scala
trait Monad[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}
```

* Monad Laws ([Cats](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Monad.scala#L76-L85), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Monad.scala#L76-L85), [Haskell Wiki](https://wiki.haskell.org/Monad_laws)):
  1. flatmap associativity: `fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(b => g(b))`
  2. left identity: `pure(a).flatMap(f) == f(a)`
  3. right identity: `fa.flatMap(a => pure(a)) == fa`

```scala
def flatMapAssociativity[A,B,C](fa: M[A], f: A => M[B], g: B => M[C]): Boolean = {
  //         flatMap(f)
  //  M[A] =============> M[B]
  val l1: M[B] = flatMap(fa)(f)
  //         flatMap(g)
  //  M[B] =============> M[C]
  val l2: M[C] = flatMap(l1)(g)

  val r1: A => M[C] = a => flatMap(f(a))(b => g(b))
  //         flatMap(f flatMap g)
  //  M[A] ======================> M[C]
  val r2: M[C] = flatMap(fa)(r1)
  l2 == r2
}

def leftIdentity[A,B,C](a: A, f: A => M[B], g: B => M[C]): Boolean = {
  //    pure
  // A =======> M[A]
  val l1: M[A] = pure(a)
  //        flatMap
  // M[A] ==========> M[B]
  val l2: M[B] = flatMap(l1)(f)

  // A =======> M[B]
  val r: M[B] = f(a)
  l2 == r
}

def rightIdentity[A,B,C](a: A, fa: M[A], g: B => M[C]): Boolean = {
  //        flatMap
  // M[A] ==========> M[A]
  val l: M[A] = flatMap(fa)(pure)
  
  val r: M[A] = fa
  l == r
}
```


* Minimal set of methods to implement Monad (others can be derived using them):
  * pure, flatMap
  * pure, flatten, map
  * pure, flatten, apply
  * pure, flatten, map2

* Derived methods:
```scala
def flatten[A](ffa: F[F[A]]): F[A]
def sequence[G[_], A](as: G[F[A]])(implicit G: Traverse[G]): F[G[A]]
def traverse[A, G[_], B](value: G[A])(f: A => F[B])(implicit G: Traverse[G]): F[G[B]]
def replicateA[A](n: Int, fa: F[A]): F[List[A]]
def unit: F[Unit] // put under effect ()
def factor[A, B](ma: F[A], mb: F[B]): F[(A, B)]
```

* Monads do not compose [Tony Morris blog post](http://blog.tmorris.net/posts/monads-do-not-compose/index.html).
You can use Monad Transformer that know what monad is inside (OptionT, EitherT, ListT) or Free Monads or Eff Monad.

* Monads can't be filtered. You can use Moand Filter for that.

* Example (translated from John Huges mind blowing workshop: Monads and all that) [instance for custom Tree](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/monad/TreeMonad.scala) 
and [usage of flatMap to implement functions zip and number](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/helper_implementations/Tree.scala) 
(using [State Int](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/monad/IntState.scala)).

* Implementations:  
  FlatMap/Bind: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CoflatMap.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bind.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/bind.scala)  
  Monad: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Monad.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Monad.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/monad.scala) [Idris](https://github.com/idris-lang/Idris-dev/blob/master/libs/prelude/Prelude/Monad.idr) [Purescript](https://pursuit.purescript.org/packages/purescript-prelude/docs/Control.Monad), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Monads/Monads.v), [nLab](https://ncatlab.org/nlab/show/monad)

* Resources
  * FSiS 3 - Monad type class - Michael Pilquist [(vido 14:44)](https://www.youtube.com/watch?v=VWCtLhH815M&t=884)
  * herding cats - Monad [blog post](http://eed3si9n.com/herding-cats/Monad.html)
  * [Cats docs](https://typelevel.org/cats/typeclasses/monad.html)
  * (Haskell) Monads for functional programming - Philip Wadler [(paper)](http://homepages.inf.ed.ac.uk/wadler/papers/marktoberdorf/baastad.pdf)
  * (Haskell) Monads are Trees with Grafting - A Neighborhood of Infinity - Dan Piponi [(paper)](https://github.com/dpiponi/grafting3/blob/master/monads.pdf)
  * More on Monoids and Monads - [(blog post)](https://apocalisp.wordpress.com/2010/07/21/more-on-monoids-and-monads/)
  * [wiki.haskell - Blow your mind - Monad magic](https://wiki.haskell.org/Blow_your_mind#Monad_magic)
  * https://www.quora.com/What-are-some-crazy-things-one-can-do-with-monads-in-Haskell
  * (Category Theory) Monads - TheCatsters [(video playlist)](https://www.youtube.com/watch?v=9fohXBj2UEI&list=PL0E91279846EC843E)
  * (Type Theory) The Partiality Monad Achim Jung Fest An Intersection of Neighbourhoods - Thorsten Altenkirch [(slides)](http://www.cs.nott.ac.uk/~psztxa/talks/achimfest.pdf) (partial evaluation modeled using Monads)
  * Tail Call Elimination in Scala Monads [(blog post)](https://apocalisp.wordpress.com/2011/10/26/tail-call-elimination-in-scala-monads/)
  * MonadMask vs MonadBracket - Michael Snoyman [blog post](https://www.fpcomplete.com/blog/2017/02/monadmask-vs-monadbracket)
  * Functional Patterns in C++, 3. Async API, Monoid, Monad - Bartosz Milewski [video](https://www.youtube.com/watch?v=ozN6XxsAF84)
  * (Haskell) Trivial Monad solutions - @geophf [(blog post 1)](http://logicaltypes.blogspot.com/2008/05/trivial-monad-solutions.html) [(blog post 2)](http://logicaltypes.blogspot.com/2008/05/trivial-monad-solutions-cont.html)
  * Monads in Java - @geophf [(blog post)](http://logicaltypes.blogspot.com/2011/09/monads-in-java.html)

### Reader

Wrapper around function from given type.
Input type can be seen as some configuration required to produce result.  

```scala
case class Reader[-In, +R](run: In => R) {
  def map[R2](f: R => R2): Reader[In, R2] =
    Reader(run andThen f)

  def flatMap[R2, In2 <: In](f: R => Reader[In2, R2]): Reader[In2, R2] =
    Reader(x => f(run(x)).run(x))
}
```

* Reader can be used to implement dependency injection.
* Monad instance can be defined for Reaer.

* Resources
  * The Reader Monad for Dependency Injection - Json Arhart [(video)](https://www.youtube.com/watch?v=xPlsVVaMoB0)
  * FSiS 9 - Reader, ReaderT, Id  - Michael Pilquist [(video)](https://www.youtube.com/watch?v=H3CCvXx4GvI)
  * https://gist.github.com/Mortimerp9/5384467

### Writer

* Resources
  * Monadic Logging and You - Martin Snyder [(video)](https://www.youtube.com/watch?v=t-YX55ZF4g0)
  * The Writer Monad using Scala (example) - Tony Morris: [blog post](http://blog.tmorris.net/posts/the-writer-monad-using-scala-example/index.html)

### State

Abstraction over stateful computation.

```scala
case class State[S,A](runState: S => (A,S))
```

is a monad:

```scala
def stateMonad[S]: Monad[State[S, ?]] = new Monad[State[S, ?]] {

  def pure[A](a: A): State[S, A] = State(s => (a, s))

  def flatMap[A, B](ma: State[S, A])(f: A => State[S, B]): State[S, B] = State[S, B](
    s => {
      val (a: A, s2: S) = ma.runState(s)
      f(a).runState(s2)
    }
  )
}
```

* Resources
  * Towards an Effect System in Scala, Part 1: ST Monad [(blog post)](https://apocalisp.wordpress.com/2011/03/20/towards-an-effect-system-in-scala-part-1/)
  * Scalaz State Monad - Michael Pilquist [(video)](https://www.youtube.com/watch?v=Jg3Uv_YWJqI)
  * Memoisation with State using Scala - Tony Morris [(blog post)](http://blog.tmorris.net/posts/memoisation-with-state-using-scala/index.html)
  * Monads to Machine Code - Stephen Diehl [(blog post)](http://www.stephendiehl.com/posts/monads_machine_code.html) explore JIT compilation and LLVM using IO Monad and State Monad
  * Immutability, Docker, and Haskell's ST type - Michael Snoyman (https://www.fpcomplete.com/blog/2017/02/immutability-docker-haskells-st-type)

### RWS Monad

* Resources
  * (Haskell) Life after monads - robinp [(blog post)](https://www.schoolofhaskell.com/user/robinp/life-after-monads)
  * (Haskell) Tying the knot, redux - Dan Rosen [(blog post)](http://mergeconflict.com/tying-the-knot-redux/) [(reddit)](https://www.reddit.com/r/haskell/comments/w0haf/tying_the_knot_using_the_rws_monad/)
  * (Haskell) [mtl/Control.Monad.RWS](http://hackage.haskell.org/package/mtl/docs/Control-Monad-RWS.html)

### Update Monad

* Resources
  * (Haskell) Update Monads: Variation On State Monads - Chris Penner [(blog post)](https://chrispenner.ca/posts/update-monad)

### Logic Monad, Prompt Monad, Failure Monad
* [Adventures in Three Monads - Edward Z. Yang](http://web.mit.edu/~ezyang/Public/threemonads.pdf)
* [LogicT - backtracking monad transformer with fair operations and pruning](http://okmij.org/ftp/Computation/monads.html#LogicT)

### Type-Indexed Monads

 * indexed RWS monad [iravid/irwst IRWS](https://github.com/iravid/irwst/blob/rws/irwst/src/main/scala/com/iravid/irwst/IRWS.scala)
 * [Monad Factory: Type-Indexed Monads, Mark Snyder, Perry Alexander](https://www.researchgate.net/publication/221335580_Monad_Factory_Type-Indexed_Monads)
 * [Indexed Monads - Kwang's Haskell Blog](https://kseo.github.io/posts/2017-01-12-indexed-monads.html)
 * [garyb/purescript-indexed-monad](https://github.com/garyb/purescript-indexed-monad)

### ContT (Continuation Monad)

* Applications:
  * [Is there a real-world applicability for the continuation monad outside of academic use?](https://stackoverflow.com/questions/41202721/is-there-a-real-world-applicability-for-the-continuation-monad-outside-of-academ)
  * [snoyberg/conduit](https://github.com/snoyberg/conduit/blob/master/resourcet/Control/Monad/Trans/Resource/Internal.hs#L104-L105)
  * byorgey/MonadRandom [Strict](https://github.com/byorgey/MonadRandom/blob/master/Control/Monad/Trans/Random/Strict.hs#L197-L198), [Lazy](https://github.com/byorgey/MonadRandom/blob/master/Control/Monad/Trans/Random/Lazy.hs#L196-L197)
  * [mrkkrp/megaparsec](https://github.com/mrkkrp/megaparsec/blob/master/Text/Megaparsec/Internal.hs#L237-L241)
  * [gitpan/Perl6-Pugs](https://github.com/gitpan/Perl6-Pugs/blob/master/src/Pugs/AST/Eval.hs#L27)
  * [snapframework/heist](https://github.com/snapframework/heist/blob/master/src/Heist/Internal/Types/HeistState.hs#L543-L544)
  * [simonmar/monad-par](https://github.com/simonmar/monad-par/blob/master/monad-par/Control/Monad/Par/Scheds/Direct.hs)
  * [mvoidex/hsdev](https://github.com/mvoidex/hsdev/blob/master/src/System/Win32/FileMapping/Memory.hs)
  * [paolino/reactivegas Server](https://github.com/paolino/reactivegas/blob/master/Lib/Server/Server.hs#L54), [Passo (1)](https://github.com/paolino/reactivegas/blob/master/Lib/Passo.hs#L67) [(2)](https://github.com/paolino/reactivegas/blob/master/Lib/Passo.hs#L100), [Interazione](https://github.com/paolino/reactivegas/blob/master/Lib/Interazione.hs#L31)
  * [motemen/jusk](https://github.com/motemen/jusk/blob/master/src/Main.hs#L27)
  * [aleino/thesis](https://bitbucket.org/aleino/thesis/src/2a6657136dc2525f8c2afcbf0e1fceb98b94892c/poga/Graphics/GraphicalGame.hs?at=master&fileviewer=file-view-default)
  * [orbitz/web_typed](https://github.com/orbitz/web_typed/blob/master/libs/pa_monad/cc.ml)
  * [exFalso/Sim](https://github.com/exFalso/Sim/blob/master/SimNode.hs#L189)
  * [chris-taylor/Haskeme](https://github.com/chris-taylor/Haskeme/blob/master/src/Language/Types.hs#L64)
  * [vpetro/heopl](https://bitbucket.org/vpetro/heopl/src/d717615d311e8d7d45594e1e6b1b20a5680a521d/continuation-examples.hs?at=default&fileviewer=file-view-default)
  * [Rabbit: A Compiler for Scheme/Chapter 8 D. Conversion to Continuation-Passing Style](https://en.wikisource.org/wiki/Rabbit:_A_Compiler_for_Scheme/Chapter_8)

* Resources
  * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/data/ContT.scala)
  * gist by xuwei-k (https://gist.github.com/xuwei-k/19c9bb8c3afd08175762957880c57500)
  * Continuation monad in Scala - Tony Morris [(blog post)](http://blog.tmorris.net/posts/continuation-monad-in-scala/index.html)
  * (Haskell) School of Haskell - The Mother of all Monads - Dan Piponi [(blog post)](https://www.schoolofhaskell.com/school/to-infinity-and-beyond/pick-of-the-week/the-mother-of-all-monads)
  * (Haskell) Haskell for all - The Continuation Monad - Gabriel Gonzalez [(blog post)](http://www.haskellforall.com/2012/12/the-continuation-monad.html)

### Reverse State Monad

* Resources
  * https://stackoverflow.com/questions/43712476/real-life-and-useful-examples-of-reverse-state-monad
  * The Curious Time-Traveling Reverse State Monad [(blog post)](https://tech-blog.capital-match.com/posts/5-the-reverse-state-monad.html)
  * https://kseo.github.io/posts/2017-01-21-writer-monad.html
  * https://pavkin.ru/reverse-state-monad-in-scala-is-it-possible/

### Tardis (Bidirectional State Monad)

* Resources
  * https://www.pusher.com/sessions/meetup/london-functional-programmers/interview-pro-tip-travel-through-time
  * https://rosettacode.org/wiki/Water_collected_between_towers
  * http://landcareweb.com/questions/33409/haskellde-ni-xiang-xing-cong-tardisdao-revstate
  * http://hackage.haskell.org/package/tardis/docs/Control-Monad-Tardis.html
  * https://kcsongor.github.io/time-travel-in-haskell-for-dummies/
  * https://www.reddit.com/r/haskell/comments/199po0/can_the_tardis_monad_be_used_in_an_efficient_way/
  * https://repl.it/@Ouroboros2/Haskell-Tardis-1
  * http://blog.sigfpe.com/2006/08/you-could-have-invented-monads-and.html

### Dijkstra monad

```scala
trait Dijkstra[S,A] {
  def wp[Omega](f: (S,A) => Omega): S => Omega
}
```

is a Monad:

```scala
implicit def dijakstraMonad[S]: Monad[Dijkstra[S, ?]] = new Monad[Dijkstra[S, ?]] {
  def pure[A](a: A): Dijkstra[S,A] = new Dijkstra[S,A] {
    def wp[Omega](f: (S, A) => Omega): S => Omega =
      s => f(s,a)
  }

  def flatMap[A,B](ma: Dijkstra[S,A])(f: A => Dijkstra[S,B]): Dijkstra[S,B] =
    new Dijkstra[S,B] {
      def wp[Omega](g: (S, B) => Omega): S => Omega =
        ma.wp{ case (s,a) => f(a).wp(g)(s) }
    }
}
```

can be created from State:

```scala
def fromState[S,A](h: State[S,A]): Dijkstra[S,A] =
  new Dijkstra[S,A] {
    def wp[Omega](f: (S, A) => Omega): S => Omega =
      s => {
        val (a,s2) = h.runState(s)
        f(s2,a)
      }
  }
```

and converted to State:

```scala
def fromDijkstra[S,A](k: Dijkstra[S,A]): State[S,A] = State(
  s => k.wp{ case(s2,a) => (a,s2) }(s)
)
```

* Resources
  * (Haskell) Dijkstra monads - James Cheney [(blgo post)](http://why-lambda.blogspot.com/2014/09/dijkstra-monads.html)
  * Dijkstra Monads in Monadic Computation - Bart Jacobs [(paper)](http://www.cs.ru.nl/B.Jacobs/PAPERS/Dijkstra-monad.pdf)
  * Dijkstra Monads for Free - Danel Ahman et. al. [(paper)](https://www.fstar-lang.org/papers/dm4free/)
  * Verifying Higher order Programs with the Dijkstra Monad - Nikhil Swamy, Juan Chen, Ben Livshits [(paper)](https://www.microsoft.com/en-us/research/publication/verifying-higher-order-programs-with-the-dijkstra-monad/)

### Hoare Monad

* Resources
  * (Coq) The Hoare State Monad - Wouter Swierstra [(paper)](http://www.staff.science.uu.nl/~swier004/publications/2009-tphols.pdf)

## IO related monads

### IO Monad

* Resources
  * The Making of an IO - Daniel Spiewak [(video)](https://www.youtube.com/watch?v=g_jP47HFpWA)
  * Towards an Effect System in Scala, Part 2: IO Monad - (https://apocalisp.wordpress.com/2011/12/19/towards-an-effect-system-in-scala-part-2-io-monad/)
  * An IO monad for cats - Daniel Spiewak [(blog post)](https://typelevel.org/blog/2017/05/02/io-monad-for-cats.html)
  * [typelevel/cats-effect](https://github.com/typelevel/cats-effect)
  * [Tutorial Monix Task 2.x](https://monix.io/docs/2x/eval/task.html)
  * There Can Be Only One...IO Monad - John A De Goes [(blog post)](http://degoes.net/articles/only-one-io)

### Bifunctor IO (BIO)

* Resources
  * Bifunctor IO: A Step Away from Dynamically-Typed Error Handling - John A De Goes [(blog post)](http://degoes.net/articles/bifunctor-io), [reddit](https://www.reddit.com/r/scala/comments/8ifwkl/bifunctor_io_a_step_away_from_dynamicallytyped/)
  * On Bifunctor IO and Java's Checked Exceptions - @alexelcu [(blog post)](https://alexn.org/blog/2018/05/06/bifunctor-io.html), [twitter](https://twitter.com/alexelcu/status/993154465518837760)
  * [LukaJCB/cats-bio](https://github.com/LukaJCB/cats-bio), [PR to move into cats-effect](https://github.com/typelevel/cats-effect/issues/189)
  * (Idris) [base/Control/IOExcept](https://github.com/idris-lang/Idris-dev/blob/master/libs/base/Control/IOExcept.idr)
  * Using ZIO with Tagless-Final - John A De Goes [(blog post)](http://degoes.net/articles/polymorphic-bifunctors)
  * scalaz/scalaz-zio IO [docs](https://scalaz.github.io/scalaz-zio/datatypes/io.html) [src](https://github.com/scalaz/scalaz-zio/blob/master/core/shared/src/main/scala/scalaz/zio/IO.scala)
  * (Haskell) Combining errors with Bifunctor - Daniel Díaz Carrete (https://medium.com/@danidiaz/combining-errors-with-bifunctor-e7a40970fda9)


## RIO Monad (Reader + IO)

* Resources
  * The RIO Monad - Michael Snoyman [(blog post)](https://www.fpcomplete.com/blog/2017/07/the-rio-monad), [snoyberg/rio](https://github.com/snoyberg/rio), [reddit](https://www.reddit.com/r/haskell/comments/6p6p4b/the_rio_monad/)
  * [http4s-tracer motivation](https://http4s-tracer.profunktor.dev/motivations.html)


### TRIO (RIO Monad + Bifunctor IO)

* Resources
  * [snoyberg/trio](https://github.com/snoyberg/trio)


### Contravariant (Contravariant Functor)

Type constructor that can be `contramap`, so map in opposite direction.

If we imagine Functor, as abstracting over some output,
then Contravariant abstract over input.

In Category Theory Contravariant Functor is a Functor from opposite category ([opposite category formalization in CubicalTT](https://github.com/mortberg/cubicaltt/blob/master/examples/opposite.ctt)).

```scala
trait Contravariant[F[_]] {
  def contramap[A, B](f: B => A): F[A] => F[B]
}
```

[Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Contravariant.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/contravariant.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Contravariant.scala), [Haskell](http://hackage.haskell.org/package/contravariant/docs/Data-Functor-Contravariant.html), [Purescript](https://pursuit.purescript.org/packages/purescript-contravariant/docs/Data.Functor.Contravariant), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Presheaf.v), [nLab](https://ncatlab.org/nlab/show/contravariant+functor)

* Contravariant laws ([Cats](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ContravariantLaws.scala), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Contravariant.scala#L59-L68), [Haskell](http://hackage.haskell.org/package/contravariant/docs/Data-Functor-Contravariant.html)):

![](img/contravariant_laws.svg)

1 contramap identity  

```scala
//         contramap(id)
// F[A]  ================> F[A]
contramap(fa)(identity[A]) == fa
```

2 contravariant composition

```scala
//        contramap f
// F[A] ==============> F[B]
val fb: F[B] = contramap(fa)(f)

//        contramap g
// F[B] ===============> F[C]
val l: F[C] = contramap(fb)(g)

//        contramap (g . f)
// F[A] =====================> F[B]
val r: F[C] = contramap(fa)(f compose g)

l == r
```

* Applications:
  * model function with fixed output type, like [Predicate - function `A => Boolean`](https://github.com/lemastero/scala_typeclassopedia/blob/master/src/main/scala/contravariant/InstancesForContravariantFunctor.scala#L61-L65) or [Show](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Show.scala#L51-L53). Edward Kmett used Contravariant functor hierarchy to [model sorting](https://github.com/ekmett/discrimination/search?q=contramap).
  * Encoder in [scodec](https://github.com/scodec/scodec/blob/series/1.11.x/shared/src/main/scala/scodec/Encoder.scala#L40-L47) has Contravariant instance: [scodec/scodec-cats](https://github.com/scodec/scodec-cats/blob/master/shared/src/main/scala/scodec/interop/cats/CatsInstances.scala#L121-L123)
  * [jberryman/simple-actors](https://github.com/jberryman/simple-actors/search?q=contramap&unscoped_q=contramap) model [Behavior of actor](https://github.com/jberryman/simple-actors/blob/master/Control/Concurrent/Actors.lhs#L336-L346)
  * [List of Haskell libraries using Contravariant functors](https://packdeps.haskellers.com/reverse/contravariant)

* Contravariant is not called Cofunctor (like Monad -> Comonad, Appy -> Coapply) because when we inverse arrows
in Functor definition, we just get Functor definition back (with A, B swapped). [More on this on SO](https://stackoverflow.com/questions/34732571/why-there-is-no-cofunctor-typeclass-in-haskell).
There is library that makes fun ot of this fact [acme-cofunctor](http://hackage.haskell.org/package/acme-cofunctor).

* Resources
  * (Haskell) 24 Days of Hackage: contravariant - Tom Ellis [(blog post)](https://ocharles.org.uk/blog/guest-posts/2013-12-21-24-days-of-hackage-contravariant.html) (all classic examples: Predicate, Op but alos Behaviors of actor, nice laws explanation)
  * (Haskell) Covariance and Contravariance - Michael Snoyman [blog post](https://www.fpcomplete.com/blog/2016/11/covariance-contravariance) (Show as contravariant, discuss Profunctors, bivariance of phantom types, good explanation of positive and negative positions)
  * (Haskell) I love profunctors. They're so easy. - Liyang HU [(blog post)](https://www.schoolofhaskell.com/school/to-infinity-and-beyond/pick-of-the-week/profunctors) (Const, Predicate, Op instances for Contravariant)
  * (Haskell) The Extended Functor Family - George Wilson [(video)](https://www.youtube.com/watch?v=JUVMiRRq6wU&t=450)
  * (Haskell) Contravariant Functors: The Other Side of the Coin - George Wilson [(video)](https://www.youtube.com/watch?v=IJ_bVVsQhvc)
  * [What is a contravariant functor? - SO](https://stackoverflow.com/questions/38034077/what-is-a-contravariant-functor)
  * [What's up with Contravariant? - r/haskell](https://www.reddit.com/r/haskell/comments/1vc0mp/whats_up_with_contravariant/)

### Divide (Contravariant Apply)

```scala
trait Divide[F[_]] extends Contravariant[F] {
  def divide[A,B,C](f: A => (B,C), fb: F[B], fc: F[C]): F[A]
}
```

* Implementations: [Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Divide.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/ContravariantSemigroupal.scala), [Why not in Haskell](https://github.com/ekmett/contravariant/issues/18), [Purescript](https://pursuit.purescript.org/packages/purescript-contravariant/docs/Data.Divide)

* Divide Laws ([Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Divide.scala#L45-L52), [Haskell](http://hackage.haskell.org/package/contravariant/docs/Data-Functor-Contravariant-Divisible.html#g:4)):
let `def delta[A]: A => (A, A) = a => (a, a)`  
divide composition `divide(divide(a1, a2)(delta), a3)(delta) == divide(a1, divide(a2, a3),(delta))(delta)`

```scala
def divideComposition[A](fa1: F[A], fa2: F[A], fa3: F[A]): Boolean = {
  //                divide(delta)
  //  F[A1], F[A2] ===============> F[A12]
  val fa12: F[A] = divide(delta[A], fa1, fa2)

  //                 divide(delta)
  // F[A12], F[A3] =================> F[A123]
  val l: F[A] = divide( delta[A], fa12, fa3)


  //                divide(delta)
  //  F[A2], F[A3] ===============> F[A23]
  val fa23: F[A] = divide(delta[A], fa2, fa3)

  //                  divide(delta)
  //  F[A1], F[A23] ===============> F[A123]
  val r: F[A] = divide( delta[A], fa1, fa23 )
  
  l == r
}
```

This is simplified version. Proper version is shown in Haskell.

* Derived methods:
```scala
def divide1[A1, Z]    (a1: F[A1])           (f: Z => A1): F[Z] // contramap
def divide2[A1, A2, Z](a1: F[A1], a2: F[A2])(f: Z => (A1, A2)): F[Z]
// ...
def tuple2[A1, A2]    (a1: F[A1], a2: F[A2]):            F[(A1, A2)]
def tuple3[A1, A2, A3](a1: F[A1], a2: F[A2], a3: F[A3]): F[(A1, A2, A3)]
// ...
def deriving2[A1, A2, Z](f: Z => (A1, A2))(implicit a1: F[A1], a2: F[A2]): F[Z]
def deriving3[A1, A2, A3, Z](f: Z => (A1, A2, A3))(implicit a1: F[A1], a2: F[A2], a3: F[A3]): F[Z]
// ...
```

* Resources
  * (Haskell) Contravariant Functors: The Other Side of the Coin - George Wilson [(video)](https://www.youtube.com/watch?v=IJ_bVVsQhvc)
  * (Haskell, Category Theory) Discrimination is Wrong: Improving Productivity - Edward Kmett [(video)](https://www.youtube.com/watch?v=cB8DapKQz-I) [slides pdf](http://yowconference.com.au/slides/yowlambdajam2015/Kmett-DiscriminationIsWrong.pdf)
  
### Divisible (Contravariant Applicative)

```scala
trait Divisible[F[_]] extends Divide[F] {
  def conquer[A]: F[A]
}
```

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Divisible.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/ContravariantMonoidal.scala) [Haskell](http://hackage.haskell.org/package/contravariant/docs/Data-Functor-Contravariant-Divisible.html), [Purescript](https://pursuit.purescript.org/packages/purescript-contravariant/docs/Data.Divisible)

* Laws ([Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Divisible.scala#L33-L39), [Haskell](http://hackage.haskell.org/package/contravariant/docs/Data-Functor-Contravariant-Divisible.html#g:4)): let `def delta[A]: A => (A, A) = a => (a, a)`
1. all Contravariant and Divide laws including composition `divide(divide(a1, a2)(delta), a3)(delta) == divide(a1, divide(a2, a3),(delta))(delta)`
(see Divide law)

2. right identity: `divide(fa, conquer)(delta) == fa`
```scala
def rightIdentity[A](fa: F[A]): Boolean = {
  //                 divide(delta)    
  // F[A], conquer ===============> F[A]
  val l: F[A] = divide(delta, fa, conquer[A])
  
  l == fa
}
```

3. left identity:  `divide(conquer, fa)(delta) == fa`
```scala
def leftIdentity[A](fa: F[A]): Boolean = {
  //                 divide(delta)    
  // conquer, F[A] ===============> F[A]
  val l: F[A] = divide(delta, conquer[A], fa)
  
  l == fa
}
```

* Instances: Predicate, Sorting, Serializable, Pritty Printing

* Resources
  * [Cats PR #2034](https://github.com/typelevel/cats/pull/2034) explaining design choices different that in Haskell, Scalaz
  * (Haskell) Contravariant Functors: The Other Side of the Coin - George Wilson [(video)](https://www.youtube.com/watch?v=IJ_bVVsQhvc)
  * (Haskell, Category Theory) Discrimination is Wrong: Improving Productivity - Edward Kmett [(video)](https://www.youtube.com/watch?v=cB8DapKQz-I) [slides pdf](http://yowconference.com.au/slides/yowlambdajam2015/Kmett-DiscriminationIsWrong.pdf)


### Bifunctor

Abstracts over type constructor with 2 "holes". Represents two independent functors:
```scala
trait Bifunctor[F[_, _]] {
  def bimap[A, B, C, D](fab: F[A, B])(f: A => C, g: B => D): F[C, D]
}
```
* Bifunctor Laws
1. identity `xs.bimap(identity, identity) == xs` bimap with two identify function does nothing
2. composition `xs.bimap(f, h).bimap(g,i) == xs.bimap(x => g(f(x), x => h(i(x))`  you can bimap using f and h and then bimap using g and i or bimap once using composition
Second law is automatically fulfilled if the first law holds.
* Alternatively can be specified by providing
```scala
def leftMap[A, B, C](fab: F[A, B])(f: A => C): F[C, B]
def rightMap[A, B, D](fab: F[A, B])(g: B => D): F[A, D]
```
In that case identity law must hold for both functions:
3. identity `xs.leftMap(identity) == xs` leftMap with identify function does nothing
4. identity `xs.rightMap(identity) == xs` rightMap with identify function does nothing
If leftMap and rightMap and bimap are specified then additional lwa must be fullfilled:
5. `xs.bimap(f, g) == xs.leftMap(f).rightMap(g)`
* Derived methods
```scala
def leftMap[A, B, C](fab: F[A, B])(f: A => C): F[C, B]
def rightMap[A, B, D](fab: F[A, B])(g: B => D): F[A, D]
def leftFunctor[X]: Functor[F[?, X]]
def rightFunctor[X]: Functor[F[X, ?]]
def umap[A, B](faa: F[A, A])(f: A => B): F[B, B]
def widen[A, B, C >: A, D >: B](fab: F[A, B]): F[C, D]
```

* Implementations: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Bifunctor.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bifunctor.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/bifunctor.scala) [Purescript](https://pursuit.purescript.org/packages/purescript-bifunctors/docs/Data.Bifunctor) [Idris](https://github.com/japesinator/Idris-Bifunctors/blob/master/src/Data/Bifunctor.idr)

* Instances [can be defined](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/bifunctor/InstancesForForBuildInTypes.scala) for: Tuple2, Either, Validated. For Function1 not - functions are contravariant for input type.

* Resources
  * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/BifunctorUsage.scala)
  * [Cats docs](https://typelevel.org/cats/typeclasses/bifunctor.html)
  * Funky Scala Bifunctor - Tony Morris [(blog post)](http://blog.tmorris.net/posts/funky-scala-bifunctor/index.html)
  * herding cats — Datatype-generic programming with Bifunctor [(blog post (understand Free monads first))](http://eed3si9n.com/herding-cats/datatype-generic-programming.html)
  * [Haskell libraries using Bifunctors](https://packdeps.haskellers.com/reverse/bifunctors)
  * (Haskell) The Extended Functor Family - George Wilson: [video](https://www.youtube.com/watch?v=JUVMiRRq6wU&t=303)
  * (Haskell) Parametricity for Bifunctor - Brent Yorgey [(blog post)](https://byorgey.wordpress.com/2018/03/30/parametricity-for-bifunctor/)

### Bifunctor Join

Turn a Bifunctor with both arguments with the same type into Functor.

```haskell
newtype Join p a = Join { runJoin :: p a a }

-- instance
-- Bifunctor p => Functor (Join p)
```

* Implementations [Haskell bifunctors/Data.Bifunctor.Join](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Join.html) [japesinator/Idris-Bifunctors Data/Bifunctor.Join](https://github.com/japesinator/Idris-Bifunctors/blob/master/src/Data/Bifunctor/Join.idr) [purescript-bifunctors/Data.Bifunctor.Join](https://pursuit.purescript.org/packages/purescript-bifunctors/docs/Data.Bifunctor.Join)

### Bifunctor Wrap

Functor over second argument of a Bifunctor

```haskell
newtype WrappedBifunctor p a b = WrapBifunctor { unwrapBifunctor :: p a b }

-- instance
-- Bifunctor p => Functor (WrappedBifunctor p a)
```

* Implementations [Haskell bifunctors/Data.Bifunctor.Wrapped](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Wrapped.html) [purescript-bifunctors/Data.Bifunctor.Wrap](https://pursuit.purescript.org/packages/purescript-bifunctors/docs/Data.Bifunctor.Wrap)

### Bifunctor Flip

Swap arguments of Bifunctor

```haskell
newtype Flip p a b = Flip { runFlip :: p b a }

-- instance
-- Bifunctor p => Bifunctor (Flip p)
```

* Implementations [Haskell bifunctors/Data.Bifunctor.Flip](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Flip.html) [purescript-bifunctors/Data.Bifunctor.Flip](https://pursuit.purescript.org/packages/purescript-bifunctors/docs/Data.Bifunctor.Flip)

### Bifunctor Joker

Functor over second argument of Bifunctor

```haskell
newtype Joker g a b = Joker { runJoker :: g b }

-- instance
-- Functor g => Bifunctor (Joker g :: * -> * -> *)
-- Functor g => Functor (Joker g a)
```

* Implementations [Haskell bifunctors/Data.Bifunctor.Joker](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Joker.html)

* Resources
  * Clowns to the Left of me, Jokers to the Right Dissecting Data Structures - Conor McBride [(paper)](http://strictlypositive.org/CJ.pdf)

### Bifunctor Clown

Functor over second argument of Bifunctor

```haskell
newtype Clown f a b = Clown { runClown :: f a }

-- instances
-- Functor (Clown f a :: * -> *)
-- Functor f => Bifunctor (Clown f :: * -> * -> *)
```

* Implementations [Haskell bifunctors/Data.Bifunctor.Clown](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Clown.html)

* Resources
  * Clowns to the Left of me, Jokers to the Right Dissecting Data Structures - Conor McBride [(paper)](http://strictlypositive.org/CJ.pdf)


### Bifunctor Product

Product of two Bifunctors

```haskell
data Product f g a b = Pair (f a b) (g a b)

-- instance
-- (Bifunctor f, Bifunctor g) => Bifunctor (Product f g)
```

* Implementations  [Haskell bifunctors/Data.Bifunctor.Product](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Product.html) [purescript-bifunctors/Data.Bifunctor.Product](https://pursuit.purescript.org/packages/purescript-bifunctors/docs/Data.Bifunctor.Product)

### Bifunctor Sum

Sum of two Bifunctors

```haskell
data Sum p q a b = L2 (p a b) | R2 (q a b)

-- instance
-- (Functor f, Bifunctor p) => Functor (Tannen f p a)
```

* Implementations  [Haskell bifunctors/Data.Bifunctor.Sum](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Sum.html)


### Bifunctor Tannen

Compose Functor on the outside.

```haskell
newtype Tannen f p a b = Tannen { runTannen :: f (p a b) }

-- instances
-- (Functor f, Bifunctor p) => Bifunctor (Tannen f p)
-- (Functor f, Bifunctor p) => Functor (Tannen f p a)
```

* Implementations [Haskell bifunctors/Data.Bifunctor.Tannen](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Tannen.html)

### Bifunctor Biff

Compose two Functors inside Bifunctor

```haskell
newtype Biff p f g a b = Biff { runBiff :: p (f a) (g b) }

-- instance
-- (Bifunctor p, Functor f, Functor g) => Bifunctor (Biff p f g)
```

* Implementations: [Haskell bifunctors/Data.Bifunctor.Biff](https://hackage.haskell.org/package/bifunctors/docs/Data-Bifunctor-Biff.html)

### Bitraverse

* Implementations: [Scalaz Bitraverse](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bitraverse.scala) [Cats Bitraverse](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Bitraverse.scala) [Purescript purescript-foldable-traversable/Data.Bitraversable](https://pursuit.purescript.org/packages/purescript-foldable-traversable/docs/Data.Bitraversable) [Haskell bifunctors/Data.Biapplicative](https://hackage.haskell.org/package/bifunctors/docs/Data-Biapplicative.html)

### Bifoldable

* Implementations: [(Scalaz Bifoldable)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bifoldable.scala) [Cats Bifoldable](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Bifoldable.scala) [Purescript purescript-foldable-traversable/Data.Bifoldable](https://pursuit.purescript.org/packages/purescript-foldable-traversable/docs/Data.Bifoldable)

### Invariant (Invariant Functor, Exponential Functor)

Functor that can create covariant functor (by passing identity as g) or contravariant functor (by passing identity to f).
It represent situation when given type constructor
contains type on both positive and negative position (like function A => A).

```scala
trait Invariant[F[_]] {
  def imap[A, B](fa: F[A])(f: A => B)(g: B => A): F[B]
}
```

* Implementations [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/InvariantFunctor.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/invariantfunctor.scala) [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Invariant.scala) [Haskell](https://hackage.haskell.org/package/category-extras/docs/Control-Functor-Exponential.html) 

* Resources
  * Explorations in Variance - Michael Pilquist [(video)](https://www.youtube.com/watch?v=VZWLRepyNvo)
  * Cats [docs](https://typelevel.org/cats/typeclasses/invariant.html)
  * Exponential on Functor level [tweeter Oleg Grenrus](https://twitter.com/phadej/status/1085342927591784451)
  * (Haskell) Rotten Bananas - Edward Kmett [(blog post)](http://comonad.com/reader/2008/rotten-bananas/) (There is nice example, beware: a lot of recursion schemas)
  * Category Theory 8.1: Function objects, exponentials [(video)](https://www.youtube.com/watch?v=REqRzMI26Nw)

### Distributive

Traverse are composable Distributive it require only Functor (and Traverse require Applicative)

```scala
trait Distributive[F[_]] extends Functor[F] {
  def collect[G[_]:Functor,A,B](fa: G[A])(f: A => F[B]): F[G[B]]
  def distribute[G[_]:Functor,A](fa: G[F[A]]): F[G[A]]
}
```

* Implementations: [Scalaz](https://github.com/scalaz/scalaz/blob/scalaz-seven/core/src/main/scala/scalaz/Distributive.scala) [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Distributive.scala) [Haskell distributive/Data.Distributive](http://hackage.haskell.org/package/distributive/docs/Data-Distributive.html) [Purescript purescript-distributive/Data.Distributive](https://pursuit.purescript.org/packages/purescript-distributive/docs/Data.Distributive)

* Resources
   * Scalaz issue about difference between Haskell/Purescript vs Scala impl [issue](https://github.com/scalaz/scalaz/issues/2034)
   * The essence of dataflow programming - 	Tarmo Uustalu, 	Varmo Vene [paper short][http://cs.ioc.ee/~tarmo/papers/aplas05.pdf] [long](http://cs.ioc.ee/~tarmo/papers/cefp05.pdf) [slides](http://www.cs.ox.ac.uk/ralf.hinze/WG2.8/22/slides/varmo.pdf)) (parsing using Monads, Distributive, Comonads) 
   * (Haskell) Moore for Less - Edward Kmett [(blog post)](https://www.schoolofhaskell.com/user/edwardk/moore/for-less)
   * (Haskell) Zippers Using Representable And Cofree - Chris Penner (https://chrispenner.ca/posts/representable-cofree-zippers)
   * (Haskell) A law for Distributive? [(reddit)](https://www.reddit.com/r/haskell/comments/4cmjeg/a_law_for_distributive/)
   * (Haskell) How to write a Representable instance using only Distributive properties? [(SO)](https://stackoverflow.com/questions/49497241/how-to-write-a-representable-instance-using-only-distributive-properties)
   * (Haskell) usage of Distributive in [old hanshoglund/music-suite](https://github.com/hanshoglund/music-suite/blob/648354f701ba6806e259a4b79b59bb5699249eea/sketch/old/TT.hs#L1742-L1743)
   * [Is there a concept of something like co-applicative functors sitting between comonads and functors?](https://cstheory.stackexchange.com/questions/16241/is-there-a-concept-of-something-like-co-applicative-functors-sitting-between-com)


### Comonad

Abstraction for type with one hole that allows:
- map over (extends Functor)
- get current value
- duplicate one layer of abstraction
It is dual to Monad (Monad allow to put value in and collapse one layer).

```scala
trait CoflatMap[F[_]] extends Functor[F] {
  def coflatMap[A, B](fa: F[A])(f: F[A] => B): F[B]
}

trait Comonad[C[_]] extends CoflatMap[C] {
  def extract[A](ca: C[A]): A // counit
  def duplicate[A](ca: C[A]): C[C[A]] // coflatten
  def extend[A, B](ca: C[A])(f: C[A] => B): C[B] = map(duplicate(ca))(f) // coflatMap, cobind
}
```
 
If we define extract and extend:
1. `fa.extend(_.extract) == fa`
2. `fa.extend(f).extract == f(fa)`
3. `fa.extend(f).extend(g) == fa.extend(a => g(a.extend(f)))`

If we define comonad using map, extract and duplicate:
3. `fa.duplicate.extract == fa`
4. `fa.duplicate.map(_.extract) == fa`
5. `fa.duplicate.duplicate == fa.duplicate.map(_.duplicate)`

And if we provide implementation for both duplicate and extend:
6. `fa.extend(f) == fa.duplicate.map(f)`
7. `fa.duplicate == fa.extend(identity)`
8. `fa.map(h) == fa.extend(faInner => h(faInner.extract))`

The definitions of laws in [Cats src Comonad](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ComonadLaws.scala)
, [Cats src Coflatmap](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CoflatMapLaws.scala)
and [Haskell Control.Comonad](https://hackage.haskell.org/package/comonad/docs/Control-Comonad.html).
 
* Derived methods:
```scala
 def extend[A, B](ca: C[A])(f: C[A] => B): C[B] = map(duplicate(ca))(f) // coFlatMap
```
Method extend can be use to chain oparations on comonads - this is called coKleisli composition.

* Implementations of comonad can be done for: None empty list, Rose tree, Identity

* Implementations:  
  CoflatMap/Cobind: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CoflatMap.scala)  [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Cobind.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/cobind.scala)    
  Comonad: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Comonad.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Comonad.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/comonad.scala) [Haskell](https://hackage.haskell.org/package/comonad/docs/Control-Comonad.html) [Purescript](https://pursuit.purescript.org/packages/purescript-control/docs/Control.Comonad)

* Resources
  * Scala Comonad Tutorial, Part 1 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/06/23/a-scala-comonad-tutorial/)
  * Scala Comonad Tutorial, Part 2 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/)
  * Streams for (Co)Free! - John DeGoes: [(video)](https://www.youtube.com/watch?v=R_nYc4FItcI)
  * Life Is A Comonad - Elias Jordan [(video)](https://www.youtube.com/watch?v=6eiS2QTQKPE) [(blog post)](https://eli-jordan.github.io/2018/02/16/life-is-a-comonad/) [(reddit)](https://www.reddit.com/r/scala/comments/7ydyjr/life_is_a_comonad/)
  * Conway's Game Of Life Using Representable And Comonads - Chris Penner [(blog post)](https://chrispenner.ca/posts/conways-game-of-life.html)
  * (Haskell) Getting a Quick Fix on Comonads - Kenneth Foner [(video)](https://www.youtube.com/watch?v=F7F-BzOB670)
  * [Haskell libraries using Comonads](https://packdeps.haskellers.com/reverse/comonad)
  * (Haskell) Monads from Comonads - Edward Kmett [(blog post)](http://comonad.com/reader/2011/monads-from-comonads/)
  * (Haskell) Monad Transformers from Comonads - Edward Kmett [(blog post)](http://comonad.com/reader/2011/monad-transformers-from-comonads/)
  * (Haskell) More on Comonads as Monad Transformers - Edward Kmett [(blog post)](http://comonad.com/reader/2011/more-on-comonads-as-monad-transformers/)
  * (Haskell) The Cofree Comonad and the Expression Problem - Edward Kmett [(blog post)](http://comonad.com/reader/2008/the-cofree-comonad-and-the-expression-problem/)
  * (Haskell) Comonads as Spaces - Phil Freeman [(blog post)](http://blog.functorial.com/posts/2016-08-07-Comonads-As-Spaces.html)
  * (Purescript) PS Unscripted - Comonads for UIs - Phil Freeman [(video)](https://www.youtube.com/watch?v=EoJ9xnzG76M)
  * (Haskell) Cofun with cofree comonads - Dave Laing [(slides, video, code)](http://dlaing.org/cofun/)
  * (Haskell) matchingSub is Comonadic (obviously!) - geophf [blog post](http://logicaltypes.blogspot.com/2014/06/matchingsub-is-comonadic-obviously.html)
  * (Haskell) Realized Constants are Comonadic - geophf [blog post](http://logicaltypes.blogspot.com/2009/06/realized-constants-are-comonadic.html)
  

### Coreader (Env comonad, Product comonad)

Wrap value of type A with some context R.

```scala
case class CoReader[R, A](extract: A, ask: R) {
  def map[B](f: A => B): CoReader[R, B] = CoReader(f(extract), ask)
  def duplicate: CoReader[R, CoReader[R, A]] = CoReader(this, ask)
}
```

* Resources
  * Scala Comonad Tutorial, Part 1 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/06/23/a-scala-comonad-tutorial/)
  * (Haskell) [(src Control-Comonad-Env)](https://hackage.haskell.org/package/comonad/docs/Control-Comonad-Env.html)
  * (Haskell) CoReader x CoState == Silver Bullet - @geophf [(blog post)](http://logicaltypes.blogspot.com/2012/11/coreader-x-costate-silver-bullet.html)

### Cowriter

It is like Writer monad, combines all logs (using Monid) when they are ready.

```scala
case class Cowriter[W, A](tell: W => A)(implicit m: Monoid[W]) {
  def extract: A = tell(m.empty)
  def duplicate: Cowriter[W, Cowriter[W, A]] = Cowriter( w1 =>
    Cowriter( w2 =>
      tell(m.append(w1, w2))
    )
  )
  def map[B](f: A => B) = Cowriter(tell andThen f)
}
```

* Resources
  * Scala Comonad Tutorial, Part 1 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/06/23/a-scala-comonad-tutorial/)

### Bimonad

Combine power of Monad and Comonad with additiona laws that tie together Monad and Comonad methods

```scala
trait Bimonad[T] extends Monad[T] with Comonad[T]
```

* They simplify resolution of implicits for things that are Monad and Comonad

Resources:
  * [Bimonads and Hopf monads on categories - Bachuki Mesablishvili, Robert Wisbauer](https://arxiv.org/pdf/0710.1163v3.pdf)
  * [PR with Bimonad to Cats](https://github.com/typelevel/cats/issues/30)

![](img/traversable_foldable.svg)

### Foldable

Given definition of foldLeft (eager, left to right0) and foldRight (lazi, right to left) provide additional way to fold Monoid.
```scala
trait Foldable[F[_]]  {
  def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B
  def foldRight[A, B](fa: F[A], z: => B)(f: (A, => B) => B): B
}
```
* Laws: no. You can define condition that foldLeft and foldRight must be consistent.
* Derived methods (are different for scalaz and Cats):
```scala
def foldMap[A, B](fa: F[A])(f: A => B)(implicit B: Monoid[B]): B
def foldM    [G[_], A, B](fa: F[A], z: B)(f: (B, A) => G[B])(implicit G: Monad[G]): G[B] // foldRightM
def foldLeftM[G[_], A, B](fa: F[A], z: B)(f: (B, A) => G[B])(implicit G: Monad[G]): G[B]
def find[A](fa: F[A])(f: A => Boolean): Option[A] // findLeft findRight
def forall[A](fa: F[A])(p: A => Boolean): Boolean // all
def exists[A](fa: F[A])(p: A => Boolean): Boolean // any
def isEmpty[A](fa: F[A]): Boolean // empty
def get[A](fa: F[A])(idx: Long): Option[A] // index
def size[A](fa: F[A]): Long // length
def toList[A](fa: F[A]): List[A]
def intercalate[A](fa: F[A], a: A)(implicit A: Monoid[A]): A
def existsM[G[_], A](fa: F[A])(p: A => G[Boolean])(implicit G: Monad[G]): G[Boolean] // anyM
def forallM[G[_], A](fa: F[A])(p: A => G[Boolean])(implicit G: Monad[G]): G[Boolean] // allM

// Cats specific
def filter_[A](fa: F[A])(p: A => Boolean): List[A]
def takeWhile_[A](fa: F[A])(p: A => Boolean): List[A]
def dropWhile_[A](fa: F[A])(p: A => Boolean): List[A]
def nonEmpty[A](fa: F[A]): Boolean
def foldMapM[G[_], A, B](fa: F[A])(f: A => G[B])(implicit G: Monad[G], B: Monoid[B]): G[B]
def traverse_[G[_], A, B](fa: F[A])(f: A => G[B])(implicit G: Applicative[G]): G[Unit]
def sequence_[G[_]: Applicative, A](fga: F[G[A]]): G[Unit]
def foldK[G[_], A](fga: F[G[A]])(implicit G: MonoidK[G]): G[A]

// scalaz specific
def filterLength[A](fa: F[A])(f: A => Boolean): Int
def maximum[A: Order](fa: F[A]): Option[A]
def maximumOf[A, B: Order](fa: F[A])(f: A => B): Option[B]
def minimum[A: Order](fa: F[A]): Option[A]
def minimumOf[A, B: Order](fa: F[A])(f: A => B): Option[B]
def splitWith[A](fa: F[A])(p: A => Boolean): List[NonEmptyList[A]]
def splitBy[A, B: Equal](fa: F[A])(f: A => B): IList[(B, NonEmptyList[A])]
def selectSplit[A](fa: F[A])(p: A => Boolean): List[NonEmptyList[A]]
def distinct[A](fa: F[A])(implicit A: Order[A]): IList[A]
```
* Can be composed

* Implementations: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Foldable.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Foldable.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/foldable.scala) [Idris](https://github.com/idris-lang/Idris-dev/blob/master/libs/prelude/Prelude/Foldable.idr) [Java DataFixerUpper](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/functions/Fold.java) [Java vavr](https://github.com/vavr-io/vavr/blob/master/vavr/src/main/java/io/vavr/collection/Foldable.java)

* Resources:
   * FSiS 4 - Semigroup, Monoid, and Foldable type classes - Michael Pilquist [video 55:38](https://www.youtube.com/watch?v=ueo_E2BxMnA&t=3337)
   * [Cats docs](https://typelevel.org/cats/typeclasses/foldable.html)
   * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/FoldableUsage.scala)
   * [scalaz Foldable1 src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Foldable1.scala) [example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/Foldable1Usage.scala)
   * [Purescript purescript-foldable-traversable/Data.FoldableWithIndex](https://pursuit.purescript.org/packages/purescript-foldable-traversable/docs/Data.FoldableWithIndex)
   * [Purescript purescript-foldable-traversable/Data.Semigroup.Foldable](https://pursuit.purescript.org/packages/purescript-foldable-traversable/docs/Data.Semigroup.Foldable)
   * A tutorial on the universality and expressiveness of fold - Graham Hutton [(paper)](http://www.cs.nott.ac.uk/~pszgmh/fold.pdf)
   * (Haskell) Conquering Folds - Edward Kmett [blog post](https://www.schoolofhaskell.com/user/edwardk/conquering-folds)
   * (Haskell) Monoidal Sorting - Chris Penner [(blog post)](https://chrispenner.ca/posts/monoid-sort) (`Monoids` and `foldMap` used for sorting)
   * Beautiful folds are practical, too - Gabriel Gonzalez [(video)](https://www.youtube.com/watch?v=6a5Ti0r8Q2s) [slides repo](https://github.com/Gabriel439/slides/blob/master/munihac/foldmap.md) [Hacker News](https://news.ycombinator.com/item?id=16478744)
   * Beautiful folds in Scala - Adam Warski [(blog post)](https://softwaremill.com/beautiful-folds-in-scala/)

### Traverse

Functor with method traverse and folding functions from Foldable.
```scala
trait Traverse[F[_]] extends Functor[F] with Foldable[F] {
  def traverse[G[_]: Applicative, A, B](fa: F[A])(f: A => G[B]): G[F[B]]
}
```
* Laws:
 [Cats Traverse laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/TraverseLaws.scala)
 (Haskell) [Typeclassopedia](https://wiki.haskell.org/Typeclassopedia#Laws_7)
* Derived methods
```scala
def sequence[G[_]:Applicative,A](fga: F[G[A]]): G[F[A]]
def zipWithIndex[A](fa: F[A]): F[(A, Int)] // indexed
// ... other helper functions are different for scalaz and cats
```

* Implementations: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Traverse.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Traverse.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/traversable.scala) [Idris](https://github.com/idris-lang/Idris-dev/blob/master/libs/prelude/Prelude/Traversable.idr) [Purescript](https://pursuit.purescript.org/packages/purescript-foldable-traversable/docs/Data.Traversable) [Java DataFixerUpper](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/kinds/Traversable.java) [Java vavr-io](https://github.com/vavr-io/vavr/blob/master/vavr/src/main/java/io/vavr/collection/Traversable.java)

* Resources
   * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/TraverseUsage.scala) 
   * [Cats docs](https://typelevel.org/cats/typeclasses/traverse.html) 
   * [PR for Cats](https://github.com/typelevel/cats/pull/2424)
   * [scalaz Traverse1](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Traverse1.scala)
   * [purescript-foldable-traversable/Data.TraversableWithIndex](https://pursuit.purescript.org/packages/purescript-foldable-traversable/docs/Data.TraversableWithIndex)
   * FSiS 5 - Parametricity and the Traverse type class - Michael Pilquist [(video)](https://www.youtube.com/watch?v=D0Fnzr15BAU) 
   * The Essence of the Iterator Pattern - Jeremy Gibbons, Bruno C. d. S. Oliveira: [(paper)](https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf)
   * An Investigation of the Laws of Traversals - Mauro Jaskelioff, Ondrej Rypacek [(paper)](https://www.fceia.unr.edu.ar/~mauro/pubs/TraverseLaws.pdf)
   
### SemigroupK (Plus)

Semigroup that abstracts over type constructor F. For any proper type A can produce Semigroup for F[A]. 

```scala
trait SemigroupK[F[_]] {
  def combineK[A](x: F[A], y: F[A]): F[A]  // plus
  def algebra[A]: Semigroup[F[A]] //  semigroup
}
```

* SemigroupK can compose

* Resources:
  * Scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Plus.scala)
  * Cats [docs](https://typelevel.org/cats/typeclasses/semigroupk.html) [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/SemigroupK.scala)
  * FSiS 6 - SemigroupK, MonoidK, MonadFilter, MonadCombine - Michael Pilquist [(video)](https://www.youtube.com/watch?v=f26aSrkFKa4)
  
### MonoidK (PlusEmpty)

Monoid that abstract over type constructor ```F```. For any proper type ```A``` can produce Monoid for ```F[A]```. 

```scala
trait MonoidK[F[_]] extends SemigroupK[F] {
  def empty[A]: F[A]
  override def algebra[A]: Monoid[F[A]] // monoid
}
```

* MonoidK can compose

* Resources:
  * Scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/PlusEmpty.scala)
  * Cats [docs](https://typelevel.org/cats/typeclasses/monoidk.html) [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/MonoidK.scala)
  * FSiS 6 - SemigroupK, MonoidK, MonadFilter, MonadCombine - Michael Pilquist [(video 21:15)](https://www.youtube.com/watch?v=f26aSrkFKa4&t=1275)

### FunctorFilter

* Implementations [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/FunctorFilter.scala)

* Resources
  * Finding all permutations of list: [(blog post haskell)](https://byorgey.wordpress.com/2007/06/26/deducing-code-from-types-filterm/) [(translation to Scala using Cats)](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/mtl/TraverseEmptyListPermutationsSpec.scala)

### TraverseFilter

* Implementations
  * [Cats TraverseFilter](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/TraverseFilter.scala) [Haskell witherable/Data.Witherable](https://hackage.haskell.org/package/witherable/docs/Data-Witherable.html)
 
## Monads not compose - solutions
 
* Resources
  * [Documentation.Layers.Overview](https://hackage.haskell.org/package/layers-0.1/docs/Documentation-Layers-Overview.html) (nice overwiev of alternative approaches in Haskell)

### Monad Transformers (OptionT EitherT ReaderT)

"Monad transformers just aren’t practical in Scala."
John A De Goes

* Resources
  * No More Transformers: High-Performance Effects in Scalaz 8 - John A De Goes [(blog post)](http://degoes.net/articles/effects-without-transformers)
  * (Haskell) [Typeclassopedia Monad transformers](https://wiki.haskell.org/Typeclassopedia#Monad_transformers)
  * FSiS 7 - OptionT transformer - Michael Pilquist [(video)](https://www.youtube.com/watch?v=ZNUTMabdgzo)
  * FSiS 8 - EitherT transformer - Michael Pilquist [(video)](https://www.youtube.com/watch?v=z7rCBQ_vTRg)
  * (Haskell) The ReaderT Design Pattern - Michael Snoyman [(blog post)](https://www.fpcomplete.com/blog/2017/06/readert-design-pattern)
  * (Haskell) Announcing: monad-unlift - Michael Snoyman [(blog post)](https://www.fpcomplete.com/blog/2015/04/announcing-monad-unlift)
  * (Haskell) Scanner-parsers II: State Monad Transformers - geophf [(blog post)](http://logicaltypes.blogspot.com/2008/08/scanner-parsers-ii-state-monad.html)

### Extensible effects

* Resources
  * Eff monad for cats atnos-org/eff [github](https://github.com/atnos-org/eff)
  * b-studios/scala-effekt [github](https://github.com/b-studios/scala-effekt)
  * extensible-effects [hackage](https://hackage.haskell.org/package/extensible-effects)
  * [Extensible Effects: an alternative to Monad Transformers - Oleg Kiselyov](http://okmij.org/ftp/Haskell/extensible/)
  * [Idris Effect](https://www.idris-lang.org/documentation/effects/)
  * [bibliography of work related to the theory and practice of computational effects](https://github.com/yallop/effects-bibliography)

## Natural transformation (FunctionK)

Represent mappings between two functors.

```scala
trait NaturalTransf[F[_], G[_]] {
  def apply[A](fa: F[A]): G[A]
}
```

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/NaturalTransformation.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/FunctionK.scala), [Haskell](http://hackage.haskell.org/package/natural-transformation/docs/Control-Natural.html), [nLab](https://ncatlab.org/nlab/show/natural+transformation), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Core/NaturalTransformations.v), [CubicalTT](https://github.com/mortberg/cubicaltt/blob/master/examples/univprop.ctt)

* Resources
  * Cats [docs](https://typelevel.org/cats/datatypes/functionk.html)
  * [tpolecat/doobie KleisliInterpreter](https://github.com/tpolecat/doobie/blob/master/modules/free/src/main/scala/doobie/free/kleisliinterpreter.scala) uses natural transformations
  * Natural transformations - TheCatsters [(video playlist)](https://www.youtube.com/watch?v=FZSUwqWjHCU&list=PLA28A5C9D19465C92)

## Free constructions

| abstraction         | free construction     |
| ------------------- | --------------------- |
| Monoid              | List, Vector | 
| Functor             | [Yoneda](#yoneda), [Coyoneda](#coyoneda), [Density](#density-comonad), [Codensity](#codensity), [Right Kan Extension](#right-kan-extension), [Left Kan Extension](#left-kan-extension), [Day Convolution](#day-convolution) |
| Applicative         | [FreeApplicative](#free-applicative) |
| Alternative         | [Free Alternative](#free-alternative)
| Monad               | [Free Monads](#free-monads), [Codensity](#codensity), [Right Kan Extension](#right-kan-extension) |
| Comonad             | [CoFree](#cofree), [Density](#density-comonad) |
| Profunctor          | [Profunctor CoYoneda](#profunctor-coyoneda), [Profunctor Yoneda](#profunctor-yoneda), [Tambara](#tambara), Pastro, Cotambara, Copastro, TambaraSum, PastroSum, CotambaraSum, CopastroSum, Closure, Environment, CofreeTraversing, FreeTraversing, Traversing |
| ProfunctorFunctor   | [Profunctor CoYoneda](#profunctor-coyoneda), [Profunctor Yoneda](#profunctor-yoneda), [Tambara](#tambara), Pastro, Cotambara, Copastro, TambaraSum, PastroSum, CotambaraSum, CopastroSum, Closure, Environment, CofreeTraversing, FreeTraversing |
| ProfunctorMonad     |          Pastro,            Copastro,             PastroSum,               CopastroSum,          Environment,                   FreeTraversing |
| ProfunctorComonad   | [Tambara](#tambara),         Cotambara,           TambaraSum,           CotambaraSum,               Closure,              CofreeTraversing |
| Strong              | [Tambara](#tambara), Pastro,                                                                       Traversing |
| Costrong            |                  Cotambara, Copastro |
| Choice              |                                       TambaraSum, PastroSum |
| Cochoice            |                                                             CotambaraSum, CopastroSum, Traversing |
| Closed              | Closure, Environment |
| Traversing          | CofreeTraversing, FreeTraversing |
| Arrow               | [Free Arrow](#free-arrow) |

* [Usage of Free construction in Haskell](https://packdeps.haskellers.com/reverse/free)

### Free Applicative

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/FreeAp.scala) [Haskell](http://hackage.haskell.org/package/free/docs/Control-Applicative-Free.html)

* Resources
  * [Cats docs](https://typelevel.org/cats/datatypes/freeapplicative.html)
  * Free Applicative Functors - Paolo Capriotti, Ambrus Kaposi [(paper)](https://arxiv.org/abs/1403.0749)
  * Move Over Free Monads: Make Way for Free Applicatives! - John deGoes [(video)](https://www.youtube.com/watch?v=H28QqxO7Ihc)
  * Flavours of free applicative functors - Roman Cheplyaka [(blog post)](https://ro-che.info/articles/2013-03-31-flavours-of-free-applicative-functors)

### Free Monads

ADT (sometimes implemented using Fix point data type) that form a Monad, without any other conditions:

```scala
sealed trait Free[F[_],A]
case class Return[F[_],A](a: A) extends Free[F,A]
case class Suspend[F[_],A](s: F[Free[F,A]]) extends Free[F,A]
```

* Resources
  * [Cats docs](https://typelevel.org/cats/datatypes/freemonad.html)
  * Why the free Monad isn’t free - Kelley Robinson: https://www.youtube.com/watch?v=wvNgoeZza2g
  * Beyond Free Monads - John DeGoes: https://www.youtube.com/watch?v=A-lmrvsUi2Y
  * Free as in Monads - Daniel Spiewak: https://www.youtube.com/watch?v=aKUQUIHRGec
  * Free Monoids and Free Monads - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/08/20/free-monads-and-free-monoids/)
  * (Haskell) Free Monoids in Haskell - Dan Doel [(blog post)](http://comonad.com/reader/2015/free-monoids-in-haskell/)
  * (Haskell) Many Roads to Free Monads - Dan Doel [(blog post)](https://www.schoolofhaskell.com/user/dolio/many-roads-to-free-monads)
  * (Haskell) Meta-programming with the Free Monad - John Wiegley [(blog post)](http://newartisans.com/2012/08/meta-programming-with-the-free-monad/)
  * (Haskell) Notes on Free monads - John Wiegley [(blog post)](http://newartisans.com/2013/09/notes-on-free-monads/)
  * (Theory) [nLab](https://ncatlab.org/nlab/show/free+monad)

### Free Monad transformers

* Implementations: [Haskell transformers-free](http://hackage.haskell.org/package/transformers-free/docs/Control-Monad-Trans-Free.html) [Haskell free](http://hackage.haskell.org/package/free/docs/Control-Monad-Trans-Free.html) [Purescript](https://pursuit.purescript.org/packages/purescript-freet/)

* Resources
  * Free monad transformers - Gabriel Gonzalez [(blog post)](http://www.haskellforall.com/2012/07/free-monad-transformers.html)

### Cofree

Create comonad for any given type A. It is based on rose tree (multiple nodes, value in each node)
where List is replaced with any Functor F. Functor F dedicdes how Cofree comonad is branching.

```scala
case class Cofree[A, F[_]](extract: A, sub: F[Cofree[A, F]])(implicit functor: Functor[F]) {
  def map[B](f: A => B): Cofree[B, F] = Cofree(f(extract), functor.map(sub)(_.map(f)))
  def duplicate: Cofree[Cofree[A, F], F] = Cofree(this, functor.map(sub)(_.duplicate))
  def extend[B](f: Cofree[A, F] => B): Cofree[B, F] = duplicate.map(f) // coKleisi composition
}
```

* Resources
  * Scala Comonad Tutorial, Part 2 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/)
  * scalaz [(src Cofree)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Cofree.scala)

### Free Alternative

* Resources
  * [Haskell free/Control.Alternative.Free](http://hackage.haskell.org/package/free/docs/Control-Alternative-Free.html)
  * [Structurally enforced Free Alternative, without left distributivity - SO](https://stackoverflow.com/questions/45647253/structurally-enforced-free-alternative-without-left-distributivity)
  * Routing With Cofree Comonad - Marcin Szamotulski [(video)](https://www.youtube.com/watch?v=O78UOsKAXsc) [(slides)](https://coot.github.io/routing-with-cofree-comonad/#/) [(repo)](https://github.com/coot/routing-with-cofree-comonad)
  * Applicative Regular Expressions using the Free Alternative - Justin Le [(blog pos)](https://blog.jle.im/entry/free-alternative-regexp.html)

## Representable & Adjunctions

### Representable

```scala
// TODO Haskell extends Distrivutive, Scalaz require F to be Functor
trait Representable[F[_], Rep] {
  def tabulate[X](f: Rep => X): F[X]
  def index[X](fx: F[X])(f: Rep): X
}
```

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Representable.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Representable.scala), [Haskell](https://hackage.haskell.org/package/adjunctions/docs/Data-Functor-Rep.html), [UniMath](https://github.com/UniMath/UniMath/tree/master/UniMath/CategoryTheory/RepresentableFunctors), [nLab](https://ncatlab.org/nlab/show/representable+functor)

* Resources:
  * (Haskell) Representing Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2013/representing-applicatives/)
  * (Category Theory, Haskell) Representable Functors - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2015/07/29/representable-functors/)
  * (Category Theory, Haskell) Category Theory II 4.1: Representable Functors - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=KaBz45nZEZw) [Scala code translation](https://github.com/typelevel/CT_from_Programmers.scala/blob/master/src/main/tut/2.4-representable-functors.md)
  * (Haskell) Zippers Using Representable And Cofree - Chris Penner [(blog post)](http://chrispenner.ca/posts/representable-cofree-zippers):
  * Reasoning with representable functors - Adelbert Chang [(blog post)](https://adelbertc.github.io/posts/2017-08-09-representable-functors.html)
  * (Haskell) Radix Sort, Trie Trees, And Maps From Representable Functors - Chris Penner [(blog post)](https://chrispenner.ca/posts/representable-discrimination)
  * (Haskell) [Monad.Representable.Reader](hackage.haskell.org/package/adjunctions/docs/Control-Monad-Representable-Reader.html), [Monad.Representable.State](http://hackage.haskell.org/package/adjunctions/docs/Control-Monad-Representable-State.html), [Comonad.Representable.Store](http://hackage.haskell.org/package/adjunctions/docs/Control-Comonad-Representable-Store.html)
  * https://www.schoolofhaskell.com/user/edwardk/moore/for-less
  * https://jozefg.bitbucket.io/posts/2013-10-21-representable-functors.html
  * https://stackoverflow.com/a/46502280
  * https://stackoverflow.com/questions/6177950/representable-functor-isomorphic-to-bool-a
  * usage of Representable in [old hanshoglund/music-suite](https://github.com/hanshoglund/music-suite/blob/648354f701ba6806e259a4b79b59bb5699249eea/sketch/old/TT.hs#L1742-L1743)
  * Java [Mojang/DataFixerUpper Representable](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/kinds/Representable.java)
  
### Corepresentable

* Implementations: [Haskell](https://hackage.haskell.org/package/representable-functors/docs/Data-Functor-Corepresentable.html)

### Adjunction

Adjunction[F,B] spacify relation between two Functors (There is natural transformation between composition of those two functors and identity.)
We say that F is left adjoint to G.

```scala
trait Adjunction[F[_], G[_]] {
  def left[A, B](f: F[A] => B): A => G[B]
  def right[A, B](f: A => G[B]): F[A] => B
}
```

* Implementations [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Adjunction.scala), [Haskell](hackage.haskell.org/package/adjunctions/docs/Data-Functor-Adjunction.html), [Purescript](https://github.com/freebroccolo/purescript-adjunctions/blob/master/docs/Data/Functor/Adjunction.md), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Adjunctions/Core.v), [nLab](https://ncatlab.org/nlab/show/adjunction)

Adjunction can be defined between Reader monad and Coreader comonad.

* Resources:
  * Adjunctions And Battleship - Chris Penner [(blog post)](https://chrispenner.ca/posts/adjunction-battleship)
  * Scala Comonad Tutorial, Part 2 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/)
  * Adjunctions in Everyday Life - Rúnar Bjarnason [(video Scala)](https://www.youtube.com/watch?v=BLk4DlNZkL8) [(video Haskell)](https://www.youtube.com/watch?v=f-kdpR0BPqo)
  * [Scalaz docs](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/AdjunctUsage.scala)
  * [Haskell libraries using Adjunctions](https://packdeps.haskellers.com/reverse/adjunctions)
  * usage in [ekmett/representable-tries](https://github.com/ekmett/representable-tries/blob/master/src/Data/Functor/Representable/Trie.hs#L155-L157)
  * (Haskell) Representing Adjunctions - Edward Kmett [(blog post)](http://comonad.com/reader/2008/representing-adjunctions/)
  * (Haskell) Zapping Adjunctions - Edward Kmett [(blog post)](http://comonad.com/reader/2008/zapping-strong-adjunctions/)
  * Adjunctions - TheCatsters [(vide playlist)](https://www.youtube.com/watch?v=loOJxIOmShE&list=PL54B49729E5102248)
  * State monad using Adjunctions [kaifransson/adjoint-stacks](https://github.com/kaifransson/adjoint-stacks)
  * (Haskell) Free And Forgetful Functors - Chris Penner [(blog post)](https://chrispenner.ca/posts/free-forgetful-functors)
  * [Adjunctions - M.M. Fokkinga, Lambert Meertens](https://research.utwente.nl/en/publications/adjunctions)
  * [Generic Programming with Adjunctions - Ralf Hinze](http://www.cs.ox.ac.uk/ralf.hinze/LN.pdf)
  * [Relational Algebra by Way of Adjunctions - Jeremy Gibbons, Fritz, Henglein, Ralf Hinze, Nicolas Wu](https://www.cs.ox.ac.uk/jeremy.gibbons/publications/reladj.pdf)
  * (Haskell) [Monad.Trans.Adjoint](hackage.haskell.org/package/adjunctions/docs/Control-Monad-Trans-Adjoint.html), [Comonad.Trans.Adjoint](hackage.haskell.org/package/adjunctions/docs/Control-Comonad-Trans-Adjoint.html), [Monad.Trans.Contravariant.Adjoint](hackage.haskell.org/package/adjunctions/docs/Control-Monad-Trans-Contravariant-Adjoint.html)

## (Co)Yoneda & (Co)Density & Kan Extensions

### Yoneda

Construction that abstract over type constructor and allow to efficiently stack computations.

In Category Theory

Yoneda Lemma states that:
`[C,Set](C(a,-),F) ~ Fa`
Set of natural transformations from `C` to `Set` of the Hom functor `C(a,-)` to Functor `F: C -> Set`
is isomorphic to `Fa`

It is possible to formulate Yoneda Lemma in terms of Ends, and we get Ninja Yoneda Lemma:
&int; `Set(C(a,x),F(x)) ~ Fa`

That corresponds to:

`def yoneda[R](cax: A => X, fx F[X]) ~ F[A]`

```scala
trait Yoneda[F[_], A] {
  def run[R](f: A => R): F[R]
}
```

* we need Functor instance for F to create instance of Yoned for F
```scala
def liftYoneda[F[_], A](fa: F[A])(implicit FunctorF: Functor[F]): Yoneda[F, A] =
  new Yoneda[F, A] {
    def run[R2](f: A => R2): F[R2] = FunctorF.map(fa)(f)
  }
```

* we don't need the fact that F is a Functor to go back to F
```scala
def lowerYoneda[F[_], A](y: Yoneda[F, A]): F[A] = y.run(identity[A])
```

* we can define Functor instance, without any requirement on F:
```scala
def yonedaFunctor[F[_]]: Functor[Yoneda[F, ?]] =
  new Functor[Yoneda[F, ?]] {
    def map[A, B](fa: Yoneda[F, A])(f: A => B): Yoneda[F, B] =
      new Yoneda[F, B] {
        def run[C](f2: B => C): F[C] = fa.run(f andThen f2)
      }
  }
```

* Yoneda efficiently stack computations.

* Implementations scalaz [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Yoneda.scala), [Purescript](https://pursuit.purescript.org/packages/purescript-free/docs/Data.Yoneda), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/covyoneda.v), [HoTT](https://github.com/HoTT/HoTT/tree/master/theories/Categories/KanExtensions), [nlab](https://ncatlab.org/nlab/show/Yoneda+lemma)

* Resources
  * https://vimeo.com/122708005
  * Free Monads and the Yoneda Lemma - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
  * (Scala & Haskell) How Haskell is Changing my Brain, Yay Yoneda - Alissa Pajer [(video)](https://vimeo.com/96639840)
  * (Haskell) Reverse Engineering Machines with the Yoneda Lemma - Dan Piponi: [(blog post)](http://blog.sigfpe.com/2006/11/yoneda-lemma.html)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * Category Theory III 7.1, Natural transformations as ends - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=DseY4qIGZV4)
  * [Type Arithmetic and the Yoneda Perspective - Emily Pillmore](https://github.com/cohomolo-gy/Type-Arithmetic-and-the-Yoneda-Perspective)
  
### Coyoneda

Rúnar in [Free Monads and the Yoneda Lemma](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
describes this type as a proof that: "if we have a type B, a function of type (B => A) for some type A, and a value of type F[B] for some functor F, then we certainly have a value of type F[A]"

This result from Category Theory allows us to perform `Coyoneda Trick`:

If we have following type:

```scala
trait Coyoneda[F[_], A] {
  type B
  def f: B => A
  def fb: F[B]
}
```

then type constructor F can be lifted to Coyoneda

```scala
def liftCoyoneda[F[_], A](fa: F[A]): Coyoneda[F, A]
```

we can map over lifted constructor F, without any requirements on F. So Coyoneda is a Free Functor:

```scala
implicit def coyoFunctor[F[_]]: Functor[Coyoneda[F, ?]] = new Functor[Coyoneda[F, ?]] {
  def map[A, AA](fa: Coyoneda[F, A])(ff: A => AA): Coyoneda[F, AA] = new Coyoneda[F, AA] {
    type B = fa.B
    def f: B => AA = fa.f andThen ff
    def fb: F[B] = fa.fb
  }
}
```

We even can change the oryginal type of F

```scala
def hoistCoyoneda[F[_], G[_], A, C](fab : NaturalTransf[F,G])(coyo: Coyoneda[F, A]): Coyoneda[G, A] =
  new Coyoneda[G, A] {
    type B = coyo.B
    def f: B => A = coyo.f
    def fb: G[B] = fab(coyo.fb)
  }
```

Finally to get back from Coyoneda fantazy land to reality of F, we need a proof that it is a Functor:

```scala
def lowerCoyoneda(implicit fun: Functor[F]): F[A]
```

* Implementations: [calaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Coyoneda.scala), [Haskell](https://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Coyoneda.html), [nLab](https://ncatlab.org/nlab/show/co-Yoneda+lemma)

* Resources
  * loop/recur Coyoneda [(video)](https://vimeo.com/122708005)
  * Free Monads and the Yoneda Lemma - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
  * (Scala & Haskell) How Haskell is Changing my Brain, Yay Yoneda - Alissa Pajer [(video)](https://vimeo.com/96639840)
  * (Haskell) Reverse Engineering Machines with the Yoneda Lemma - Dan Piponi: [(blog post)](http://blog.sigfpe.com/2006/11/yoneda-lemma.html)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)

### Right Kan extension

```scala
trait Ran[G[_], H[_], A] {
  def runRan[B](f: A => G[B]): H[B]
}
```

* Implementations: [Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala), [Haskell](http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Kan-Ran.html), [Purescript](https://github.com/freebroccolo/purescript-kan-extensions/blob/master/src/Data/Functor/Kan/Ran.purs), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/RightKanExtension.v), [nLab](https://ncatlab.org/nlab/show/Kan+extension)

* We can create functor for Ran, without any requirements on G, H
```scala
def ranFunctor[G[_], H[_]]: Functor[Ran[G, H, ?]] =
    new Functor[Ran[G, H, ?]] {

      def map[A, B](fa: Ran[G, H, A])(f: A => B): Ran[G, H, B] =
        new Ran[G, H, B] {
          def runRan[C](f2: B => G[C]): H[C] =
            fa.runRan(f andThen f2)
        }
    }
```

* We can define Monad for Ran, without any requirements on G, H. Monad
generated by Ran is Codensity.
```scala
def codensityMonad[F[_], A](ran: Ran[F, F, A]): Codensity[F, A] =
  new Codensity[F, A] {
    def run[B](f: A => F[B]): F[B] = ran.runRan(f)
  }
```

* Resources
  * [Haskell libraries using Kan extensions](https://packdeps.haskellers.com/reverse/kan-extensions)
  * (Haskell, Category Theory) Kan Extensions - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/04/17/kan-extensions/)
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Haskell) Kan Extensions II: Adjunctions, Composition, Lifting - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions-ii/)
  * (Haskell) Kan Extensions III: As Ends and Coends - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extension-iii/)
  * (Haskell) Free Monads for Less (Part 1 of 3): Codensity - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less/)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * (Haskell) Free Monads for Less (Part 3 of 3): Yielding IO - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-3/)
  * (Haskell) List based on right Kan extension [SO](https://stackoverflow.com/questions/27381133/list-based-on-right-kan-extension/27382234)
  * What is a layman's explanation of "Kan extensions"? [(quora)](https://www.quora.com/What-is-a-laymans-explanation-of-Kan-extensions)
  * (Theory) [Lecture 49 - Chapter 3: Kan Extensions - John Baez](https://forum.azimuthproject.org/discussion/2266/lecture-49-chapter-3-kan-extensions/p1)
  * (Theory) [Kan Extensions for Program Optimisation Or: Art and Dan Explain an Old Trick - Ralf Hinze](https://www.cs.ox.ac.uk/ralf.hinze/publications/MPC12.pdf) (adjunctions, kan extensions, string diagrams)
  * (Haskell) Combinatorial Species and Labelled Structures (PhD thesis) - Brent A. Yorgey [(pdf)](http://ozark.hendrix.edu/~yorgey/pub/thesis.pdf), [(repo)](https://github.com/byorgey/thesis)
  * (Hasekll) (Co-)Iteration for Higher-Order Nested Datatypes - Andreas Abel, Ralph Matthes [(paper)](http://www.cse.chalmers.se/~abela/publications.html#types02)
  * (Theory) Kan Extensions as the Most Universal of the Universal Constructions - Marina Lehner [(thesis)](http://www.math.harvard.edu/theses/senior/lehner/lehner.pdf)

### Left Kan Extension

```scala
trait Lan[F[_], H[_], A] {
  type B
  val hb: H[B]
  def f: F[B] => A
}
```

[Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala), [Haskell](http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Kan-Lan.html), [Purescript](https://github.com/freebroccolo/purescript-kan-extensions/blob/master/src/Data/Functor/Kan/Lan.purs), [nLab](https://ncatlab.org/nlab/show/Kan+extension)

* we can define Functor for it
```scala
def lanFunctor[F[_], H[_]]: Functor[Lan[F, H, ?]] = new Functor[Lan[F, H, ?]]() {
  def map[A, X](x: Lan[F, H, A])(fax: A => X): Lan[F, H, X] = {
    new Lan[F, H, X] {
      type B = x.B
      val hb: H[B] = x.hb
      def f: F[B] => X = x.f andThen fax
    }
  }
}
```

* Resources
  * [Haskell libraries using Kan extensions](https://packdeps.haskellers.com/reverse/kan-extensions)
  * Constructing Applicative Functors - Ross Paterson [(paper)](http://www.staff.city.ac.uk/~ross/papers/Constructors.html)
  * (Theory) [Lecture 50 - Chapter 3: Left Kan Extensions - John Baez](https://forum.azimuthproject.org/discussion/2267/lecture-50-chapter-3-left-kan-extensions)
  * (Haskell, Category Theory) Kan Extensions - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/04/17/kan-extensions/)

### Density Comonad

Density is a Comonad that is simpler that Left Kan Extension.
More precisely it is comonad formed by left Kan extension of a Functor along itself.)

```scala
trait Density[F[_], Y] { self =>
  type X
  val fb: F[X]
  def f: F[X] => Y
  
  def densityToLan: Lan[F,F,Y] = new Lan[F,F,Y] {
   type B = X
   val hb: F[B] = fb
   def f: F[B] => Y = self.f
  }
}

object Density {
  def apply[F[_], A, B](kba: F[B] => A, kb: F[B]): Density[F, A] = new Density[F, A] {
    type X = B
    val fb: F[X] = kb
    def f: F[X] => A = kba
  }
}
```

[Scalaz PR](https://github.com/scalaz/scalaz/pull/2029), [Purescript](https://github.com/rightfold/purescript-density-codensity/blob/master/src/Control/Comonad/Density.purs), [Haskell](https://hackage.haskell.org/package/kan-extensions/docs/Control-Comonad-Density.html)

Density form a Functor without any conditions on F, so it is a Free Functor. Similar like Lan.

```scala
def functorInstance[K[_]]: Functor[Density[K, ?]] = new Functor[Density[K, ?]] {
  def map[A, B](x: Density[K, A])(fab: A => B): Density[K, B] = Density[K,B,x.X](x.f andThen fab, x.fb)
}
```

Density is a Comonad without any conditions of F, so it is a Free Comonad.

```scala
def comonadInstance[K[_]]: Comonad[Density[K, ?]] = new Comonad[Density[K, ?]] {
  def extract[A](w: Density[K, A]): A = w.f(w.fb)
  def duplicate[A](wa: Density[K, A]): Density[K, Density[K, A]] =
    Density[K, Density[K, A], wa.X](kx => Density[K, A, wa.X](wa.f, kx), wa.fb)
  def map[A, B](x: Density[K, A])(f: A => B): Density[K, B] = x.map(f)
}
```

* Density is also left adjoint to Comonad formed by Adjunction.

* Resources
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Haskell) A Product of an Imperfect Union - Edward Kmett [(blog post)](http://comonad.com/reader/2011/a-product-of-an-imperfect-union/)
  * [Comonads from Monads, and a new way do the reverse - u/King_of_the_Homeless](https://www.reddit.com/r/haskell/comments/8hgub9/comonads_from_monads_and_a_new_way_do_the_reverse/)
  * (Haskell) kan-extensions/Control.Monad.Co [diter](http://hackage.haskell.org/package/kan-extensions-5.2/docs/Control-Monad-Co.html#v:diter) [dctrlM](http://hackage.haskell.org/package/kan-extensions/docs/Control-Monad-Co.html#v:dctrlM)
  * small note in: Adjoint folds and unfolds—An extended study - Ralf Hinze [(paper)](https://www.cs.ox.ac.uk/ralf.hinze/publications/SCP-78-11.pdf)
    and in Generic Programming with Adjunctions - Ralf Hinze [(paper)](http://www.cs.ox.ac.uk/ralf.hinze/LN.pdf)
  * [Edward Kmett mentions it in Origami.hs](https://github.com/ekmett/ekmett.github.com/blob/8d3abab5b66db631e148e1d046d18909bece5893/haskell/Origami.hs#L225-L226)

### Codensity

Interface with flatMap'ish method:

```scala
trait Codensity[F[_], A] {
  def run[B](f: A => F[B]): F[B]
}
```

* Implementations: [Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Codensity.scala), [Haskell](https://hackage.haskell.org/package/kan-extensions/docs/Control-Monad-Codensity.html), [Purescript](https://github.com/rightfold/purescript-density-codensity/blob/master/src/Control/Monad/Codensity.purs)

It gives us monad (without any requirement on F):

```scala
implicit def codensityMonad[G[_]]: Monad[Codensity[G, ?]] =
  new Monad[Codensity[G, ?]] {
    def map[A, B](fa: Codensity[G, A])(f: A => B): Codensity[G, B] =
      new Codensity[G, B] {
        def run[C](f2: B => G[C]): G[C] = fa.run(f andThen f2)
      }

    def unit[A](a: A): Codensity[G, A] =
      new Codensity[G, A] {
        def run[B](f: A => G[B]): G[B] = f(a)
      }

    def flatMap[A, B](c: Codensity[G, A])(f: A => Codensity[G, B]): Codensity[G, B] =
      new Codensity[G, B] {
        def run[C](f2: B => G[C]): G[C] = c.run(a => f(a).run(f2))
      }
  }
```

* Resources
  * Difference Lists and the Codensity Monad - Mio Alter [(video, slides, blog post)](https://begriffs.com/posts/2016-02-04-difference-lists-and-codennsity.html)
  * The Free and The Furious: And by 'Furious' I mean Codensity - raichoo [(video)]()https://www.youtube.com/watch?v=EiIZlX_k89Y)
  * (Haskell) Free Monads for Less (Part 1 of 3): Codensity - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less/)
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Haskell) Kan Extensions II: Adjunctions, Composition, Lifting - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions-ii/)
  * (Haskell) Kan Extensions III: As Ends and Coends - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extension-iii/)
  * (Haskell) Unnatural Transformations and Quantifiers - Edward Kmett [blog post](http://comonad.com/reader/2012/unnatural-transformations-and-quantifiers/)
  * scalaz [example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/CodensityUsage.scala)


## Contravariant Adjuctions & Representable

### Contravariant Adjunction

* Implementations: [Haskell](hackage.haskell.org/package/adjunctions/docs/Data-Functor-Contravariant-Adjunction.html), [nLab](https://ncatlab.org/nlab/show/dual+adjunction)

### Contravariant Rep

* Implementations: [Haskell](hackage.haskell.org/package/adjunctions/docs/Data-Functor-Contravariant-Rep.html)

## Contravariant Kan Extensions

### Contravariant Yoneda

* Implementation: [Haskell](https://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Contravariant-Yoneda.html)

### Contravariant Coyoneda

Similar a Coyoneda yet, with existenctial function running in opposite direction.

```scala
trait ContravariantCoyoneda[F[_], A] {
  type B
  val fb: F[B]
  val m: A => B


  def lowerCoyoneda(implicit CF: Contravariant[F]): F[A] = CF.contramap(fb)(m) // run
}

def liftCoyoneda[F[_], AA](fa: F[AA]): ContravariantCoyoneda[F, AA] = new ContravariantCoyoneda[F, AA] {
  type B = AA
  val fb: F[B] = fa
  val m: AA => B = identity[AA]
}
```

We can define Contravariant instance for Contravariant Coyoneda:

```scala
def cotraContraCoyo[F[_]] = new Contravariant[ContravariantCoyoneda[F, ?]] {
  def contramap[AA, BB](fa: ContravariantCoyoneda[F, AA])(f: BB => AA): ContravariantCoyoneda[F, BB] = new ContravariantCoyoneda[F, BB] {
    type B = fa.B
    val fb: F[B] = fa.fb
    val m: BB => B = fa.m compose f
  }
}
```

* Implementation: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/ContravariantCoyoneda.scala) [Haskll](https://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Contravariant-Coyoneda.html)

* Resources:
  * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/ContravariantCoyonedaUsage.scala)

### Contravariant Day

* Implementation: [Haskell](https://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Contravariant-Day.html)

### Invariant Day

* Implementation: [Haskell](https://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Invariant-Day.html)

### Functor Functor (FFunctor)

Functor that works on natural transformations rather than on regular types

```scala
trait FFunctor[FF[_]] {
  def ffmap[F[_],G[_]](nat: NaturalTransf[F,G]): FF[F] => FF[G]
}
```

* Laws:
  * identity: `ffmap id == id`
  * composition: `ffmap (eta . phi) = ffmap eta . ffmap phi`

* Resources
  * (Haskell) Functor Functors - Benjamin [(blog post)](https://www.benjamin.pizza/posts/2017-12-15-functor-functors.html)

### Monad morphisms

* Resources
  * (Haskell) [monad morphisms](http://hackage.haskell.org/package/mmorph/docs/Control-Monad-Morph.html)
  * (Haskell) [MFunctor](http://hackage.haskell.org/package/mmorph/docs/Control-Monad-Morph.html#t:MFunctor) [used to be in pipes](https://hackage.haskell.org/package/pipes-3.1.0/docs/Control-MFunctor.html)
  * (Haskell) Q: What is not an MFunctor? [reddit](https://www.reddit.com/r/haskell/comments/2c87m8/q_what_is_not_an_mfunctor/)
  * (Haskell) [MMonad](http://hackage.haskell.org/package/mmorph-1.1.2/docs/Control-Monad-Morph.html#t:MMonad)
  * (Haskell) [Github issue with code with MCoyoneda](https://github.com/Gabriel439/Haskell-MMorph-Library/issues/33)
  * (Haskell) [Tutorial - Gabriel Gonzalez](http://hackage.haskell.org/package/mmorp-1.1.2/docs/Control-Monad-Morph.html#g:3)
  * (Haskell) mmorph-1.0.0: Monad morphisms - Gabriel Gonzalez [blog post](http://www.haskellforall.com/2013/03/mmorph-100-monad-morphisms.html)

### higher kinded category theory
  * [hask](https://github.com/ekmett/hask)
  * [cokin](http://hackage.haskell.org/package/conkin/docs/Conkin.html)
  * [rank2classes](http://hackage.haskell.org/package/rank2classes)

### Monoidal Categories, Monoid Object

In Category Theory a Monoidal Category is a Category with a Bifuctor and morphisms that satisfy some laws (see gist for details).

```scala
trait MonoidalCategory[M[_, _], I] {
  val tensor: Bifunctor[M]
  val mcId: I

  def rho[A]    (mai: M[A,I]): A
  def rho_inv[A](a:   A):      M[A, I]

  def lambda[A]      (mia: M[I,A]): A
  def lambda_inv[A,B](a: A):        M[I, A]

  def alpha[A,B,C](    mabc: M[M[A,B], C]): M[A, M[B,C]]
  def alpha_inv[A,B,C](mabc: M[A, M[B,C]]): M[M[A,B], C]
}
```

* Implementations: [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Monoidal/MonoidalCategories.v), [nlab](https://ncatlab.org/nlab/show/monoidal+category)

We can create monoidal category where product (Tuple) is a bifunctor or an coproduct (Either).

Monoidal Categories are usefull if we consider category of endofunctors. If we develop concept of Monoid Object then it is possible to define
Monads as Monoid Object in Monoidal Category of Endofunctors with Product as Bifunctor
Applicative as Monoid Object in Monoidal Category of Endofunctors with Day convolution as Bifunctor

In category of Profunctors with Profunctor Product as Bifunctor the Monoid Ojbect is Arrow.

* Resources
  * lemastero/MonoidalCategories.scala [(Gist)](https://gist.github.com/lemastero/cb50818fc40361ffb309701cffa651c9)
  * (Haskell, Category Theory) Discrimination is Wrong: Improving Productivity - Edward Kmett [(video)](https://www.youtube.com/watch?v=cB8DapKQz-I&feature=youtu.be&t=373) [slides pdf](http://yowconference.com.au/slides/yowlambdajam2015/Kmett-DiscriminationIsWrong.pdf)
  * (Haskell, Category  Theory) Notions of Computation as Monoids (extended version) - Exequiel Rivas, Mauro Jaskelioff [(paper)](http://www.fceia.unr.edu.ar/~mauro/pubs/Notions_of_Computation_as_Monoids_ext.pdf)
  * (Haskell) Monoidal Category [data-category/Data.Category.Monoidal](https://hackage.haskell.org/package/data-category/docs/Data-Category-Monoidal.html), [categories/Control.Category.Monoidal](https://hackage.haskell.org/package/categories/docs/Control-Category-Monoidal.html)
 
 
### Cartesian Closed Category

* Resources
  * [TomasMikula/LambdaCart](https://github.com/TomasMikula/LambdaCart) (CCC is used to define EDSL in Scala)
  * [Compiling to categories - Conal Elliott](http://conal.net/papers/compiling-to-categories/) (Encoding of many CT abstractions, applications for automatic derivation of functions, describing hardware, Fast Fourier Transformation)
  * (Haskell) [Cartesian Closed Category in data-category](https://hackage.haskell.org/package/data-category/docs/Data-Category-CartesianClosed.html)
  * (Haskell) [Cartesian Closed Category in category-extras](https://hackage.haskell.org/package/category-extras-0.53.0/docs/Control-Category-Cartesian-Closed.html)

### Day Convolution

Monads are monoids in a monoidal category of endofunctors.
Applicative functors are also monoids in a monoidal category of endofunctors but as a tensor is used Day convolution.

There is nice intuition for Day convolution as generalization of one of Applicative Functor methods. 

* Haskell

```haskell
data Day f g a where
  Day :: forall x y. (x -> y -> a) -> f x -> g y -> Day f g a
```
* Scala

```scala
trait DayConvolution[F[_], G[_], A] {
  type X
  type Y
  val fx: F[X]
  val gy: G[Y]
  def xya: (X, Y) => A
}
```

* Implementation: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Day.scala), [Haskell](http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Day.html), [Purescritp](https://pursuit.purescript.org/packages/purescript-day/10.0.0/docs/Data.Functor.Day)

* There is various ways to create Day Convolution:
```scala
def day[F[_], G[_], A, B](fab: F[A => B], ga: G[A]): Day[F, G, B]
def intro1[F[_], A](fa: F[A]): Day[Id, F, A]
def intro2[F[_], A](fa: F[A]): Day[F, Id, A]
```

* Day convolution can be transformed by mapping over last argument, applying natural transformation to one of type constructors, or swapping them
```scala
def map[B](f: A => B): Day[F, G, B]
def trans1[H[_]](nat: NaturalTransf[F, H]): Day[H, G, A]
def trans2[H[_]](nat: NaturalTransf[G, H]): Day[F, H, A]
def swapped: Day[G, F, A] = new Day[G, F, A]
```

* There is various ways to collapse Day convolution into value in type constructor:
```scala
def elim1[F[_], A](d: Day[Id, F, A])(implicit FunF: Functor[F]): F[A]
def elim2[F[_], A](d: Day[F, Id, A])(implicit FunF: Functor[F]): F[A]
def dap[F[_], A](d: Day[F, F, A])(implicit AF: Applicative[F]): F[A]
```

* We can define Functor instance without any conditions on type constructors (so it forms Functor for free like Coyoneda):

```scala
def functorDay[F[_], G[_]]: Functor[DayConvolution[F, G, ?]] = new Functor[DayConvolution[F, G, ?]] {
  def map[C, D](d: DayConvolution[F, G, C])(f: C => D): DayConvolution[F, G, D] =
    new DayConvolution[F, G, D] {
      type X = d.X
      type Y = d.Y
      val fx: F[X] = d.fx
      val gy: G[Y] = d.gy

      def xya: X => Y => D = x => y => f(d.xya(x)(y))
    }
}
```

* If both type constructor are Applicative then whoe Day Convolution is applicative.
Similarly it is Comonad if both type constructors are Comonads.

* Resources
  * (Haskell) Notions of Computation as Monoids by Exequiel Rivas, Mauro Jaskelioff [(paper)](https://arxiv.org/abs/1406.4823)
  * (Haskell) Reddit comment by echatav [(comment)](https://www.reddit.com/r/haskell/comments/4wvae2/functorial_blog_comonads_and_day_convolution/d6bem5i/)
  * (Haskell) Comonads and Day Convolution - Phil Freeman [(blog post)](http://blog.functorial.com/posts/2016-08-08-Comonad-And-Day-Convolution.html)
  * (Purescript) extensible coeffect system built out of comonads and Day convolution [paf31/purescript-smash](https://github.com/paf31/purescript-smash/blob/master/src/Data/Smash.purs)
  * (Purescript) [paf31/purescript-react-explore](https://github.com/paf31/purescript-react-explore/blob/master/src/React/Explore/List.purs)
  * (Haskell) usage examples with Free CoFree [jwiegley/notes Day](https://github.com/jwiegley/notes/blob/0d8720a38221fdface5e766a66423c1b14e89484/haskell/Day.hs)


### Profunctor

Profunctor abstract over
- type constructor with two holes `P[_,_]`
- operation `def dimap(preA: NewA => A, postB: B => NewB): P[A, B] => P[NewA, NewB]` 
that given `P[A,B]` and two functions 
- apply first `preA` before first type of `P` (ast as contravariant functor)
- apply second `postB` after second type of `P` (act as functor)

Alternatively we can define Profunctor not using dimap but using two separate functions:
 * def lmap(f: AA => A): P[A,C] => P[AA,C] = dimap(f,identity[C])
 * def rmap(f: B => BB): P[A,B] => P[A,BB] = dimap(identity[A], f)

Profunctors in Haskell were explored by sifpe at blog A Neighborhood of Infinity in post [Profunctors in Haskell](http://blog.sigfpe.com/2011/07/profunctors-in-haskell.html)
Implemented in Haskell: [ekmett/profunctors](https://github.com/ekmett/profunctors)

```scala
trait Profunctor[F[_, _]] {
  def dimap[A, B, C, D](fab: F[A, B])(f: C => A)(g: B => D): F[C, D]
}
```

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/profunctor.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Profunctor.scala), [Haskell](hackage.haskell.org/package/profunctors/docs/Data-Profunctor.html), [Purescript](https://github.com/purescript/purescript-profunctor/blob/master/src/Data/Profunctor.purs), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Profunctors/Core.v), [nLab](https://ncatlab.org/nlab/show/profunctor), [Java](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/optics/profunctors/Profunctor.java)

* Alternatively we can define functor using:
```scala
def lmap[A, B, C](fab: F[A, B])(f: C => A): F[C, B]
def rmap[A, B, C](fab: F[A, B])(f: B => C): F[A, C]
```

* Most popular is instance for Function with 1 argument:

```scala
trait Profunctor[Function1] {
  def lmap[A,B,C](f: A => B): (B => C) => (A => C) = f andThen
  def rmap[A,B,C](f: B => C): (A => B) => (A => C) = f compose
}
```

Becasue Profunctors can be used as base to define Arrows therefore there are instances for Arrow like constructions like `Kleisli`

* In Category Theroy:
When we have Category `C` and `D` and `D'` the opposite category to D,
then a Profunctor `P` is a Functor  `D' x C -> Set` We write `D -> C`
In category of types and functions we use only one category, so Profunctor P is `C' x C => C`
  
* Laws [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala#L39-L45) [Cats](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ProfunctorLaws.scala):
- if we define Profunctor using dimap:
  * `dimap id id == id`
```scala
def dimapIdentity[A, B](p: P[A, B]): Boolean = {
  //          dimap(id, id)
  // P[A,B] ================> P[A,B]
  dimap(identity[A], identity[B])(p) == p
}
```
  * `dimap (f . g) (h . i) == dimap g h . dimap f i`
```scala
def dimapComposition[A, B, C, D, E, F](pad: P[A,D], fcb: C => B, fba: B => A, fde: D => E, fef: E => F): Boolean = {
  //          dimap B=>A D=>E
  // P[A,D] ===================> F[B,E]
  val pbe: P[B, E] = dimap(fba, fde)(pad)
  //          dimap C=>B E=>F
  // P[B,E] ====================> P[C,F]
  val l: P[C,F] = dimap(fcb, fef)(pbe)

  val fca: C => A = fba compose fcb
  val fdf: D => F = fef compose fde
  //         dimap C=>A D=> F
  // P[A,D] ===================> P[C,F]
  val r: P[C,F] = dimap(fca, fdf)(pad)
  
  l == r
}
```
Second law we get for free by parametricity.

- if specify lmap or rmap
  * `lmap id == id`
  * `rmap id == id`
  * `lmap (f . g) == lmap g . lmap f`
  * `rmap (f . g) == rmap f . rmap g`
  
Last two laws we get for free by parametricity.

- if specify both (in addition to law for dimap and laws for lmap:
  * `dimap f g == lmap f . rmap g`

* Resources
   * (Haskell) Fun with Profunctors - Phil Freeman [video](https://www.youtube.com/watch?v=OJtGECfksds)
   * I love profunctors. They're so easy - Liyang HU [(post)](https://www.schoolofhaskell.com/school/to-infinity-and-beyond/pick-of-the-week/profunctors)
   * [Haskell libraries using Profunctors](https://packdeps.haskellers.com/reverse/profunctors)
   * [Tom Ellis: 24 Days of Hackage: profunctors](https://ocharles.org.uk/guest-posts/2013-12-22-24-days-of-hackage-profunctors.html)
   * Explorations in Variance - Michael Pilquist [(video)](https://www.youtube.com/watch?v=VZWLRepyNvo)
   * Monadic profunctors for bidirectional programming [(post)](https://blog.poisson.chat/posts/2017-01-01-monadic-profunctors.html), [(blog Lysxia)](https://blog.poisson.chat/), repo [Lysxia/profunctor-monad](https://github.com/Lysxia/profunctor-monad)
   * Analog of free monads for Profunctors [(post SO)](https://stackoverflow.com/questions/39241262/analog-of-free-monads-for-profunctors)
   * Category Theory III 6.1, Profunctors - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=XJgfrF3O6iE)
   * [How to abstract over a “back and forth” transformation? - SO](https://stackoverflow.com/questions/15222013/how-to-abstract-over-a-back-and-forth-transformation/15235409#15235409)

### Star 

Lift Functor into Profunctor "forward"

```scala
case class Star[F[_],D,C](runStar: D => F[C])
```

If `F` is a Functor then `Star[F, ?, ?]` is a Profunctor:

```scala
def profunctor[F[_]](implicit FF: Functor[F]): Profunctor[Star[F, ?,?]] = new Profunctor[Star[F, ?, ?]] {
  def dimap[X, Y, Z, W](ab: X => Y, cd: Z => W): Star[F, Y, Z] => Star[F, X, W] = bfc =>
    Star[F,X, W]{ x =>
      val f: Y => F[Z] = bfc.runStar
      val fz: F[Z] = f(ab(x))
      FF.map(fz)(cd)
    }
}
```

### CoStar

Lift Functor into Profunctor "backwards"

```scala
case class Costar[F[_],D,C](runCostar: F[D] => C)
```

If `F` is a Functor then `Costar[F, ?, ?]` is a Profunctor

```scala
def profunctor[F[_]](FF: Functor[F]): Profunctor[Costar[F, ?, ?]] = new Profunctor[Costar[F, ?, ?]] {
  def dimap[A, B, C, D](ab: A => B, cd: C => D): Costar[F, B, C] => Costar[F, A, D] = fbc =>
    Costar{ fa =>
      val v: F[B] = FF.map(fa)(ab)
      val c: C = fbc.runCostar(v)
      cd(c)
    }
}
```

### Strong Profunctor

Profunctor with additional method `first` that lift profunctor so it can run on first element of tuple.

For Profunctor of functions from A to B this operation just apply function to first element of tuple.

```scala
trait StrongProfunctor[P[_, _]] extends Profunctor[P] {
  def first[X,Y,Z](pab: P[X, Y]): P[(X, Z), (Y, Z)]
}
```

* Laws [Haskell](https://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Strong.html) [Cats](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/StrongLaws.scala)
  1) `first == dimap(swap, swap) andThen second`
  2) `lmap(_.1) == rmap(_.1) andThen first`
  3) `lmap(second f) andThen first == rmap(second f) andThen first`
  4) `first . first ≡ dimap assoc unassoc . first`
  5) `second ≡ dimap swap swap . first`
  6) `lmap snd ≡ rmap snd . second`
  7) `lmap (first f) . second ≡ rmap (first f) . second`
  8) `second . second ≡ dimap unassoc assoc . second`
    
where
```haskell
assoc ((a,b),c) = (a,(b,c))
unassoc (a,(b,c)) = ((a,b),c)
```
  
In [Notions of Computation as Monoids by Exequiel Rivas and Mauro Jaskelioff](https://www.fceia.unr.edu.ar/~mauro/pubs/Notions_of_Computation_as_Monoids_ext.pdf) in 7.1 there are following laws:
1) `dimap identity pi (first a) = dimap pi id a`
2) `first (first a) = dimap alphaInv alpha (first a)`
3) `dimap (id × f) id (first a) = dimap id (id × f) (first a)`
    
* Derived methods:
```scala
def second[X,Y,Z](pab: P[X, Y]): P[(Z, X), (Z, Y)]
def uncurryStrong[P[_,_],A,B,C](pa: P[A, B => C])(S: Strong[P]): P[(A,B),C]
```
In [Purescript implementation of Strong](https://pursuit.purescript.org/packages/purescript-profunctor/docs/Data.Profunctor.Strong) there are some more helper methods that use Category constraint for P.

* Most common instance is Function with one argument:

```scala
val Function1Strong = new Strong[Function1] with Function1Profunctor {
  def first[X, Y, Z](f: Function1[X, Y]): Function1[(X,Z), (Y, Z)] = { case (x,z) => (f(x), z) }
}
```

it is possible to define instance for Kleisli arrow

* Implementations: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Strong.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Strong.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/strong.scala) [Haskell](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Strong.html) [Purescript](https://github.com/purescript/purescript-profunctor/blob/master/src/Data/Profunctor/Strong.purs)

* Resources:
   * usage of Strong in [paf31/purescript-sdom](https://github.com/paf31/purescript-sdom/blob/master/src/SDOM.purs#L197-L205)

### Tambara

```scala
trait Tambara[P[_,_],A,B]{
  def runTambara[C]: P[(A,C),(B,C)]
}
```

Tambara is a Profunctor:

```scala
trait Profunctor[Tambara[P, ?, ?]] {
  def PP: Profunctor[P]

  def dimap[X, Y, Z, W](f: X => Y, g: Z => W): Tambara[P, Y, Z] => Tambara[P, X, W] = (tp : Tambara[P, Y, Z]) => new Tambara[P, X, W]{
   
    def runTambara[C]: P[(X, C), (W, C)] = {
      val fp: P[(Y,C),(Z,C)] => P[(X, C), (W, C)] = PP.dimap(
        Function1Strong.first[X, Y, C](f),
        Function1Strong.first[Z, W, C](g)
      )
      val p: P[(Y,C),(Z,C)] = tp.runTambara[C]
      fp(p)
    }
  }
}
```

It is also FunctorProfunctor:

```scala
def promap[P[_, _], Q[_, _]](f: DinaturalTransformation[P, Q])(implicit PP: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => Tambara[P, A, B]], Lambda[(A,B) => Tambara[Q, A, B]]] = {
  new DinaturalTransformation[Lambda[(A,B) => Tambara[P, A, B]], Lambda[(A,B) => Tambara[Q, A, B]]] {
    def dinat[X, Y](ppp: Tambara[P, X, Y]): Tambara[Q, X, Y] = new Tambara[Q, X, Y] {
      def runTambara[C]: Q[(X, C), (Y, C)] = {
        val p: P[(X,C), (Y,C)] = ppp.runTambara
        f.dinat[(X,C), (Y,C)](ppp.runTambara)
      }
    }
  }
}
```

# Profunctor Costrong

```scala
trait Costrong[F[_,_]] extends Profunctor[F] {
  def unfirst[A,B,D](fa: F[(A,D), (B, D)]): F[A,B]
  def unsecond[A,B,D](fa: F[(D,A),(D,B)]): F[A,B]
}
```

### Choice Profunctor

Profunctor with additional method left that wrap both types inside Either.

```scala
trait ProChoice[P[_, _]] extends Profunctor[P] {
  def left[A,B,C](pab: P[A, B]):  P[Either[A, C], Either[B, C]]
}
```

* derived method
```scala
def right[A,B,C](pab: P[A, B]): P[Either[C, A], Either[C, B]]
```

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/choice.scala) [Haskell](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Choice.html) [Purescript](https://github.com/purescript/purescript-profunctor/blob/master/src/Data/Profunctor/Choice.purs)

### Extranatural Transformation

```scala
trait ExtranaturalTransformation[P[_,_],Q[_,_]]{
  def exnat[A,B](p: P[A,B]): Q[A,B]
}
```
 
* Resources
   * [Haskell profunctors/ Data.Profunctor.Types :->](https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Types.html#t::-45--62-)


### Profunctor Functor

Functor (endofunctor) between two Profunctors.

It is different than regualar Functor:
Functor lifts regular function to function working on type constructor: def map[A, B](f: A => B): F[A] => F[B]
Profunctor lifts two regular functions to work on type constructor with two holed.

And ProfunctorFunctor lifts dinatural transformation of two Profunctors P[_,_] => Q[_,_]
 
operates on type constructor with one hole (F[A] => F[B])
and ProfunctorFunctor and ProfunctorFunctor map P[A,B] => Q[A,B]

 in Scala 2.12 we cannot express type constructor that have hole with shape
that is not sepcified)

```scala
trait ProfunctorFunctor[T[_]] {
  def promap[P[_,_], Q[_,_]](dt: DinaturalTransformation[P,Q])(implicit PP: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[P[A,B]]], Lambda[(A,B) => T[Q[A,B]]]]
}
```

### Profunctor Monad

```scala
trait ProfunctorMonad[T[_]] extends ProfunctorFunctor[T] {
  def proreturn[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[P, Lambda[(A,B) => T[P[A,B]]]]
  def projoin[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[T[P[A,B]]]], Lambda[(A,B) => T[P[A,B]]]]
}
```

* Laws:
  * `promap f . proreturn == proreturn . f`
  * `projoin . proreturn == id`
  * `projoin . promap proreturn == id`
  * `projoin . projoin == projoin . promap projoin`

### Profunctor Comonad

```scala
trait ProfunctorComonad[T[_]] extends ProfunctorFunctor[T] {
  def proextract[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[P[A,B]]], P]
  def produplicate[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[P[A,B]]], Lambda[(A,B) => T[T[P[A,B]]]]]
}
```

* Laws
  * `proextract . promap f == f . proextract`
  * `proextract . produplicate == id`
  * `promap proextract . produplicate == id`
  * `produplicate . produplicate == promap produplicate . produplicate`

### Profunctor Yoneda

```scala
trait ProfunctorYoneda[P[_,_],A,B] {
  def runYoneda[X,Y](f: X => A, g: B => Y): P[X,Y]
}
```

is a Profunctor for free, because we can define:
```scala
def dimap[AA, BB](l: AA => A, r: B => BB): ProfunctorYoneda[P, AA, BB] = new ProfunctorYoneda[P, AA, BB] {
  def runYoneda[X, Y](l2: X => AA, r2: BB => Y): P[X, Y] = {
    val f1: X => A = l compose l2
    val f2: B => Y = r2 compose r
    self.runYoneda(f1, f2)
  }
}
```

### Profunctor CoYoneda

```scala
trait ProfunctorCoyoneda[P[_,_],A,B] {
  type X
  type Y
  def f1: A => X
  def f2: Y => B
  def pxy: P[X,Y]
}
```

helper constructor:

```scala
def apply[XX,YY,P[_,_],A,B](ax: A => XX, yb: YY => B, p: P[XX,YY]): ProfunctorCoyoneda[P,A,B] = new ProfunctorCoyoneda[P,A,B] {
  type X = XX
  type Y = YY
  def f1: A => X = ax
  def f2: Y => B = yb
  def pxy: P[X,Y] = p
}
```

ProfunctorCoyoneda is a Profunctor for free:

```scala
def dimap[C, W](l: C => A, r: B => W): ProfunctorCoyoneda[P, C, W] =
  ProfunctorCoyoneda[X, Y, P, C, W](f1 compose l, r compose f2, pxy)
```

### Profunctor Ran

* Implementations: [Haskell](https://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Ran.html)

### Profunctor Codensity

* Implementations: [Haskell](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Ran.html#t:Codensity)

### Profunctor Adjunction

* Implementations: [Haskell](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Adjunction.html)

### Profunctor Rep

* TODO Corepresentable, Corep, Prep, Coprep

* Implementations: [Haskell](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Rep.html)

### Procompose

In general Profunctors should have straightforward way to compose them as we have the same category in definition.
But to be faithfull with Category Theory definition, Profunctor Composition is defined using exitential types:

```scala
trait Procompose[P[_,_],Q[_,_],D,C] {
  type X
  val p: P[X,C]
  val q: Q[D,X]
}
```

### ProductProfunctor

A generalization of Profunctor by adding Applicative superpowers to output.
Documentation of [tomjaguarpaw/product-profunctors on Hackage](http://hackage.haskell.org/package/product-profunctors/docs/Data-Profunctor-Product.html) is excelent.

```haskell
class Profunctor p => ProductProfunctor p where
  purePP :: b -> p a b
  (****) :: p a (b -> c) -> p a b -> p a c
  empty  :: p () ()
  (***!) :: p a b -> p a' b' -> p (a, a') (b, b')

(***$) :: ProductProfunctor p => (b -> c) -> p a b -> p a c
```

* utility methods
from `p0` to `p62`
```haskell
p3 :: forall p a0 a1 a2 b0 b1 b2. ProductProfunctor p => (p a0 b0, p a1 b1, p a2 b2) -> p (a0, a1, a2) (b0, b1, b2)
```

from to `pT1` to `pT62` 
```haskell
pT3 :: forall p a0 a1 a2 b0 b1 b2. ProductProfunctor p => T3 (p a0 b0) (p a1 b1) (p a2 b2) -> p (T3 a0 a1 a2) (T3 b0 b1 b2)
```

* Implementation: [tomjaguarpaw/product-profunctors](http://hackage.haskell.org/package/product-profunctors/docs/Data-Profunctor-Product.html)

### SumProfunctor

```haskell
class Profunctor p => SumProfunctor p where
  (+++!) :: p a b -> p a' b' -> p (Either a a') (Either b b')

list :: (ProductProfunctor p, SumProfunctor p) => p a b -> p [a] [b]
```

* Implementation: [tomjaguarpaw/product-profunctors](http://hackage.haskell.org/package/product-profunctors/docs/Data-Profunctor-Product.html#t:SumProfunctor)

### ProfunctorEnd

```haskell
class Profunctor p => ProfunctorEnd p where
  end :: p a a

instance ProfunctorEnd (->) where
  end = id
```

* (Haskell) [sjoerdvisscher/product-profunctors](https://github.com/sjoerdvisscher/product-profunctors/blob/master/Data/Profunctor/Product.hs#L171-L197)

## Arrows

![](img/arrows.svg)

### Category

Abstraction for operations that can be composed and that provide no-op (id).

```scala
trait Compose[F[_, _]] {
  def compose[A, B, C](f: F[B, C], g: F[A, B]): F[A, C] // alias <<<
}

trait Category[F[_, _]] extends Compose[F] {
  def id[A]: F[A, A]
}
```

* Implementations:  
  Compose/Semicategory [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Compose.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/semicategory.scala)  
  Category: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Category.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/category.scala) [Haskell](https://hackage.haskell.org/package/base/docs/Control-Category.html), [CubicalTT](https://github.com/mortberg/cubicaltt/blob/master/examples/category.ctt), [nlab](https://ncatlab.org/nlab/show/category)

* Category laws [Cats Category laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CategoryLaws.scala), [Cats Compose laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ComposeLaws.scala):
 * associativity `f.compose(g.compose(h)) == f.compose(g).compose(h)`
 * left `f.compose(id) == id.compose(f) == f`

* Resources
  * (Category Theory) Category Theory 1.2: What is a category? - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=i9CU4CuHADQ)
  * Hask is not a category - Andrej Bauer [(blog)](http://math.andrej.com/2016/08/06/hask-is-not-a-category/)

### Arrow

* Resources
  * Scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Arrow.scala) [examples](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/ArrowUsage.scala)
  * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Arrow.scala), [laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ArrowLaws.scala)
  * [traneio/arrows](https://github.com/traneio/arrows)
  * [Understanding arrows - Haskell wiki](https://en.wikibooks.org/wiki/Haskell/Understanding_arrows)
  * [When does one consider using Arrows? - reddit](https://www.reddit.com/r/haskell/comments/4fkkzo/when_does_one_consider_using_arrows/)
  * (Haskell) [base/Control-Arrow](http://hackage.haskell.org/package/base/docs/Control-Arrow.html)
  * (Haskell) The arrow calculus - Sam Lindley, Philip Wadler, and Jeremy Yalloop [(paper)](http://homepages.inf.ed.ac.uk/wadler/papers/arrows-jfp/arrows-jfp.pdf)
  * (Haskell) Idioms are oblivious, arrows are meticulous, monads are promiscuous - Sam Lindley, Philip Wadler [(paper)](http://homepages.inf.ed.ac.uk/wadler/papers/arrows-and-idioms/arrows-and-idioms.pdf)
  * [Tom Ellis: 24 Days of GHC Extensions: Arrows](https://ocharles.org.uk/guest-posts/2014-12-21-arrows.html)
  * (Category Theory) Bartosz Milewski - Arrows are strong profunctors [(video)](https://www.youtube.com/watch?v=hrNfkP8iKAs)
  * (Category Theory) What is a Categorical Model of Arrows? - Robert Atkey [(paper)](https://bentnib.org/arrows.pdf)
  * Learning Scalaz - Arrow - eed3si9n: [(blog post)](http://eed3si9n.com/learning-scalaz/Arrow.html)
  * (Haskell) FixxBuzz using arrows [(blog post)](http://logicaltypes.blogspot.com/2014/02/arrow-is-spelt-fizz-buzz.html)
  * (Haskell) Arrow's place in the Applicative/Monad hierarchy - Gergő Érdi [(blog post)](https://gergo.erdi.hu/blog/2014-07-12-arrow's_place_in_the_applicative_monad_hierarchy/)
  * Do it with (free?) arrows! – Julien Richard Foy [(video)](https://www.youtube.com/watch?v=PWBTOhMemxQ)
  * Functional programming with arrows [(video)](https://www.youtube.com/watch?v=ZfAgvAIoUEY)
  * (Haskell) 'Arrow' is spelt 'fizz-buzz' - @geophf [blog post](http://logicaltypes.blogspot.com/2014/02/arrow-is-spelt-fizz-buzz.html)

### CommutativeArrow

* Resources
  * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/CommutativeArrow.scala), [laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CommutativeArrowLaws.scala)

### Arrow Choice

* Resources
  * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/ArrowChoice.scala)
  * [channingwalton/typeclassopedia ArrowChoice](https://github.com/channingwalton/typeclassopedia/blob/master/src/main/scala/org/typeclassopedia/ArrowChoice.scala)
  * (Haskell) [Typeclassopedia ArrowChoice](https://wiki.haskell.org/Typeclassopedia#ArrowChoice)

### Arrow Apply, Arrow Monad

* Resources
  * [channingwalton/typeclassopedia ArrowApply](https://github.com/channingwalton/typeclassopedia/blob/master/src/main/scala/org/typeclassopedia/ArrowApply.scala)
  * (Haskell) [Typeclassopedia ArrowApply](https://wiki.haskell.org/Typeclassopedia#ArrowApply)
  * (Haskell) [base/Control-Arrow ArrowApply](https://hackage.haskell.org/package/base/docs/Control-Arrow.html#t:ArrowApply)
  * (Haskell) [base/Control-Arrow ArrowMonad](https://hackage.haskell.org/package/base/docs/Control-Arrow.html#t:ArrowMonad)

### Arrow Loop

* Resources
  * (Haskell) [base/Control-Arrow ArrowLoop](https://hackage.haskell.org/package/base/docs/Control-Arrow.html#t:ArrowLoop)
  * (Haskell) [Typeclassopedia ArrowLoop](https://wiki.haskell.org/Typeclassopedia#ArrowLoop)

### Arrow Zero

* Resources
  * [channingwalton/typeclassopedia ArrowZero](https://github.com/channingwalton/typeclassopedia/blob/master/src/main/scala/org/typeclassopedia/ArrowZero.scala)
  * (Haskell) [base/Control-Arrow ArrowZero](https://hackage.haskell.org/package/base/docs/Control-Arrow.html#t:ArrowZero)


### Free Arrow

### Kleisli

* Implementations:  [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kleisli.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/data/Kleisli.scala), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Monads/KleisliCategory.v)

* Resources
  * (C++) Category Theory 3.2: Kleisli category - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=i9CU4CuHADQ)
  * (Category Theory) Category Theory 4.1: Terminal and initial objects - Bartosz Milewski [(first 10 min of video)](https://www.youtube.com/watch?v=zer1aFgj4aU)
  * Cats [docs](http://typelevel.org/cats/datatypes/kleisli.html)
  * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/KleisliUsage.scala)
  * (Java) Thoughts on Kleisli Arrows in Java (WIP) - geophf [blog post](http://logicaltypes.blogspot.com/2013/06/thoughts-on-kleisli-arrows-in-java-wip.html)

### Cokleisli

* Cats
  * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/data/Cokleisli.scala)

### BiArrow

* Resources:
  * There and back again: arrows for invertible programming - Artem Alimarine , Sjaak Smetsers , Arjen Weelden , Marko Eekelen , Rinus Plasmeijer [(paper)](http://citeseerx.ist.psu.edu/viewdoc/summary?doi=10.1.1.153.9383)
  * (Haskell) [BiArrow](http://hackage.haskell.org/package/invertible/docs/Control-Invertible-BiArrow.html)

### BiKleisli

* Resources:
  * [Haskell category-extras](http://hackage.haskell.org/package/category-extras-0.53.3/docs/Control-Arrow-BiKleisli.html)
  * [everpeace/scala-bikleisli](https://github.com/everpeace/scala-bikleisli)
  * BiKleisli Arrow in Scala using Scalaz - everpeace [blog post](http://everpeace.github.io/scala-bikleisli/)


### Adjoint Triples

* Resources:
  * (Haskell) Adjoint Triples - Dan Doel [(blog post)](http://comonad.com/reader/2016/adjoint-triples/)
  * (Haskell) [adjunctions/Control.Comonad.Trans.Adjoint](http://hackage.haskell.org/package/adjunctions/docs/Control-Comonad-Trans-Adjoint.html#t:AdjointT)
  * [Adjoint functors and triples - Samuel Eilenberg and John C. Moore](https://projecteuclid.org/euclid.ijm/1256068141)
  * [Fancy Algebra (Graduate Topics Course) - Drew Armstrong](http://www.math.miami.edu/~armstrong/FA.php)
  * [nLab adjoint triple](https://ncatlab.org/nlab/show/adjoint+triple)

### Dinatural Transformation

Dinatural Transformation is a function that change one Profunctor P into another one Q without modifying the content.
It is equivalent to Natural Transformation between two Functors (but for Profunctors).

```scala
trait DinaturalTransformation[P[_,_],Q[_,_]]{
  def dinat[A](p: P[A,A]): Q[A,A]
}
```

* Laws:
  * `rmap f . dinat . lmap f == lmap f . dinat . rmap f`

* Resources
   * [Scalaz src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/NaturalTransformation.scala#L87-L89)
   * [nlab/Extranatural Transformation](https://ncatlab.org/nlab/show/extranatural+transformation)


## Limits

### Cone

* Resources
   * (Haskell) [category-extras Cone](https://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Functor-Cone.html#t:Cone)
   * (Haskell) [data-category Cone](http://hackage.haskell.org/package/data-category/docs/Data-Category-Limit.html#t:Cone)

### Cocone

* Resources
   * (Haskell) [category-extras Cocone](https://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Functor-Cone.html#t:Cocone)
   * (Haskell) [data-category Cocone](http://hackage.haskell.org/package/data-category-0.7/docs/Data-Category-Limit.html#t:Cocone)

### Diagonal Functor

* Resources
   * (Haskell) [category-extras Diagonal Functor](https://hackage.haskell.org/package/data-category/docs/Data-Category-Limit.html#2)

### Limit

* Resources
   * Category Theory II 1.2: Limits - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=sx8FELiIPg8)
   * Category Theory II 2.1: Limits, Higher order functors - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=9Qt664lfDRE)
   * Category Theory II 2.2: Limits, Naturality - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=1AOHbF6Ex8E)
   * Category Theory II 3.1: Examples of Limits and Colimits [(video)](https://www.youtube.com/watch?v=TtvVHokhSoM)
   * Limits and Colimits - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2015/04/15/limits-and-colimits/)
   * Understanding Limits - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2014/05/08/understanding-limits-2/)
   * TheCatsters - Limits and colimits [(video playlist)](https://www.youtube.com/playlist?list=PLE337D7DEA972E632)
   * (Haskell) [category-extras Limit](http://hackage.haskell.org/package/category-extras-0.53.4/docs/Control-Functor-Limit.html#t:Limit)
   * (Haskell) [data-category Limit](http://hackage.haskell.org/package/data-category/docs/Data-Category-Limit.html#g:4)

### Colimit

* Resources
   * (Haskell) [category-extras Colimit](http://hackage.haskell.org/package/category-extras-0.53.4/docs/Control-Functor-Limit.html#t:Colimit)
   * (Haskell) [data-category Colimit](http://hackage.haskell.org/package/data-category/docs/Data-Category-Limit.html#g:5)

### Ends & Coends

Ends can be seen as infinite product.
End corresponds to forall so polymorphic function:

```scala
// P is Profunctor

trait End[P[_,_]] {
  def run[A]: P[A,A]
}
```

Coend can be seen as infinite coproduct (sum).
Coends corresponds to exists

```Haskell
data Coend p = forall x. Coend p x x
```

* Resources
  * This is the (co)end, my only (co)friend - Fosco Loregian [(paper)](https://arxiv.org/tb/1501.02503)
  * (Haskell) [Dinatural Transformations and Coends - A Neighborhood of Infinity - Dan Piponi](http://blog.sigfpe.com/2009/03/dinatural-transformations-and-coends.html)
  * Category Theory III 6.2, Ends - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=TAPxt26YyEI)
  * Category Theory III 7.1, Natural transformations as ends - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=DseY4qIGZV4)
  * Category Theory III 7.2, Coends - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=jQUebw8uac0)
  * Ends - TheCatsters [(video playlist)](https://www.youtube.com/watch?v=mxI9ba6Rexc&list=PLg-WC4aaB3JuUXyuc337P8UYQSQVTjyP6)

### Comma categories, Slice category

* Resources
  * (Theory) Pointwise Kan Extensions - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2018/01/23/pointwise-kan-extensions/)
  * (Theory) Slice and comma categories 1 - TheCatsters [(video)](https://www.youtube.com/watch?v=f4jpvwwnq_s)
  * (Theory) Slice and comma categories 2 - TheCatsters [(video)](https://www.youtube.com/watch?v=W6sG5uraex0)
  * (Theory) Overcategories aka Comma Categories - MathProofsable [(video)](https://www.youtube.com/watch?v=WHwRghUl2Zo)
  * (Theory) Abstract and Concrete Categories The Joy of Cats - Jiri Adamek, Horst Herrlich, George E. Strecker [(book)](http://katmat.math.uni-bremen.de/acc/acc.pdf)
  * (Theory) Functorial Semantics of Algebraic Theories and Some Algebraic Problems in the context of Functorial Semantics of Algebraic Theories - F. William Lawvere - TAC Reprints [(book)](http://www.tac.mta.ca/tac/reprints/articles/5/tr5.pdf)
  * (Theory) Computational Category Theory - D.E. Rydeheard, R.M. Burstall [(book)](http://www.cs.man.ac.uk/~david/categories/book/book.pdf)
  * (Theory) [comma category - nLab](https://ncatlab.org/nlab/show/comma+category#functors_and_comma_categories)
  
### Align
  * [PR for Cats](https://github.com/typelevel/cats/pull/1755)
  * (Haskell) [these/Data.Align](https://hackage.haskell.org/package/these/docs/Data-Align.html)


## ADT (Algebra of types)

* Resources
  * [Haskell wiki ADT](https://wiki.haskell.org/Algebraic_data_type)
  * Simple Algebraic Data Types - Bartosz Milewski [blog post](https://bartoszmilewski.com/2015/01/13/simple-algebraic-data-types/)
  * Category Theory 5.2: Algebraic data types - Bartosz Milewski [video](https://www.youtube.com/watch?v=w1WMykh7AxA)
  * Counting type inhabitants - Alexander Konovalov [blog post](https://alexknvl.com/posts/counting-type-inhabitants.html)

### Unit

Type that has only one element

* Implementations [scala.Unit](https://www.scala-lang.org/api/2.11.12/#scala.Unit) [purescript-prelude/Data.Unit](https://pursuit.purescript.org/packages/purescript-prelude/docs/Data.Unit), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/limits/terminal.v), [nLab](https://ncatlab.org/nlab/show/terminal+object)

* Resources
  * Category Theory 4.1: Terminal and initial objects [video](https://www.youtube.com/watch?v=zer1aFgj4aU&feature=youtu.be&t=615) [Scala translation](https://github.com/typelevel/CT_from_Programmers.scala/blob/master/src/main/tut/2.2-limits-and-colimits.md)
  * (Category Theory) Limits and colimits - TheCatsters [video playlist](https://www.youtube.com/playlist?list=PLE337D7DEA972E632)

### Void

Type that has no elements.
In Category Theory - Initial Object

* Implementations [scala.Nothing](https://www.scala-lang.org/api/2.11.12/#scala.Nothing) [purescript-prelude/Data.Void](https://pursuit.purescript.org/packages/purescript-prelude/docs/Data.Void) [Idris prelude/Prelude/Uninhabited](https://github.com/idris-lang/Idris-dev/blob/master/libs/prelude/Prelude/Uninhabited.idr), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/limits/initial.v), [nLab](https://ncatlab.org/nlab/show/initial+object)

* Resources
  * Category Theory 4.1: Terminal and initial objects [video](https://www.youtube.com/watch?v=zer1aFgj4aU&feature=youtu.be&t=615) [Scala translation](https://github.com/typelevel/CT_from_Programmers.scala/blob/master/src/main/tut/2.2-limits-and-colimits.md)
  * (Category Theory) Limits and colimits - TheCatsters [video playlist](https://www.youtube.com/playlist?list=PLE337D7DEA972E632)

## Sum (Coproduct)

Type represents either one or another element.
In set theory: disjoint union in Category theory: coproduct (sum).

* Implementations [scala.util.Either](https://www.scala-lang.org/api/2.11.12/index.html#scala.util.Either) [purescript-either/Data.Either](https://pursuit.purescript.org/packages/purescript-either/docs/Data.Either), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/limits/coproducts.v), [nLab](https://ncatlab.org/nlab/show/coproduct)

## Product

Type represents combination of two types.
In Set theory cartesian product, in Category Theory product.

* Implementations [scala.Product2](https://www.scala-lang.org/api/2.11.12/index.html#scala.Product2) [scala.Tuple2](https://www.scala-lang.org/api/2.11.12/#scala.Tuple2) [purescript-tuples/Data.Tuple](https://pursuit.purescript.org/packages/purescript-tuples/docs/Data.Tuple), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/limits/products.v), [nLab](https://ncatlab.org/nlab/show/cartesian+product)

### These

Type that represents both sum and product (Non exclusive two values):

Tuple(a,b) => a * b
Eiter(a,b) => a + b
These(a,b) => (a + b) + a*b

```scala
sealed trait These[A,B]
case class This[A, B](a: A) extends These[A,B]
case class That[A,B](b: B) extends These[A,B]
case class Those[A,B](a: A, b: B) extends These[A,B]
```

* There is many abstractions that can be implemented for this data type

Implementations [Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/These.scala) [Haskell](https://hackage.haskell.org/package/these/docs/Data-These.html)

### Chronicle Monad

Implementations: [Cats MTL](https://github.com/typelevel/cats-mtl/blob/master/core/src/main/scala/cats/mtl/MonadChronicle.scala) [Haskell](https://hackage.haskell.org/package/these/docs/Control-Monad-Chronicle.html)  

Resources:
  * [Cats MTL docs](https://typelevel.org/cats-mtl/mtl-classes/monadchronicle.html)
  * (Haskell) These, Align, and Crosswalk - Tim Humphries [(blog post)](https://teh.id.au/posts/2017/03/29/these-align-crosswalk/index.html) [(reddit)](https://www.reddit.com/r/haskell/comments/634owv/these_align_and_crosswalk/)

### Unfoldable

Implementations:  
  * Unfoldable1: [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/unfoldable1.scala) [Purescript](https://pursuit.purescript.org/packages/purescript-unfoldable/docs/Data.Unfoldable1)  
  * Unfoldable: [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/unfoldable.scala) [Purescript](https://pursuit.purescript.org/packages/purescript-unfoldable/docs/Data.Unfoldable) [Haskell](http://hackage.haskell.org/package/unfoldable/docs/Data-Unfoldable.html)  
  * Biunfoldable: [Haskell](http://hackage.haskell.org/package/unfoldable/docs/Data-Biunfoldable.html)

Resources:
  * [PR for Cats](https://github.com/typelevel/cats/pull/1132)

### Andrey Mokhov Task

"computes the value of a key k, in a side-effect-free way, using a callback of type k → f v to find the values of its dependencies"

```Haskell
type Task c k v = forall f. c f => (k -> f v) -> k -> Maybe (f v)
```

Resources:
  * The Task abstraction - Andrey Mokhov [(blog post)](https://blogs.ncl.ac.uk/andreymokhov/the-task-abstraction/)
  * [usage in snowleopard/build](https://github.com/snowleopard/build/search?q=Task&unscoped_q=Task)

##  Cayley representations

"The Cayley representation theorem (CRT):
every group is isomorphic to a group of permutations"
Can be extended to monoids and defined monoidal category of endofunctor

In FP the CRT is optimisation by change of representation:
- CRT for List monoid - difference lists
- CRT for monads - Codensity monad
- CRT for applicatives - [NoCaM 5.4](http://www.fceia.unr.edu.ar/~mauro/pubs/Notions_of_Computation_as_Monoids_ext.pdf)
- CRT for arrows - ???

Resources:
  * Notions of Computation as Monoids (extended version) - Exequiel Rivas, Mauro Jaskelioff [(paper)](http://www.fceia.unr.edu.ar/~mauro/pubs/Notions_of_Computation_as_Monoids_ext.pdf)

### Difference lists

* List with concatenation and empty list is monoid. W optimize list concatenation (that is slow) by representing
list as function (difference list):

```scala
type Elist[A] = List[A] => List[A]
```

EList is isomorphic to List:

```scala
def rep[A](xs: List[A]): Elist[A] = ys => xs ++ ys
def abs[A](xs: Elist[A]): List[A] = xs(Nil)
```

We can concatenate EList's efficiently and at the end get to the List back.

* Cayley Theorem can be define for general Monoid:

```scala
trait CayleyTheoremForMonoid[M[_]] extends MonoidK[M] {
  type MonoidEndomorphism[A] = M[A] => M[A]
  def rep[A](xs: M[A]): MonoidEndomorphism[A] = ys => combineK(xs, ys)
  def abs[A](xs: MonoidEndomorphism[A]): M[A] = xs(empty)
}
```

Resources:
  * (Haskell) Using Difference Lists - geophf [blog post](http://logicaltypes.blogspot.com/2008/08/using-difference-lists.html)
  * (Haskell) keepEquals with Difference Lists - geophf [blog post](http://logicaltypes.blogspot.com/2014/06/keepequals-with-difference-lists.html)
  * (Haskell) A novel representation of lists and its application to the function "reverse" - John Hughes

### Double Cayley Representation

"optimises both left-nested sums and left-nested products"

Resources:
  * A Unified View of Monadic and Applicative Non-determinism - Exequiel Rivas, Mauro Jaskeliof, Tom Schrijvers [(paper)](https://www.fceia.unr.edu.ar/~mauro/pubs/UnifiedND.pdf)

### Transducers

Clojure transducers expressed using types.

```Haskell
type Reducer a r = r -> a -> r
type Transducer a b = forall r . Reducer a r -> Reducer b r
```

Resources:
  * (Haskell) Understanding Clojure transducers through types - Franklin Chen [(blog post)](https://conscientiousprogrammer.com/blog/2014/08/07/understanding-cloure-transducers-through-types/)
  * (Haskell) Transducers are monoid homomorphisms - Oleksandr Manzyuk [(blog post)](https://oleksandrmanzyuk.wordpress.com/2014/08/09/transducers-are-monoid-homomorphisms/)
  * (Haskell) Transducers, Mappers and Reducers - giuliohome [(blog post)](https://giuliohome.wordpress.com/2017/05/22/transducers-mappers-and-reducers/)

## Relative monads

Monads thats wokrs on different categories.

* Implementations: [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/Monads/RelativeMonads.v), [Agda](https://github.com/jmchapman/Relative-Monads) [nlab](https://ncatlab.org/nlab/show/relative+monad)

Resources:
  * (Type Theory) Monads Need Not Be Endofunctors - Thorsten Altenkirch, James Chapman, Tarmo Uustalu [(paper)](http://www.cs.nott.ac.uk/~psztxa/talks/scotcats-slides.pdf), [(code)](https://jmchapman.github.io/relmon.html)

## Commutative

For many standard abstractions we can add restriction to be commutative.

Implementations:
  Commutative Semigroup [ekmett/abelian](https://github.com/ekmett/abelian/blob/master/src/Data/Semigroup/Commutative.hs)  
  Commutative Monoid [ekmett/abelian](https://github.com/ekmett/abelian/blob/master/src/Data/Semigroup/Commutative.hs#L50-L51)  
  Commutative Apply [Cats src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CommutativeApply.scala) [Cats laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CommutativeApplyLaws.scala)   
  Commutative Applicative [Cats src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CommutativeApplicative.scala) [Cats laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CommutativeApplicativeLaws.scala) [ekmett/abelian](https://github.com/ekmett/abelian/blob/master/src/Control/Commutative.hs#L41)   
  Commutative FlatMap [Cats src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CommutativeFlatMap.scala) [Cats laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CommutativeFlatMapLaws.scala)   
  Commutative Monad [Cats src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CommutativeMonad.scala) [Cats laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CommutativeMonadLaws.scala)   
  Commutative Free Monads [ekmett/abelian](https://github.com/ekmett/abelian/blob/master/src/Control/Commutative/Monad/Free.hs)   
  Commutative Monad Transformers [ekmett/abelian](https://github.com/ekmett/abelian/blob/master/src/Control/Commutative/Monad/Trans.hs)   
  Commutative Comonads [ekmett/abelian](https://github.com/ekmett/abelian/blob/master/src/Control/Commutative.hs#L107-L109)

Resources:
  * (Haskell) Haskell Live-Coding, Session 1, Commutativity - Edward Kmett [(video)](https://www.youtube.com/watch?v=Nv5tf8pvgrY)
  * Are there any interesting commutative monads in Haskell? [SO](https://stackoverflow.com/questions/24356166/are-there-any-interesting-commutative-monads-in-haskell)
  * The Commutativity monad [(blog post)](http://gelisam.blogspot.com/2013/07/the-commutative-monad.html) [reddit](https://www.reddit.com/r/haskell/comments/1jegxb/the_commutative_monad/)

## Topos

Resources:
  * vpatryshev/Categories [Topos](https://github.com/vpatryshev/Categories/blob/master/src/main/scala/math/cat/topos/Topos.scala) [Grothendieck Topos, Subobject Classifier](https://github.com/vpatryshev/Categories/blob/master/src/main/scala/math/cat/topos/GrothendieckTopos.scala)
  * (Agda) agda/agda-categories [Topos](https://github.com/agda/agda-categories/blob/master/Categories/Category/Topos.agda) [Subobject Classifier](https://github.com/agda/agda-categories/blob/master/Categories/Diagram/SubobjectClassifier.agda)
  * (Coq) UniMath/UniMath [Grothendieck Topos](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/GrothendieckTopos.v) [Subobject Classifier](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/SubobjectClassifier.v)
  * (Haskell) [brunjlar/protop](https://github.com/brunjlar/protop)
  * (Category Theory, Haskell) Topoi - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/07/22/topoi/)
  * Computational Quadrinitarianism (Curious Correspondences go Cubical) - Gershom Bazerman [(blog post)](http://comonad.com/reader/2018/computational-quadrinitarianism-curious-correspondences-go-cubical/)
  * [fdilke/bewl](https://github.com/fdilke/bewl) (A DSL for the internal language of a topos)
  * [resources about topos theory](./ComputationalTrinitarianism.MD#topos-theory)

## Resource About Category Theory

* Resources covering topics about FP and category theory in great details:
  * [Functional Programming in Scala - Paul Chiusano and Rúnar Bjarnason](https://www.manning.com/books/functional-programming-in-scala) Best book about FP in Scala. I have bought it for myself and higly recommend it. Worth reading, doing exercises and re-reading.
  * [Functional Structures in Scala - Michael Pilquist](https://www.youtube.com/watch?v=Dsd4pc99FSY&list=PLFrwDVdSrYE6dy14XCmUtRAJuhCxuzJp0): workshop on [implementating FP constructions](https://github.com/mpilquist/Structures) with usage examples and great insights about Scala and FP.
  * [Applied functional type theory - Sergei Winitzki](https://www.youtube.com/watch?v=0Ld79Lnzx_o&list=PLcoadSpY7rHXJWbUkjQ3P9MXBbXxLP8kV)
  * Series of blog posts by Eugene Yokota (@eed3si9n): [herding cats](http://eed3si9n.com/herding-cats/) and [learning Scalaz](http://eed3si9n.com/learning-scalaz/) Easy to understand examples, clear explanations, many insights from Haskell papers and literature.
  * [Examples in scalaz repository](https://github.com/scalaz/scalaz/tree/series/7.3.x/example/src/main/scala/scalaz/example) Learning Scalaz is probably the best documentation for Scalaz.
  * [Documentation for Cats](https://typelevel.org/cats/) (runnable online version for older Cats version on [ScalaExercises](https://www.scala-exercises.org/cats))
  * [channingwalton/typeclassopedia](https://github.com/channingwalton/typeclassopedia) implementation of Haskell Typeclassopedia by Channing Walton, [(blog post)](http://channingwalton.github.io/typeclassopedia/)
  * Scala Type-class Hierarchy - Tony Morris [(blog post)](http://blog.tmorris.net/posts/scala-type-class-hierarchy/index.html) (traits for all cathegory theory constructions with exotic ones like `ComonadHoist`)
  * [Summary of types in Cats documentation](https://typelevel.org/cats/nomenclature.html)
  * (Haskell) [Typeclassopedia](https://wiki.haskell.org/Typeclassopedia)
  * (Kotlin) [Patterns from Category Theory in Kotlin](https://arrow-kt.io/docs/)


## Functor Oriented Programming

Resources:
  * Functor-Oriented Programming - Russell O’Connor [blog post](http://r6.ca/blog/20171010T001746Z.html) [hacker news](https://news.ycombinator.com/item?id=15440108) [reddit](https://www.reddit.com/r/haskell/comments/75fo8k/functor_oriented_programming/)

## Abstractions for abstractions

  * (Haskell) Functor-Of - Vladimir Ciobanu [(blog post)](https://cvlad.info/functor-of/) 
