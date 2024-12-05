[![Scala CI](https://github.com/lemastero/scala_typeclassopedia/actions/workflows/scala.yml/badge.svg?branch=main)](https://github.com/lemastero/scala_typeclassopedia/actions/workflows/scala.yml?query=branch%3Amain)
[![Scala Steward badge](https://img.shields.io/badge/Scala_Steward-helping-brightgreen.svg?style=flat&logo=data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAA4AAAAQCAMAAAARSr4IAAAAVFBMVEUAAACHjojlOy5NWlrKzcYRKjGFjIbp293YycuLa3pYY2LSqql4f3pCUFTgSjNodYRmcXUsPD/NTTbjRS+2jomhgnzNc223cGvZS0HaSD0XLjbaSjElhIr+AAAAAXRSTlMAQObYZgAAAHlJREFUCNdNyosOwyAIhWHAQS1Vt7a77/3fcxxdmv0xwmckutAR1nkm4ggbyEcg/wWmlGLDAA3oL50xi6fk5ffZ3E2E3QfZDCcCN2YtbEWZt+Drc6u6rlqv7Uk0LdKqqr5rk2UCRXOk0vmQKGfc94nOJyQjouF9H/wCc9gECEYfONoAAAAASUVORK5CYII=)](https://github.com/scala-steward-org/scala-steward)

# Scala typeclassopedia

```mermaid
classDiagram
   Functor~F~ <|-- Apply~F~
   Apply <|-- FlatMap~F~
   Functor <|-- Traverse~F~
   Foldable~F~ <|-- Traverse
   FlatMap~F~ <|-- Monad~F~
   Apply~F~ <|-- Applicative~F~
   Apply <|-- CoflatMap~F~
   CoflatMap <|-- Comonad~F~
   Applicative <|-- Selective~F~
   Selective <|-- Monad
   Applicative <|-- Alternative~F~
   MonoidK~F~ <|-- Alternative
   Applicative <|-- ApplicativeError~F~
   ApplicativeError <|-- MonadError~F~
   Monad <|-- MonadError
   Monad <|-- Bimonad~F~
   Comonad <|-- Bimonad

   class Functor {
     ) map(F[A], A => B): F[B]
   }
   class Foldable {
     ) foldLeft(F[A], B, Tuple2[B,A] => B): B
   }
   class Traverse {
     ) traverse(F[A], A => G[B]): G[F[B]]
   }
   class Apply {
     ) ap(F[A], F[A => B]): F[B]
     ) map2(Tuple2[A,B] => C, F[A], F[B]): F[C]
   }
   class Applicative {
     ) pure(A): F[A]
   }
   class Selective {
     ) select(F[Either[A,B]], F[A=>B]): F[B]
   }
   class FlatMap {
     ) flatmap(F[A], A => F[B]): F[B]
   }
   class Monad {
     ) flatten(F[F[A]]): F[A]
   }
   class ApplicativeError {
     ) raiseError(E): F[A]
   }
   class CoflatMap {
     ) extend(F[A], F[A] => B): F[B]
   }
   class Comonad {
     ) extract(W[A]): A
   }
   class MonoidK {
     ) empty(): F[A]
     ) combine(F[A], F[A]): F[A]
   }
   class Alternative {
     ) some(F[A]): F[NonEmptyList[A]]
     ) many(F[A]): F[List[A]]
   }
```

* Base abstractions: [Functor](./BasicAbstractions.md#functor-covariant-functor), [Apply](./BasicAbstractions.md#apply), [Applicative](./BasicAbstractions.md#applicative-applicative-functor), [Monad](./BasicAbstractions.md#monad), [Contravariant](./Contravariant.md#contravariant-contravariant-functor), [Comonad](./Comonads.md#comonad), [Foldable](./BasicAbstractions.md#foldable), [Bifunctor](./Bifunctors.md#bifunctor), [Arrow](./Profunctors.md#arrow), [Coyoneda](./KanExtensions.md#coyoneda)

* Covariant Functors: [Functor](./BasicAbstractions.md#functor-covariant-functor), [Apply](./BasicAbstractions.md#apply), [Applicative](./BasicAbstractions.md#applicative-applicative-functor), [Selective](./BasicAbstractions.md#selective-selective-applicative-functors)

* [Monads](./BasicAbstractions.md#monad): [Reader](./BasicAbstractions.md#reader), [Writer](./BasicAbstractions.md#writer), [State](./BasicAbstractions.md#state), [RWS Monad](./BasicAbstractions.md#rws-monad), [Update Monad](./BasicAbstractions.md#update-monad), [Logic Monad, Prompt Monad, Failure Monad](./BasicAbstractions.md#logic-monad-prompt-monad-failure-monad), [ContT (Continuation Monad)](./BasicAbstractions.md#contt-continuation-monad), [Reverse State Monad](./BasicAbstractions.md#reverse-state-monad), [Tardis (Bidirectional State Monad)](./BasicAbstractions.md#tardis-bidirectional-state-monad), [Chronicle Monad](./BasicAbstractions.md#chronicle-monad), [Bimonad](./BasicAbstractions.md#bimonad), [Dijkstra monad](./BasicAbstractions.md#dijkstra-monad), [Hoare Monad](./BasicAbstractions.md#hoare-monad)

* Monads generalizations: [Indexed Monads](./BasicAbstractions.md#indexed-monads), [SuperMonads](./BasicAbstractions.md#supermonads)

* [IO related monads](./BasicAbstractions.md#io-related-monads): [IO](./BasicAbstractions.md#io-monad), [Bifunctor IO (BIO)](./BasicAbstractions.md#bifunctor-io-bio), [RIO Monad (Reader + IO)](./BasicAbstractions.md#rio-monad-reader--io), [TRIO (RIO Monad + Bifunctor IO)](./BasicAbstractions.md#trio-rio-monad--bifunctor-io)

* Contravariant functors: [Contravariant](./Contravariant.md#contravariant-contravariant-functor), [Divide (Contravariant Apply)](./Contravariant.md#divide-contravariant-apply), [Divisible (Contravariant Applicative)](./Contravariant.md#divisible-contravariant-applicative)

* Contravariant Adjuctions & Representable: [Contravariant Adjunction](./Contravariant.md#contravariant-adjunction), [Contravariant Rep](./Contravariant.md#contravariant-rep)

* [Contravariant Kan Extensions](./Contravariant.md#contravariant-kan-extensions): [Contravariant Yoneda](./Contravariant.md#contravariant-yoneda), [Contravariant Coyoneda](./Contravariant.md#contravariant-coyoneda), [Contravariant Day](./Contravariant.md#contravariant-day), [Invariant Day](./Contravariant.md#invariant-day)

* Invariant Functors: [Invariant (Invariant Functor, Exponential Functor)](./BasicAbstractions.md#invariant-invariant-functor-exponential-functor), [Invariant Day](./HigherKinded.md#invariant-day)

```mermaid
classDiagram
   Bifoldable~P[+_,+_]~ <|-- Bitraverse~P[+_,+_]~
   Bifunctor~P[+_,+_]~ <|-- Bitraverse
   Bifunctor <|-- Biapply~P[+_,+_]~
   Biapply <|-- Biapplicative~P[+_,+_]~
   Functor~F[+_]~ <|-- Bifunctor
   Functor <|-- Bifunctor
   Functor <|-- Profunctor~P[-_,+_]~
   Bifunctor <|-- Zivariant~Z[-_,+_,+_]~
   Profunctor <|-- Zivariant

  class Functor {
    ) map(F[A], A => B): F[B]
  }
  class Profunctor {
    ) dimap(AA => A, B => BB): P[A,B] => P[AA,BB]
  }
  class Bifunctor {
    ) bimap(A => AA, B => BB): P[A,B] => P[AA,BB]
  }
  class Bifoldable {
    ) bifoldLeft(F[A,B], C, (C,A) => C, (C,B) => C): C
  }
  class Bitraverse {
    ) bitraverse[G: Applicative](F[A,B], A=>G[C], B => G[D]): G[F[C,D]]
  }
  class Biapply {
    ) biApply(F[A,B], F[A=>AA,B=>BB]): F[AA,BB]
  }
  class Biapplicative {
    ) bipure(a: A, b: B): F[A,B]
  }
  class Zivariant {
    ) zimap(AA => A, B => BB, C => CC): P[A,B,C] => P[AA,BB,CC]
  }
```

* Bifunctors: [Bifunctor](./Bifunctors.md#bifunctor), [Join](./Bifunctors.md#bifunctor-join), [Wrap](./Bifunctors.md#bifunctor-wrap), [Flip](./Bifunctors.md#bifunctor-flip), [Joker](./Bifunctors.md#bifunctor-joker), [Clown](./Bifunctors.md#bifunctor-clown), [Product](./Bifunctors.md#bifunctor-product), [Bifunctor Sum](./Bifunctors.md#bifunctor-sum), [Bifunctor Tannen](./Bifunctors.md#bifunctor-tannen), [Bifunctor Biff](./Bifunctors.md#bifunctor-biff), [Bitraverse](./Bifunctors.md#bitraverse), [Bifoldable](./Bifunctors.md#bifoldable),

* Comonads: [Comonad](./Comonads.md#comonad), [Coreader (Env comonad, Product comonad)](./Comonads.md#coreader-env-comonad-product-comonad), [Cowriter](./Comonads.md#cowriter), [Cofree](./Free.md#cofree), [Cokleisli](./Comonads.md#cokleisli), [Bimonad](./Comonads.md#bimonad)

* Traversing Folding Filtering: [Monoid](./AbstractAlgebra.md#monoid), [Foldable](./BasicAbstractions.md#foldable), [Traverse](./BasicAbstractions.md#traverse), [Bitraverse](./BasicAbstractions.md#bitraverse), [Bifoldable](./BasicAbstractions.md#bifoldable), [FunctorFilter](./BasicAbstractions.md#functorfilter), [TraverseFilter](./BasicAbstractions.md#traversefilter), [Distributive](./BasicAbstractions.md#distributive), [Cofree Traverse](./Free.md#cofree-traverse)

* Monads not compose - solutions: [Monad Transformers](./BasicAbstractions.md#monad-transformers-optiont-eithert-readert), [Free Monads](./Free.md#free-monads), Tagless Final, [Extensible effects](./BasicAbstractions.md#extensible-effects)

* [Free constructions](./Free.md#free-constructions), [Free Applicative](./Free.md#free-applicative), [Free Monads](./Free.md#free-monads), [Cofree](./Free.md#cofree), [Free Alternative](./Free.md#free-alternative), [Free Arrow](./Free.md#free-arrow), [Free Monad transformers](./Free.md#free-monad-transformers), [Cofree Traverse](./Free.md#cofree-traverse)
 
* [Representable & Adjunctions](./Adjunction.md#representable--adjunctions): [Representable](./Adjunction.md#representable), [Corepresentable](./Adjunction.md#corepresentable), [Adjunction](./Adjunction.md#adjunction), [Adjoint Triples](./Adjunction.md#adjoint-triples)

```mermaid
classDiagram
   Ran~G[_], H[_], A~ <|-- Yoneda~H[_], A~
   Lan~G[_], H[_], A~ <|-- CoYoneda~H[_], A~
   Ran <|-- Codensity~G[_], A~
   Lan <|-- Density~G[_], A~

  class Ran {
    // Right Kan Extension
    ) run[B](A => G[B]): H[B]
  }
  class Yoneda {
    ) run[B](A => B): H[R]
  }
  class Codensity {
    ) run[B](A => G[B]): G[B]
  }
  class Lan {
    // Left Kan Extension
    fz: H[Z]
    run: G[Z] => A
  }
  class CoYoneda {
    fz: H[Z]
    run: Z => A
  }
  class Density {
    fz: G[Z]
    run: G[Z] => A
  }
  class Day~G[_], H[_], A~ {
    // Day convolution
    gb: G[Z]
    hb: H[X]
    ) run: (Z,X) => A
  }
```

* [(Co)Yoneda & (Co)Density & Kan Extensions](./KanExtensions.md#coyoneda--codensity--kan-extensions), [Yoneda](./KanExtensions.md#yoneda), [Coyoneda](./KanExtensions.md#coyoneda), [Right Kan extension](./KanExtensions.md#right-kan-extension), [Left Kan Extension](./KanExtensions.md#left-kan-extension), [Density Comonad](./KanExtensions.md#density-comonad), [Codensity](./KanExtensions.md#codensity), [Day Convolution](./KanExtensions.md#day-convolution)

* Profunctors: [Profunctor](./Profunctors.md#profunctor), [Star](./Profunctors.md#star), [CoStar](./Profunctors.md#costar), [Strong Profunctor](./Profunctors.md#strong-profunctor), [Tambara](./Profunctors.md#tambara), [Choice Profunctor](./Profunctors.md#choice-profunctor), [Extranatural Transformation](./Profunctors.md#extranatural-transformation), [Profunctor Functor](./Profunctors.md#profunctor-functor), [Profunctor Monad](./Profunctors.md#profunctor-monad), [Profunctor Comonad](./Profunctors.md#profunctor-comonad), [Procompose](./Profunctors.md#procompose),  [ProductProfunctor](./Profunctors.md#roductprofunctor), [SumProfunctor](./Profunctors.md#sumprofunctor)

* Profunctor Adjuctions & Representable: [Profunctor Adjunction](./Profunctors.md#profunctor-adjunction), [Profunctor Rep](./Profunctors.md#profunctor-rep)

* Profunctor Kan Extensions: [Profunctor Yoneda](./Profunctors.md#profunctor-yoneda), [Profunctor CoYoneda](./Profunctors.md#profunctor-coyoneda), [Profunctor Ran](./Profunctors.md#profunctor-ran), [Profunctor Codensity](./Profunctors.md#profunctor-codensity)

```mermaid
classDiagram
   Functor~F[+_]~ <|-- Bifunctor~F[+_,+_]~
   Functor <|-- Bifunctor
   Functor <|-- Profunctor~F[-_,+_]~
   Contravariant~F[-_]~ <|-- Profunctor
   Semicategory~F[-_,+_]~ <|-- Category~F[-_,+_]~
   Category <|-- Arrow~F[-_,+_]~
   Bifunctor <|-- Zivariant~F[-_,+_,+_]~
   Profunctor <|-- Zivariant
   Profunctor <|-- Strong~F[-_,+_]~
   Strong -- Arrow
   Arrow <|-- ArrowApply~F[-_,+_]~
   Arrow <|-- CommutativeArrow~F[-_,+_]~
   Arrow <|-- ArrowLoop~F[-_,+_]~
   Profunctor <|-- Choice~F[-_,+_]~
   Arrow <|-- ArrowZero~F[-_,+_]~
   Arrow <|-- ArrowChoice~F[-_,+_]~
   Choice <|-- ArrowChoice

   class Functor {
     ) map(F[A], A => B): F[B]
   }
   class Contravariant {
     ) contramap(F[A], B => A): F[B]
   }
   class Semicategory {
     ) compose[A,B,C](F[B,C], F[A,B]): F[A,C]
   }
  class Category {
    ) id[A]: F[A,A]
  }
  class Profunctor {
    ) dimap(AA => A, B => BB): P[A,B] => P[AA,BB]
  }
  class Bifunctor {
    ) bimap(A => AA, B => BB): P[A,B] => P[AA,BB]
  }
  class Zivariant {
    ) zimap(AA => A, B => BB, C => CC): P[A,B,C] => P[AA,BB,CC]
  }
  class Strong {
    ) first(P[A,B]): P[(A,C), (B,C)]
  }
  class Choice {
    ) left(P[A,B]): P[Either[A, C], Either[B, C]]
  }
  class Arrow {
    ) arr(A => B): F[A, B]
  }
  class ArrowZero {
    ) zeroArr(): P[A,B]
  }
  class ArrowApply {
    ) app(P[P[B,C],B]): C
  }
  class ArrowApply {
    ) app(P[P[B,C],B]): C
  }
  class ArrowLoop {
    ) loop(P[(B,D), (C,D)]: P[B,C]
  }
```

* [Arrows](./Profunctors.md#arrows): [Category](./Profunctors.md#category), [Arrow](./Profunctors.md#arrow), [Commutative Arrow](./Profunctors.md#commutativearrow), [Arrow Choice](./Profunctors.md#arrow-choice), [Arrow Apply, Arrow Monad](./Profunctors.md#arrow-apply-arrow-monad), [Arrow Loop](./Profunctors.md#arrow-loop), [Arrow Zero](./Profunctors.md#arrow-zero), [Free Arrow](./Free.md#free-arrow), [Kleisli](./Profunctors.md#kleisli), [Cokleisli](./Profunctors.md#cokleisli), [BiArrow](./Profunctors.md#biarrow), [BiKleisli](./Profunctors.md#bikleisli)

* [Cayley representations](./BasicAbstractions.md#cayley-representations): [Difference Lists](./BasicAbstractions.md#difference-lists), [Codensity](./KanExtensions.md#codensity), [Double Cayley Representation](./BasicAbstractions.md#double-cayley-representation)

* [Curry-Howard Isomorphism](./Limits.md#adt-algebra-of-types): [These](./Limits.md#These)

| Types         | Logic                | Category Theory       | Homotopy Theory   |
|---------------|----------------------|-----------------------|-------------------|
| [Void](./Limits.md#void)          | false                | initial object        | empty space       |
| [Unit](./Limits.md#unit)          | true                 | terminal object       | singleton         |
| [Sum (Coproduct)](./Limits.md#sum-coproduct) Eiter[A,B]    | A v B disjunction    | coproduct             | coproduct space   |
| [Product](./Limits.md#product) (A,B)          | A ∧ B conjunction    | product               | product space     |
| A => B        | A => B implication   | exponential object    | singleton         |
| A => Void     | negation             | exp. obj. into initial obj. |            |

* Higher kinded & exotic abstractions: [Natural transformation (FunctionK)](./HigherKinded.md#natural-transformation-functionk), [Monoidal Category, Monoid Object](./HigherKinded.md#monoidal-categories-monoid-object), [Cartesian Closed Category](./HigherKinded.md#cartesian-closed-category), [Day Convolution](./HigherKinded.md#day-convolution), [Functor Functor (FFunctor)](./HigherKinded.md#functor-functor-ffunctor), [Monad morphisms](./HigherKinded.md#monad-morphisms), [higher kinded category theory](./HigherKinded.md#higher-kinded-category-theory), [SemigroupK (Plus)](./BasicAbstractions.md#semigroupk-plus), [MonoidK (PlusEmpty)](./BasicAbstractions.md#monoidk-plusempty), [Dinatural Transformation](./Profunctors.md#dinatural-transformation), [Ends & Coends](./Profunctors.md#ends--coends), [Align](./BasicAbstractions.md#align), [Task](./BasicAbstractions.md#andrey-mokhov-task), [Transducers](./BasicAbstractions.md#transducers), [Relative monads](./BasicAbstractions.md#relative-monads), [Disintegrate](./BasicAbstractions.md#disintegrate)

* Limits: [Cone](./Limits.md#cone), [Cocone](./Limits.md#cocone), [Diagonal Functor](./Limits.md#diagonal-functor), [Limit](./Limits.md#limit), [Colimit](./Limits.md#colimit), [Ends & Coends](./Profunctors#ends--coends)

* Topoi: Subobject classifier, [Topos](./Topos.md#topos)

* [Other Encodings of Category Theory](./OtherEncodingsOfCT.md): [data-category by Sjoerd Visscher](./OtherEncodingsOfCT.md#encoding-of-category-theory-by-sjoerd-visscher), [Formalizations of Category Theory in proof assistants (Coq)](./OtherEncodingsOfCT.md#formalizations-of-category-theory-in-proof-assistants)

* [Recursion schemas](RecursionSchemas.md)

* [Optics](./Optics.md)

* [Functor Oriented Programming](/BasicAbstractions.md#functor-oriented-programming)

## Resources covering topics about FP and category theory in great details:

* ["Red Book" - Functional Programming in Scala - Paul Chiusano and Rúnar Bjarnason](https://www.manning.com/books/functional-programming-in-scala) Best book about FP in Scala. I have bought it for myself and higly recommend it. Worth reading, doing exercises and re-reading.
* [Category Theory for Programmers - Bartosz Milewski](https://www.blurb.com/b/9621951-category-theory-for-programmers-new-edition-hardco) the best book about this subject. [Blog posts](https://bartoszmilewski.com/2014/10/28/category-theory-for-programmers-the-preface/) and video lectures [Part 1](https://www.youtube.com/playlist?list=PLbgaMIhjbmEnaH_LTkxLI7FMa2HsnawM_), [Part II](https://www.youtube.com/playlist?list=PLbgaMIhjbmElia1eCEZNvsVscFef9m0dm), [Part III](https://www.youtube.com/playlist?list=PLbgaMIhjbmElia1eCEZNvsVscFef9m0dm)
* (Haskell) [Typeclassopedia](https://wiki.haskell.org/Typeclassopedia) wiki in Haskell about abstractions from category theory used by Haskell programmers - excellent resource.
* Agda formalizations: [1Lab](https://1lab.dev/#category-theory), [agda-unimath](https://github.com/UniMath/agda-unimath/tree/master/src/category-theory), [agda/cubical](https://github.com/agda/cubical/tree/master/Cubical/Categories), [agda/agda-categories](https://github.com/agda/agda-categories)
* [Seven Sketches in Compositionality: An Invitation to Applied Category Theory - Brendan Fong, David I Spivak]() book about Applied Category Theory branch of mathematics that use mathematics from category theory and apply it in different subjects like engineering. The [video lectures](https://www.youtube.com/playlist?list=PLhgq-BqyZ7i5lOqOqqRiS0U5SwTmPpHQ5) based on the book by the authors.
* (Haskell) Haskell libraries containing abstractions from category theory written by Edward Kmett:
  [Profunctors](https://hackage.haskell.org/package/profunctors/docs/Data-Profunctor.html), [Bifunctors](https://hackage.haskell.org/package/bifunctors), [Comonad](https://hackage.haskell.org/package/comonad), [free](https://hackage.haskell.org/package/free), [adjunctions](https://hackage.haskell.org/package/adjunctions), [Kan extensions](https://hackage.haskell.org/package/kan-extensions), [invariant](https://hackage.haskell.org/package/invariant), [distributive](https://hackage.haskell.org/package/distributive), [transformers](https://hackage.haskell.org/package/transformers), [semigroupoids](https://hackage.haskell.org/package/semigroupoids). This is how all of this was started :) Some of them were already moved into Haskell standard library e.g. [Data.Functor.Contravariant](https://hackage.haskell.org/package/base/docs/Data-Functor-Contravariant.html)
* [zio-prelude](https://github.com/zio/zio-prelude) modern look at abstractions from category theory, more modular and expressive
* (Idris) [statebox/idris-ct](https://github.com/statebox/idris-ct) encoding of abstractions & formal verification in Idris 2
* Functional Structures in Scala - Michael Pilquist [(video playlist)](https://www.youtube.com/watch?v=Dsd4pc99FSY&list=PLFrwDVdSrYE6dy14XCmUtRAJuhCxuzJp0): workshop on [implementating FP constructions](https://github.com/mpilquist/Structures) with usage examples and great insights about Scala and FP.
* Applied functional type theory - Sergei Winitzki [(video playlist)](https://www.youtube.com/watch?v=0Ld79Lnzx_o&list=PLcoadSpY7rHXJWbUkjQ3P9MXBbXxLP8kV)
* Series of blog posts by Eugene Yokota (@eed3si9n): [herding cats](http://eed3si9n.com/herding-cats/) and [learning Scalaz](http://eed3si9n.com/learning-scalaz/) Easy to understand examples, clear explanations, many insights from Haskell papers and literature.
* [Examples in scalaz repository](https://github.com/scalaz/scalaz/tree/series/7.3.x/example/src/main/scala/scalaz/example) Learning Scalaz is probably the best documentation for Scalaz.
* [Documentation for Cats](https://typelevel.org/cats/) (runnable online version for older Cats version on [ScalaExercises](https://www.scala-exercises.org/cats)), [summary](https://typelevel.org/cats/nomenclature.html)
* [channingwalton/typeclassopedia](https://github.com/channingwalton/typeclassopedia) implementation of Haskell Typeclassopedia by Channing Walton, [(blog post)](http://channingwalton.github.io/typeclassopedia/)
* Notes on Category Theory in Scala 3 (Dotty) - Juan Pablo Romero Méndez [(blog post)](https://typista.org/categories-in-dotty/#1-categories)
* Scala Type-class Hierarchy - Tony Morris [(blog post)](http://blog.tmorris.net/posts/scala-type-class-hierarchy/index.html) (traits for all cathegory theory constructions with exotic ones like `ComonadHoist`)

[Computational trinitarianism resources](ComputationalTrinitarianism.md)
