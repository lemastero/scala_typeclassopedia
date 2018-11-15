# Scala typeclassopedia

* Resources covering topics about FP and category theory in great details and very approachable form:
  * [Functional Programming in Scala - Paul Chiusano and Rúnar Bjarnason](https://www.manning.com/books/functional-programming-in-scala) Best book about FP in Scala. I have bought it for myself and higly recommend it. Worth reading, doing exercises and re-reading.
  * [Functional Structures in Scala - Michael Pilquist](https://www.youtube.com/watch?v=Dsd4pc99FSY&list=PLFrwDVdSrYE6dy14XCmUtRAJuhCxuzJp0): workshop on [implementating FP constructions](https://github.com/mpilquist/Structures) with usage examples and great insights about Scala and FP.
  * Series of blog posts by Eugene Yokota (@eed3si9n): [herding cats](http://eed3si9n.com/herding-cats/) and [learning Scalaz](http://eed3si9n.com/learning-scalaz/) Easy to understand examples, clear explanations, many insights from Haskell papers and literature.
  * [Examples in scalaz repository](https://github.com/scalaz/scalaz/tree/series/7.3.x/example/src/main/scala/scalaz/example) Learning Scalaz is probably the best documentation for Scalaz.
  * [Documentation for Cats](https://typelevel.org/cats/) (runnable online version for older Cats version on [ScalaExercises](https://www.scala-exercises.org/cats/semigroup))
  * (Haskell) [Typeclassopedia](https://wiki.haskell.org/Typeclassopedia)
  * [channingwalton/typeclassopedia](https://github.com/channingwalton/typeclassopedia) another implementation of FP constructions
  * Scala Type-class Hierarchy - Tony Morris [(blog post)](http://blog.tmorris.net/posts/scala-type-class-hierarchy/index.html) (traits for all cathegory theory constructions with exotic ones like `ComonadHoist`)
  * [Patterns from Category Theory in Kotlin](https://arrow-kt.io/docs/)

## [Abstract Algebra](./AbstractAlgebra.MD)

## Category Theory

![](covariant_contravariant_functors.svg)

### Functor (Covariant Functor)

Abstraction for type constructor (type with "hole", type parameter) that can be mapped over.

Containers (List, Tree, Option) can apply given function to every element in the collection.
Computation effects (Option - may not have value, List - may have multiple values,
 Either/Validated - may contain value or error) can apply function to a value inside this effect without changing the effect.
```scala
trait Functor[F[_]] {
  def map[A,B](a: F[A])(f: A => B): F[B]
}
```
* Functor Laws:
1. identify: `xs.map(identity) == xs`
2. composition: `xs.map(f).map(g) == xs.map(x => g(f(x))`

If Functor satisfy fist law then it also satisfy second law: [(Haskell) The second Functor law is redundant - David Luposchainsky](https://github.com/quchen/articles/blob/master/second_functor_law.md)
if we don't include bottom values - [(Haskell) contrexample using undefined](https://stackoverflow.com/questions/8305949/haskell-functor-implied-law/8323243#8323243).

* Instances can be implemented for: List, Vecotr, Option, Either, Validated, Tuple1, Tuple2, Function

* Functor must preserve structure, so Set is not a Functor (map constant function would change the structure).

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

* Functors can be composed

* Resources:
  * herding cats - Functor: [blog post](http://eed3si9n.com/herding-cats/Functor.html)
  * FSiS 1, Type Constructors, Functors, and Kind Projector - Michael Pilquist [video](https://www.youtube.com/watch?v=Dsd4pc99FSY)
  * (Haskell) The Extended Functor Family - George Wilson [video](https://www.youtube.com/watch?v=JUVMiRRq6wU)
  * Cats [docs](https://typelevel.org/cats/typeclasses/functor.html) [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Functor.scala)
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Functor.scala)

* Examples for [instances for built in types](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/InstancesForBuiltInTypes.scala),
[function1](https://www.youtube.com/watch?v=Dsd4pc99FSY&t=1075),
and [custom Tree type](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/TreeFunctor.scala).
Examples for [usage of map, derived methods, compose](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/functor/FunctorExamplesSpec.scala).

### Apply

Apply is a Functor that can apply function already inside container to container of arguments.

Apply is a weaker version of Applicative that cannot put value inside effetc F.

```scala
trait Apply[F[_]] extends Functor[F] {
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}
```

* Derived methods
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
* Can compose
* Resources
  * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/ApplyUsage.scala) [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala)
  * Cartesian: Cats [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Cartesian.scala)

### Applicative (Applicative Functor)

Applicative Functor is a Functor that can:
- apply function already inside container to container of arguments (so it is Apply)
- put value into container (lift into effect)
```scala
trait Applicative[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A]
}
```
* Applicative Laws:
1. identify: `xs.apply(pure(identity)) == xs` apply identify function lifted inside effect does nothing
2. homomorphism: `pure(a).apply(pure(f)) == pure(f(a))` lifting value a and applying lifted function f is the same as apply function to this value and then lift result
3. interchange: `pure(a).apply(ff) == ff.apply(pure(f => f(a)))` where `ff: F[A => B]`
4. map: `fa.map(f) == fa.apply(pure(f))`
* Derived methods - see Apply
* Applicatives can be composed
* Minimal set of methods to implement Applicative (other methods can be derived from them):
  * map2, pure
  * apply, pure
* Resources:
  * herding cats - Applicative: [blog post](http://eed3si9n.com/herding-cats/Applicative.html)
  * FSiS 2 - Applicative type class - Michael Pilquist: [video](https://www.youtube.com/watch?v=tD_EyIKqqCk)
  * FSiS 3 - Monad type class - Michael Pilquist: [video](https://www.youtube.com/watch?v=VWCtLhH815M)
  * Cats: [docs](https://typelevel.org/cats/typeclasses/applicative.html) [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Applicative.scala)
  * scalaz: [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Applicative.scala)
  * Applicative programming with effects - Conor McBride, Ross Paterson [(shorter)](http://strictlypositive.org/IdiomLite.pdf) [longer](http://strictlypositive.org/Idiom.pdf)
  * The Essence of the Iterator Pattern - Jeremy Gibbons, Bruno C. d. S. Oliveira: [(paper)](https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf)
    * The Essence of the Iterator Pattern - Eric Torreborre [blog post](http://etorreborre.blogspot.com/2011/06/essence-of-iterator-pattern.html)
    * Lifting - Tony Morris: [blog post](http://blog.tmorris.net/posts/lifting/index.html)
  * (Haskell) Abstracting with Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2012/abstracting-with-applicatives/)
  * (Haskell) Algebras of Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2013/algebras-of-applicatives/)

### Monad
We add to Apply ability `flatMap` that can join two computations
and use the output from previous computations to decide what computations to run next.

```scala
trait Monad[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B]
}
```

* Monad Laws:
  1. flatmap associativity: `fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(b => g(b))`
  2. left identity: `pure(a).flatMap(f) == f(a)`
  3. right identity: `fa.flatMap(a => pure(a)) == fa`

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

* Resources
  * FSiS 3 - Monad type class - Michael Pilquist [(vido 14:44)](https://www.youtube.com/watch?v=VWCtLhH815M&t=884)
  * herding cats - Monad [blog post](http://eed3si9n.com/herding-cats/Monad.html)
  * Cats [(docs)](https://typelevel.org/cats/typeclasses/monad.html) [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Monad.scala)
  * scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Monad.scala)
  * (Haskell) Monads for functional programming - Philip Wadler [(paper)](http://homepages.inf.ed.ac.uk/wadler/papers/marktoberdorf/baastad.pdf)
  * More on Monoids and Monads - [(blog post)](https://apocalisp.wordpress.com/2010/07/21/more-on-monoids-and-monads/)
  * [wiki.haskell - Blow your mind - Monad magic](https://wiki.haskell.org/Blow_your_mind#Monad_magic)
  * https://www.quora.com/What-are-some-crazy-things-one-can-do-with-monads-in-Haskell

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
* Resources
  * Memoisation with State using Scala - Tony Morris [(blog post)](http://blog.tmorris.net/posts/memoisation-with-state-using-scala/index.html)
  * Scalaz State Monad - Michael Pilquist [(video)](https://www.youtube.com/watch?v=Jg3Uv_YWJqI)

### IO

### Continuation Monad

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
  * [PR to Cats with ContT](https://github.com/typelevel/cats/pull/2506)
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

* https://www.pusher.com/sessions/meetup/london-functional-programmers/interview-pro-tip-travel-through-time
* https://rosettacode.org/wiki/Water_collected_between_towers
* http://landcareweb.com/questions/33409/haskellde-ni-xiang-xing-cong-tardisdao-revstate
* http://hackage.haskell.org/package/tardis/docs/Control-Monad-Tardis.html
* https://kcsongor.github.io/time-travel-in-haskell-for-dummies/
* https://www.reddit.com/r/haskell/comments/199po0/can_the_tardis_monad_be_used_in_an_efficient_way/
* https://repl.it/@Ouroboros2/Haskell-Tardis-1
* http://blog.sigfpe.com/2006/08/you-could-have-invented-monads-and.html

### F-Algebra

* Resources
   * https://lukepalmer.wordpress.com/2013/03/12/constructions-on-typeclasses-part-1-f-algebras/
   * loop/recur F-Algebras Part 1 [(video)](https://vimeo.com/122715366)
   * loop/recur F-Algebras Part 2 [(video)](https://vimeo.com/122716014)
   * loop/recur F-Algebras Part 3 [(video)](https://vimeo.com/122716071)

### Monoidal Categories, Monoid Object

* Resources
  * (Haskell, Category Theory) Discrimination is Wrong: Improving Productivity - Edward Kmett [(video)](https://www.youtube.com/watch?v=cB8DapKQz-I&feature=youtu.be&t=373)
  * (Haskell, Category  Theory) Notions of Computation as Monoids (extended version) - Exequiel Rivas, Mauro Jaskelioff [(paper)](http://www.fceia.unr.edu.ar/~mauro/pubs/Notions_of_Computation_as_Monoids_ext.pdf)

### Contravariant (Contravariant Functor)
```scala
trait Contravariant[F[_]] {
  def contramap[A, B](f: B => A): F[A] => F[B]
}
```
* Resources
  * Scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Contravariant.scala)
  * Cats [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Contravariant.scala)
  * [Haskell libraries using Contravariant functors](https://packdeps.haskellers.com/reverse/contravariant)
  * (Haskell) The Extended Functor Family - George Wilson [video](https://www.youtube.com/watch?v=JUVMiRRq6wU&t=450)

### Divide (Contravariant Apply)
```scala
trait Divide[F[_]] extends Contravariant[F] {
  def divide[A, B, C](fa: F[A], fb: F[B])(f: C => (A, B)): F[C]
}
```
* Laws: let `def delta[A]: A => (A, A) = a => (a, a)`
   1. composition `divide(divide(a1, a2)(delta), a3)(delta) == divide(a1, divide(a2, a3),(delta))(delta)`
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
  * Discrimination is Wrong: Improving Productivity - Edward Kmett [(video)](https://www.youtube.com/watch?v=cB8DapKQz-I)
  * scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Divide.scala)
  
### Divisible (Contravariant Applicative)  
```scala
trait Divisible[F[_]] extends Divide[F] {
  def conquer[A]: F[A]
}
```
* Laws: let `def delta[A]: A => (A, A) = a => (a, a)`
   1. composition `divide(divide(a1, a2)(delta), a3)(delta) == divide(a1, divide(a2, a3),(delta))(delta)`
   2. right identity: `divide(fa, conquer)(delta) == fa`
   3. left identity:  `divide(conquer, fa)(delta) == fa`
* Resources
  * scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Divisible.scala)
  * (Haskell) [contravariant Divisible.hs](https://github.com/ekmett/contravariant/blob/master/src/Data/Functor/Contravariant/Divisible.hs)

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
* Instances [can be defined](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/bifunctor/InstancesForForBuildInTypes.scala) for: Tuple2, Either, Validated. For Function1 not - functions are contravariant for input type.
* Resources
  * scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bifunctor.scala) [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/BifunctorUsage.scala)
  * Cats [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Bifunctor.scala)
  * Funky Scala Bifunctor - Tony Morris [(blog post)](http://blog.tmorris.net/posts/funky-scala-bifunctor/index.html)
  * (Haskell) The Extended Functor Family - George Wilson: [video](https://www.youtube.com/watch?v=JUVMiRRq6wU&t=303)
  * [Haskell libraries using Bifunctors](https://packdeps.haskellers.com/reverse/bifunctors)
  * herding cats — Datatype-generic programming with Bifunctor [(blog post (understand Free monads first))](http://eed3si9n.com/herding-cats/datatype-generic-programming.html)

### Invariant (Invariant Functor, Exponential Functor)

Functor that can create covariant functor (by passing identity as g) or contravariant functor (by passing identity to f)
```scala
trait Invariant[F[_]] {
  def imap[A, B](fa: F[A])(f: A => B)(g: B => A): F[B]
}
```
* Resources
  * Explorations in Variance - Michael Pilquist [(video)](https://www.youtube.com/watch?v=VZWLRepyNvo)
  * Cats [docs](https://typelevel.org/cats/typeclasses/invariant.html) [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Invariant.scala)
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/InvariantFunctor.scala) 

### Comonad

Abstraction for type with one hole that allows:
- map over (extends Functor)
- get current value
- duplicate one layer of abstraction
It is dual to Monad (Monad allow to put value in and collapse one layer).

```scala
trait Comonad[C[_]] extends Functor[C] {
  def extract[A](ca: C[A]): A // counit
  def duplicate[A](ca: C[A]): C[C[A]] // coflatten
  def extend[A, B](ca: C[A])(f: C[A] => B): C[B] = map(duplicate(ca))(f) // coflatMap, cobind
}
```

* Coflatmap / Cobind: ability to extend is sometimes defined in separate trait (extending Functor): [(Cats CoflatMap src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CoflatMap.scala) [(Scalaz Cobind src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Cobind.scala)
 
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

* Resources
  * Scala Comonad Tutorial, Part 1 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/06/22/a-scala-comonad-tutorial/)
  * Scala Comonad Tutorial, Part 2 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/)
  * (Haskell) Getting a Quick Fix on Comonads - Kenneth Foner: https://www.youtube.com/watch?v=F7F-BzOB670
  * Streams for (Co)Free! - John DeGoes: [(video)](https://www.youtube.com/watch?v=R_nYc4FItcI)
  * scalaz [(src Comonad)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Comonad.scala)
  * [Haskell libraries using Comonads](https://packdeps.haskellers.com/reverse/comonad)
  * (Haskell) [(src Control.Comonad)](https://hackage.haskell.org/package/comonad/docs/Control-Comonad.html)
  * [Purescript Control.Comonad](https://pursuit.purescript.org/packages/purescript-control/docs/Control.Comonad)
  * (Haskell) Monads from Comonads - Edward Kmett [(blog post)](http://comonad.com/reader/2011/monads-from-comonads/)
  * (Haskell) Monad Transformers from Comonads - Edward Kmett [(blog post)](http://comonad.com/reader/2011/monad-transformers-from-comonads/)
  * (Haskell) More on Comonads as Monad Transformers - Edward Kmett [(blog post)](http://comonad.com/reader/2011/more-on-comonads-as-monad-transformers/)
  * (Haskell) The Cofree Comonad and the Expression Problem - Edward Kmett [(blog post)](http://comonad.com/reader/2008/the-cofree-comonad-and-the-expression-problem/)
  * (Haskell) Comonads as Spaces - Phil Freeman [(blog post)](http://blog.functorial.com/posts/2016-08-07-Comonads-As-Spaces.html)

### Coreader (Env comonad, Product comonad)

Wrap value of type A with some context R.

```scala
case class CoReader[R, A](extract: A, ask: R) {
  def map[B](f: A => B): CoReader[R, B] = CoReader(f(extract), ask)
  def duplicate: CoReader[R, CoReader[R, A]] = CoReader(this, ask)
}
```

* Resources
  * Scala Comonad Tutorial, Part 1 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/06/22/a-scala-comonad-tutorial/)
  * (Haskell) [(src Control-Comonad-Env)](https://hackage.haskell.org/package/comonad/docs/Control-Comonad-Env.html)

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
  * Scala Comonad Tutorial, Part 1 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/06/22/a-scala-comonad-tutorial/)

![](traversable_foldable.svg)

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
* Resources:
   * FSiS 4 - Semigroup, Monoid, and Foldable type classes - Michael Pilquist [video 55:38](https://www.youtube.com/watch?v=ueo_E2BxMnA&t=3337)
   * Cats [(docs)](https://typelevel.org/cats/typeclasses/foldable.html) [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Foldable.scala)
   * scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Foldable.scala) [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/FoldableUsage.scala)
   * scalaz  Foldable1 [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Foldable1.scala) [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/Foldable1Usage.scala)
   * Bifoldable: Cats [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Bifoldable.scala) scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bifoldable.scala)

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
* Traverse are composable Distributive (scalaz [src](https://github.com/scalaz/scalaz/blob/scalaz-seven/core/src/main/scala/scalaz/Distributive.scala))
it require only Functor (and Traverse require Applicative)
```scala
trait Distributive[F[_]] extends Functor[F] {
   def distribute[G[_]:Functor,A,B](fa: G[A])(f: A => F[B]): F[G[B]]
   def cosequence[G[_]:Functor,A](fa: G[F[A]]): F[G[A]]
}
```
* Resources
   * FSiS 5 - Parametricity and the Traverse type class - Michael Pilquist [(video)](https://www.youtube.com/watch?v=D0Fnzr15BAU) 
   * The Essence of the Iterator Pattern - Jeremy Gibbons, Bruno C. d. S. Oliveira: [(paper)](https://www.cs.ox.ac.uk/jeremy.gibbons/publications/iterator.pdf)
   * Cats [(docs)](https://typelevel.org/cats/typeclasses/traverse.html) [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Traverse.scala)
   * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/TraverseUsage.scala) [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Traverse.scala)
   * Bitraverse [(scalaz src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Bitraverse.scala)
   * Traverse1 [(scalaz src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Traverse1.scala)

### SemigroupK (Plus)

Semigroup that abstracts over type constructor F. Fo any proper type A can produce Semigroup for F[A]. 

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

### TraverseEmpty

* Finding all permutations of list: [(blog post haskell)](https://byorgey.wordpress.com/2007/06/26/deducing-code-from-types-filterm/) [(translation to Scala using Cats)](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/mtl/TraverseEmptyListPermutationsSpec.scala)

### Monad Transformers
* Resources
  * FSiS 7 - OptionT transformer - Michael Pilquist [(video)](https://www.youtube.com/watch?v=ZNUTMabdgzo)
  * FSiS 8 - EitherT transformer - Michael Pilquist [(video)](https://www.youtube.com/watch?v=z7rCBQ_vTRg)

### Natural transformation (FunctionK)

Represent mappings between two functors.

```scala
trait NaturalTransf[F[_], G[_]] {
  def apply[A](fa: F[A]): G[A]
}
```

* Resources
  * Cats [docs](https://typelevel.org/cats/datatypes/functionk.html) [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/FunctionK.scala)

## Free constructions

| abstraction | free construction |
| ----------- | --------------------- |
| Monoid      | List, Vector | 
| Functor     | [Yoneda](#yoneda), [Coyoneda](#coyoneda), [Density](#density-comonad), [Codensity](#codensity), [Right Kan Extension](#right-kan-extension), [Left Kan Extension](#left-kan-extension), [Day Convolution](#day-convolution) |
| Applicative | [FreeApplicative](#free-applicative) |
| Alternative | [Free Alternative](#free-alternative)
| Monad       | [Free Monads](#free-monads), [Codensity](#codensity), [Right Kan Extension](#right-kan-extension) |
| Comonad     | [CoFree](#cofree), [Density](#density-comonad) |
| Arrow       | [Free Arrow](#free-arrow) |

* [Usage of Free construction in Haskell](https://packdeps.haskellers.com/reverse/free)

### Free Applicative

* Resources
  * [Cats docs](https://typelevel.org/cats/datatypes/freeapplicative.html)
  * Move Over Free Monads: Make Way for Free Applicatives! - John deGoes: https://www.youtube.com/watch?v=H28QqxO7Ihc

### Free Monads

ADT (sometimes implemented using Fix point data type) that form a Monad without any other conditions:

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
  * (Theory) [nLab](https://ncatlab.org/nlab/show/free+monad)

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

### Yoneda

Construction that abstract over type constructor and allow to effectively stack computations.

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

* we can define Functor instance without any requirement on F:
```scala
def yonedaFunctor[F[_]]: Functor[Yoneda[F, ?]] =
  new Functor[Yoneda[F, ?]] {
    def map[A, B](fa: Yoneda[F, A])(f: A => B): Yoneda[F, B] =
      new Yoneda[F, B] {
        def run[C](f2: B => C): F[C] = fa.run(f andThen f2)
      }
  }
```

* Yoneda effectively stack computations. 

* Resources
  * https://vimeo.com/122708005
  * Free Monads and the Yoneda Lemma - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
  * (Scala & Haskell) How Haskell is Changing my Brain, Yay Yoneda - Alissa Pajer: https://vimeo.com/96639840
  * (Haskell) Reverse Engineering Machines with the Yoneda Lemma - Dan Piponi: [(blog post)](http://blog.sigfpe.com/2006/11/yoneda-lemma.html)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * scalaz [(Yoneda src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Yoneda.scala)
  * (Theory) [nLab](https://ncatlab.org/nlab/show/co-Yoneda+lemma)
  * Purescript [](https://pursuit.purescript.org/packages/purescript-free/docs/Data.Yoneda)

### Coyoneda

Rúnar in [Free Monads and the Yoneda Lemma](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
describe this type as a proof that: "if we have a type B, a function of type (B => A) for some type A, and a value of type F[B] for some functor F, then we certainly have a value of type F[A]"

This result from Category Theory allow us to perform `Coyoneda Trick`:

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

we can map over lifted constructor F without any requirements on F. So Coyoneda is a Free Functor:

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

* Resources
  * loop/recur Coyoneda [(video)](https://vimeo.com/122708005)
  * Free Monads and the Yoneda Lemma - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
  * (Scala & Haskell) How Haskell is Changing my Brain, Yay Yoneda - Alissa Pajer: https://vimeo.com/96639840
  * (Haskell) Reverse Engineering Machines with the Yoneda Lemma - Dan Piponi: [(blog post)](http://blog.sigfpe.com/2006/11/yoneda-lemma.html)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * scalaz [(Coyoneda src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Coyoneda.scala)
  * (Theory) [nLab](https://ncatlab.org/nlab/show/co-Yoneda+lemma)

## Arrows

![](arrows.svg)

### Profunctor

Contravariant on first and Functor on the second argument.
```scala
trait Profunctor[F[_, _]] {
  def dimap[A, B, C, D](fab: F[A, B])(f: C => A)(g: B => D): F[C, D]
}
```
* Laws: [Cats src](https://github.com/typelevel/cats/blob/0.7.x/laws/src/main/scala/cats/laws/ProfunctorLaws.scala)
* Derived methods:
```scala
def lmap[A, B, C](fab: F[A, B])(f: C => A): F[C, B]
def rmap[A, B, C](fab: F[A, B])(f: B => C): F[A, C]
```
* Resources
   * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Profunctor.scala) [laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ProfunctorLaws.scala)
   * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala) [laws](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala#L39-L45)
   * [Haskell libraries using Profunctors](https://packdeps.haskellers.com/reverse/profunctors)
   * [Tom Ellis: 24 Days of Hackage: profunctors](https://ocharles.org.uk/guest-posts/2013-12-22-24-days-of-hackage-profunctors.html)
   * Explorations in Variance - Michael Pilquist [(video)](https://www.youtube.com/watch?v=VZWLRepyNvo)
   * (Haskell) Fun with Profunctors - Phil Freeman [video](https://www.youtube.com/watch?v=OJtGECfksds)
   * Monadic profunctors for bidirectional programming [(post)](https://blog.poisson.chat/posts/2017-01-01-monadic-profunctors.html), [(blog Lysxia)](https://blog.poisson.chat/), repo [Lysxia/profunctor-monad](https://github.com/Lysxia/profunctor-monad)
   * Analog of free monads for Profunctors [(post SO)](https://stackoverflow.com/questions/39241262/analog-of-free-monads-for-profunctors)
   * Purescript [src](https://github.com/purescript/purescript-profunctor/blob/master/src/Data/Profunctor.purs)

### Strong Profunctor

Profunctor with additional method `first` that lift profunctor so it can run on first element of tuple.

For Profunctor of functions from A to B this operation just apply function to first element of tuple.

```scala
trait StrongProfunctor[P[_, _]] extends Profunctor[P] {
  def first[X,Y,Z](pab: P[X, Y]): P[(X, Z), (Y, Z)]
}
```

* Laws in [Haskell implementation of Strong Profunctor](https://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Strong.html)
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

* Resources
   * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Strong.scala) [laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/StrongLaws.scala)
   * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Strong.scala)
   * Haskell [Data.Profunctor.Strong](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Strong.html)
   * Purescript [src](https://github.com/purescript/purescript-profunctor/blob/master/src/Data/Profunctor/Strong.purs)
   * usage of Strong in [paf31/purescript-sdom](https://github.com/paf31/purescript-sdom/blob/master/src/SDOM.purs#L197-L205)

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

* Resources
   * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala)
   * Haskell [src](http://hackage.haskell.org/package/profunctors/docs/Data-Profunctor-Choice.html)
   * Purescript [src](https://github.com/purescript/purescript-profunctor/blob/master/src/Data/Profunctor/Choice.purs)

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

* Category laws
 * associativity `f.compose(g.compose(h)) == f.compose(g).compose(h)`
 * left `f.compose(id) == id.compose(f) == f`

* Resources
  * (Category Theory) Category Theory 1.2: What is a category? - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=i9CU4CuHADQ)
  * Cats [src Category](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Category.scala)
  * Cats [src Category laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CategoryLaws.scala)
  * Cats [src Compose](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Compose.scala)
  * Cats [src Compose laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ComposeLaws.scala)

### Arrow

* Learning Scalaz - Arrow - eed3si9n: http://eed3si9n.com/learning-scalaz/Arrow.html
* [Tom Ellis: 24 Days of GHC Extensions: Arrows](https://ocharles.org.uk/guest-posts/2014-12-21-arrows.html)
* FixxBuzz using arrows [(blog post) (Haskell)](http://logicaltypes.blogspot.com/2014/02/arrow-is-spelt-fizz-buzz.html)

### Free Arrow

* Resources
  * Do it with (free?) arrows! – Julien Richard Foy [video](https://www.youtube.com/watch?v=PWBTOhMemxQ)

### Kleisli

* Resources
  * (C++) Category Theory 3.2: Kleisli category - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=i9CU4CuHADQ)
  * (Category Theory) Category Theory 4.1: Terminal and initial objects - Bartosz Milewski [(first 10 min of video)](https://www.youtube.com/watch?v=zer1aFgj4aU)
  * Cats [docs](http://typelevel.org/cats/datatypes/kleisli.html) [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kleisli.scala)
  * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/KleisliUsage.scala) [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kleisli.scala)

## Adjunctions & Kan Extension

### Adjunctions

Adjunction[F,B] spacify relation between two Functors (There is natural transformation between composition of those two functors and identity.)
We say that F is left adjoint to G.

```scala
trait Adjunction[F[_], G[_]] {
  def left[A, B](f: F[A] => B): A => G[B]
  def right[A, B](f: A => G[B]): F[A] => B
}
```

Adjunction can be defined between Reader monad and Coreader comonad.

* Resources:
  * Scala Comonad Tutorial, Part 2 - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/)
  * Adjunctions in Everyday Life - Rúnar Bjarnason [(video Scala)](https://www.youtube.com/watch?v=BLk4DlNZkL8) [( video Haskell)](https://www.youtube.com/watch?v=f-kdpR0BPqo)
  * [Scalaz docs](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/AdjunctUsage.scala) [Scalaz src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Adjunction.scala)
  * [Haskell libraries using Adjunctions](https://packdeps.haskellers.com/reverse/adjunctions)
  * (Haskell) Representing Adjunctions - Edward Kmett [(blog post)](http://comonad.com/reader/2008/representing-adjunctions/)
  * (Haskell) Zapping Adjunctions - Edward Kmett [(blog post)](http://comonad.com/reader/2008/zapping-strong-adjunctions/)
  * (Haskell) Adjoint Triples - Dan Doel [(blog post)](http://comonad.com/reader/2016/adjoint-triples/) 
  * TheCatsters - Adjunctions [(vide playlist)](https://www.youtube.com/watch?v=loOJxIOmShE&list=PL54B49729E5102248)

### Right Kan extension

```scala
trait Ran[G[_], H[_], A] {
  def runRan[B](f: A => G[B]): H[B]
}
```

* We can create functor for Ran without any requirements on G, H
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

* We can define Monad for Ran without any requirements on G, H. Monad
generated by Ran is Codensity.
```scala
def codensityMonad[F[_], A](ran: Ran[F, F, A]): Codensity[F, A] =
  new Codensity[F, A] {
    def run[B](f: A => F[B]): F[B] = ran.runRan(f)
  }
```

* Resources
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala)
  * Purescript implementation of [Kan in freebroccolo/purescript-kan-extensions](https://github.com/freebroccolo/purescript-kan-extensions/blob/master/src/Data/Functor/Kan/Ran.purs)
  * [Haskell libraries using Kan extensions](https://packdeps.haskellers.com/reverse/kan-extensions)
  * (Haskell, Category Theory) Kan Extensions - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/04/17/kan-extensions/)
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Haskell) Kan Extensions II: Adjunctions, Composition, Lifting - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions-ii/)
  * (Haskell) Kan Extensions III: As Ends and Coends - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extension-iii/)
  * (Haskell) Free Monads for Less (Part 1 of 3): Codensity - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less/)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * (Haskell) Free Monads for Less (Part 3 of 3): Yielding IO - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-3/)  


### Left Kan Extension

```scala
trait Lan[F[_], H[_], A] {
  type B
  val hb: H[B]
  def f: F[B] => A
}
```

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
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala)
  * Purescript implementation of [Lan in freebroccolo/purescript-kan-extensions](https://github.com/freebroccolo/purescript-kan-extensions/blob/master/src/Data/Functor/Kan/Lan.purs)
  * [Haskell libraries using Kan extensions](https://packdeps.haskellers.com/reverse/kan-extensions)
  * (Haskell, Category Theory) Kan Extensions - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/04/17/kan-extensions/)
    
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
  * (Haskell) implementation [kan-extensions/Data.Functor.Day)](http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Day.html)
  * (Purescritp) implementation [paf31/purescript-day)](https://pursuit.purescript.org/packages/purescript-day/10.0.0/docs/Data.Functor.Day)
  * (Purescript) extensible coeffect system built out of comonads and Day convolution [paf31/purescript-smash](https://github.com/paf31/purescript-smash/blob/master/src/Data/Smash.purs)
  * (Purescript) [paf31/purescript-react-explore](https://github.com/paf31/purescript-react-explore/blob/master/src/React/Explore/List.purs)

### Density Comonad

Density is a Comonad that is simpler that Left Kan Extension. More precisely it is comonad formed by left Kan extension of a Functor along itself.)

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

Density form a Functor without any conditions of F so it is a Free Functor. Similar like Lan.

```scala
def functorInstance[K[_]]: Functor[Density[K, ?]] = new Functor[Density[K, ?]] {
  def map[A, B](x: Density[K, A])(fab: A => B): Density[K, B] = Density[K,B,x.X](x.f andThen fab, x.fb)
}
```

Density is a Comonad without any conditions of F so it is a Free Comonad.

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
  * Partial implementation by kenji yoshida [(gist)](https://gist.github.com/xuwei-k/7937745)
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Purescript) [rightfold/purescript-density-codensity Density](https://github.com/rightfold/purescript-density-codensity/blob/master/src/Control/Comonad/Density.purs)
  * (Haskell) [ekmett/kan-extensions Density](https://hackage.haskell.org/package/kan-extensions/docs/Control-Comonad-Density.html)

### Codensity

Interface with flatMap'ish method:
```scala
trait Codensity[F[_], A] {
  def run[B](f: A => F[B]): F[B]
}
```
that gives us monad (without any requirement on F):
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
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Codensity.scala)
  * scalaz [example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/CodensityUsage.scala)


### Representable

```scala
// TODO Haskell extends Distrivutive, Scalaz require F to be Functor
trait Representable[F[_], Rep] {
  def tabulate[X](f: Rep => X): F[X]
  def index[X](fx: F[X])(f: Rep): X
}
```

* Resources:
  * (Haskell) Data.Functor.Rep: [(src Haskell)](https://hackage.haskell.org/package/adjunctions-4.4/docs/Data-Functor-Rep.html)
  * (Haskell) Representing Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2013/representing-applicatives/)
  * (Category Theory, Haskell) Representable Functors - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2015/07/29/representable-functors/)
  * (Category Theory, Haskell) Category Theory II 4.1: Representable Functors - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=KaBz45nZEZw) [Scala code translation](https://github.com/typelevel/CT_from_Programmers.scala/blob/master/src/main/tut/2.4-representable-functors.md)
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Representable.scala)
  * (Haskell) Zippers Using Representable And Cofree - Chris Penner [(blog post)](http://chrispenner.ca/posts/representable-cofree-zippers):
  * Reasoning with representable functors - Adelbert Chang [(blog post)](https://adelbertc.github.io/posts/2017-08-09-representable-functors.html)
  * https://www.schoolofhaskell.com/user/edwardk/moore/for-less
  * https://jozefg.bitbucket.io/posts/2013-10-21-representable-functors.html
  * https://stackoverflow.com/a/46502280
  * https://stackoverflow.com/questions/6177950/representable-functor-isomorphic-to-bool-a
  
## Recursive schemas

* Resources:
  * (Haskell) Functional Programming with Bananas, Lenses, Envelopes and Barbed Wire - Erik Meijer, Maarten Fokkinga, Ross Paterson: [(paper Haskell)](https://maartenfokkinga.github.io/utwente/mmf91m.pdf)
  * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog Haskell)](http://comonad.com/reader/2009/recursion-schemes/)
  * (Haskell) Rotten Bananas - Edward Kmett: http://comonad.com/reader/2008/rotten-bananas/
  * (Haskell) Generalized Hylomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/generalized-hylomorphisms/)
  * (Haskell) Time for Chronomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/time-for-chronomorphisms/)
  * (Haskell) Unnatural Transformations - Edward Kmett [(blog post)](http://comonad.com/reader/2008/unnatural-transformations/)
  * (Haskell) Dynamorphisms as Chronomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/dynamorphisms-as-chronomorphisms/)
  * (Haskell) Elgot (Co)Algebras - Edward Kmett [(blog post)](http://comonad.com/reader/2008/elgot-coalgebras/) 
  
### Cathamorphism
* (Haskell) - Catamorphisms - Edward Kmett: [(article)](https://www.schoolofhaskell.com/user/edwardk/recursion-schemes/catamorphisms)

## Optics

### Iso
* Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA)

### Prism
* Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA&t=960)

### Lens
* Resources:
  * Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA&t=1694)
  * scalaz [Lens src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Lens.scala) [(PLens src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/PLens.scala)

### Profunctor Optics

Optics (Iso, Lens, Prism, etc) can be implemented using different Profunctors.

If we have following Profunctor hierarchy:

```scala
trait Strong[P[_, _]] extends Profunctor[P] {
  def first[A,B,C](pab: P[A, B]): P[(A, C), (B, C)]
}

trait Choice[P[_, _]] extends Profunctor[P] {
  def left[A,B,C](pab: P[A, B]):  P[Either[A, C], Either[B, C]]
  def right[A,B,C](pab: P[A, B]): P[Either[C, A], Either[C, B]] = dimap(_.swap, _.swap)(left(pab))
}

trait Step[P[_,_]] extends Choice[P] with Strong[P] {
  def step[A,B,C,D](pab: P[A,B]): P[Either[D, (A,C)], Either[D, (B,C)]] = right(first(pab))
}

trait Walk[P[_,_]] extends Step[P] {
  def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
}

trait Settable[P[_,_]] extends Walk[P] {
  def mapping[A,B,F[_]](pab: P[A,B])(implicit FT: Functor[F]): P[A,B] => P[F[A], F[B]]
}

trait Closed[P[_,_]] extends Profunctor[P] {
  def closed[A,B,X](pab: P[A,B]): P[X=>A, X=>B]
}
```

Then optics can be expressed in following way:

```scala
trait Iso[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit P: Profunctor[P]): P[S, T]
}

trait Lens[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit S: Strong[P]): P[S, T]
}

trait Prism[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit C: Choice[P]): P[S, T]
}

trait AffineTraversal[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit S: Step[P]): P[S, T]
}

trait Traversal[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit W: Walk[P]): P[S, T]
}

trait SEC[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit S: Settable[P]): P[S, T]
}

trait Grates[S, T, A, B] { // https://r6research.livejournal.com/28050.html
  def run[P[_,_]](pab: P[A, B])(implicit C: Closed[P]): P[S, T]
}
```

* Resources:
  * Bartosz Milewski - Profunctor Optics: The Categorical Approach [(video)](https://www.youtube.com/watch?v=l1FCXUi6Vlw)
  * Mainline Profunctor Heirarchy for Optics [(blog post)](https://r6research.livejournal.com/27476.html)
  * Jeremy Gibbons - Profunctor Optics Modular Data Accessors [(video)](https://www.youtube.com/watch?v=sfWzUMViP0M)
  * Grate: A new kind of Optic: [(blog post)](https://r6research.livejournal.com/28050.html)

## Functional data structures

### Zipper
* Resources:
  * learning Scalaz - Zipper [(blog post)](http://eed3si9n.com/learning-scalaz/Zipper.html)
  * Zippers by Example - George Wilson: https://www.youtube.com/watch?v=woK7ntZRwXQ
  * Zippers, Comonads & Data Structures in Scala - Mark Hibberd & Tony Morris: https://www.youtube.com/watch?v=WpA8VPekcK4
  * scalaz [(src)]()

### FingerTree
* Resources:
  * Extreme Cleverness: Functional Data Structures in Scala - Daniel Spiewak [(video)](https://www.youtube.com/watch?v=pNhBQJN44YQ)
  * scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/FingerTree.scala)
  * What's new in purely functional data structures since Okasaki? [theory](https://cstheory.stackexchange.com/questions/1539/whats-new-in-purely-functional-data-structures-since-okasaki)

## WIP Collections of interesting papers mostly in (Haskell)
* [Functional pearls](https://wiki.haskell.org/Research_papers/Functional_pearls) (Haskell some in OCaml)
* [School of Haskell articles](https://www.schoolofhaskell.com/) 
* [The Comonad.Reader](http://comonad.com/reader/)
* [Monads and all that](https://www.cs.uoregon.edu/research/summerschool/summer12/curriculum.html) - John Hughes
* [The Monad.Reader](https://themonadreader.wordpress.com/) and [old issues](https://wiki.haskell.org/The_Monad.Reader/Previous_issues)
