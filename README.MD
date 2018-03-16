# Scala typeclassopedia

* Resources covering all topis about FP in greater details in way better and more approachable form:
  * [Functional Programming in Scala - Paul Chiusano and Rúnar Bjarnason](https://www.manning.com/books/functional-programming-in-scala) Best book about FP in Scala. I have bought it for myself and higly recommend it. Worth reading, doing exercises and re-reading.
  * [Functional Structures in Scala - Michael Pilquist](https://www.youtube.com/watch?v=Dsd4pc99FSY&list=PLFrwDVdSrYE6dy14XCmUtRAJuhCxuzJp0): workshop on [implementating FP constructions](https://github.com/mpilquist/Structures) with usage examples and great insights about Scala and FP.
  * Series of blog posts by Eugene Yokota (@eed3si9n): [herding cats](http://eed3si9n.com/herding-cats/) and [learning Scalaz](http://eed3si9n.com/learning-scalaz/) Easy to understand examples, clear explanations, many insights from Haskell papers and literature.
  * [Examples in scalaz repository](https://github.com/scalaz/scalaz/tree/series/7.3.x/example/src/main/scala/scalaz/example) Learning Scalaz is probably the best documentation for Scalaz.
  * [Documentation for Cats](https://typelevel.org/cats/) (runnable online version for older Cats version on [ScalaExercises](https://www.scala-exercises.org/cats/semigroup))
  * (Haskell) [Typeclassopedia](https://wiki.haskell.org/Typeclassopedia)
  * [channingwalton/typeclassopedia](https://github.com/channingwalton/typeclassopedia) another implementation of FP constructions
  * Scala Type-class Hierarchy - Tony Morris [(blog post)](http://blog.tmorris.net/posts/scala-type-class-hierarchy/index.html) (traits for all cathegory theory constructions with exotic ones like `ComonadHoist`)
  
## Abstract Algebra

### Semigroup
Abstract over associative operation `combine` (mappend in Haskell)  on some proper type A.
```scala
trait Semigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
}
```

* Semigroup Law - associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`

* Derived methods:
```scala
def combineN(a: A, n: Int): A // multiply1
def combineAllOption(as: TraversableOnce[A]): Option[A]
```

* Semigroup can be defined for List (with list concatenation), Vector, Nel, Short/Int/Long with +, Strings with string concatenation

* Examples how to define alterntive Semigroup instances [for Option, Int with *](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/monoid/AlternativeMonoidInstances.scala) and [usage](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/monoid/AlternativeMonoidInstancesSpec.scala).
Examples for usage of derived methods and combine [(src)](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/semigroup/SemigroupExamplesSpec.scala)  

* Resources:
   * herding cats - Semigroup [(blog post)](http://eed3si9n.com/herding-cats/Semigroup.html)
   * Cats [(docs)](https://typelevel.org/cats/typeclasses/semigroup.html) [(src)](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Semigroup.scala)
   * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/FunctorUsage.scala) [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Semigroup.scala)

### Monoid

Abstract over associative operation `combine` that have default value `empty` (mempty in Haskell).
```scala
trait Monoid[A] extends Semigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
  def empty: A // mempty
}
```

* Monoid Laws
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. combine right identity: `(x |+| empty) == x`
  3. combine left identity: `(empty |+| x) == x`

* Derived methods:
```scala
def combineAll(as: TraversableOnce[A]): A
```

* Monoid can be defined for List (with list concatenation, Nil), Vector, Int with +, Strings with string concatenation. NEL is Semigrou but not a Monoid, same with non empty Strings :)

* We can create Monoid for any Semigroup by wrapping it in Option (None is empty value) same with Map

* It is possible to provide additional structure to Monoid (but not as much as in Group).
Such monoids (ReductiveMonoid, CancellativeMonoid, GCDMonoid) are implemented
in [monoid-subclasses in Haskell](https://hackage.haskell.org/package/monoid-subclasses) described in paper [Adding Structure to Monoids by Mario Blaževic](https://github.com/blamario/monoid-subclasses/wiki/Files/HaskellSymposium2013.pdf)

* We can joint two monoids into tuple of monoids.

* **Monoid homomorphisms**
Function f between two monoids A, B that preserve the structure of monoid:
  1. `f(A.empty) == B.empty`
  2. `f(A.combine(a1,a2)) == B.compine( f(a1), f(a2) )`

For example size and Monoid - list with concatenation and integers with addition.

* Resources:
   * FSiS 4 - Semigroup, Monoid, and Foldable type classes - Michael Pilquist [video 23:02](https://www.youtube.com/watch?v=ueo_E2BxMnA&t=1381)
   * herding cats - Monoid [(blog post)](http://eed3si9n.com/herding-cats/Monoid.html)
   * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Monoid.scala)
   * Cats [(docs)](https://typelevel.org/cats/typeclasses/monoid.html) [(src)](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Monoid.scala)
   * (Haskell) Monoids, theme and variations - Brent Yorgey [(video)](https://www.youtube.com/watch?v=X-8NCkD2vOw) [(paper)](http://repository.upenn.edu/cgi/viewcontent.cgi?article=1773&context=cis_papers)
   * (Haskell) https://byorgey.wordpress.com/2011/04/18/monoids-for-maybe/
   * (Haskell) http://blog.sigfpe.com/2009/01/haskell-monoids-and-their-uses.html
   * (Haskell) https://apfelmus.nfshost.com/articles/monoid-fingertree.html
   * On Monoids - Rúnar Bjarnason [(blog post)](https://apocalisp.wordpress.com/2010/06/14/on-monoids/)
   * (semirings etc.) (Haskell) http://r6.ca/blog/20110808T035622Z.html
   
## ... and beyond

* Resources
  * docs typelevel/algebra [(docs)](https://typelevel.github.io/algebra/)
  * docs twitter/algebird [(docs)](https://twitter.github.io/algebird/)
  * Life After Monoids - Tom Switzer talks about Group, Ring, Vector Space [(video)](https://www.youtube.com/watch?v=xO9AoZNSOH4)
  * [(Cats Group src)](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Group.scala)
  * Semilattice [(Cats src)](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Semilattice.scala) [(scalaz src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/SemiLattice.scala)
  * Propagators - Edward Kmett: [(video theory)](https://www.youtube.com/watch?v=acZkF6Q2XKs) 

## Cathegory Theory

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

```scala
trait Apply[F[_]] extends Functor[F] {
  def ap[A, B](ff: F[A => B])(fa: F[A]): F[B]
}
```

* Derived methods
```scala
def product[A,B](fa: F[A], fb: F[B]): F[(A, B)]
def ap2[A,B,C]  (fa: => F[A], fb: => F[B])             (f: F[(A,B) => C]): F[C]
def ap3[A,B,C,D](fa: => F[A], fb: => F[B], fc: => F[C])(f: F[(A,B,C) => D]): F[D]
// ...
def map2[A, B, Z](fa: F[A], fb: F[B])(f: (A, B) => Z): F[Z]
```
* Can compose
* Resources
  * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/ApplyUsage.scala) [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Apply.scala)
  * Cartesian: Cats [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Cartesian.scala)

### Applicative (Applicative Functor)

Applicative Functor is a Functor that can:
- put value into container (lift into effect)
- apply function already inside container to container
```scala
trait Applicative[F[_]] extends Apply[F] {
  def pure[A](value: A): F[A]  
  def apply[A, B](fa: F[A])(ff: F[A => B]): F[B]
}
```
* Applicative Laws:
1. identify: `xs.apply(pure(identity)) == xs` apply identify function lifted inside effect does nothing
2. homomorphism: `pure(a).apply(pure(f)) == pure(f(a))` lifting value a and applying lifted function f is the same as apply function to this value and then lift result
3. interchange: `pure(a).apply(ff) == ff.apply(pure(f => f(a)))` where `ff: F[A => B]`
4. map: `fa.map(f) == fa.apply(pure(f))`
* Derived methods:
```scala
def apply [A, B]   (fa: F[A])          (ff: F[A => B]):     F[B]
def apply2[A, B, Z](fa: F[A], fb: F[B])(ff: F[(A,B) => Z]): F[Z]
// ...
def map2[A , B, Z]  (fa: F[A], fb: F[B])          (f: (A, B) => Z):    F[Z]
def map3[A, B, C, Z](fa: F[A], fb: F[B], fc: F[C])(f: (A, B, C) => Z): F[Z]
// ...
def tuple2[A, B]   (fa: F[A], fb: F[B]):           F[(A, B)]
def tuple3[A, B, C](fa: F[A], fb: F[B], fc: F[C]): F[(A, B, C)]
// ...
def flip[A, B](ff: F[A => B]): F[A] => F[B]
```
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
  * (Haskell) Representing Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2013/representing-applicatives/)


### Monad
We add to Apply ability `flatMap` that can join two computations
and use the output from previous computations to decide what computations to run next.

```scala
trait Monad[F[_]] extends Apply[F] { self =>
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

### Contravariant (Contravariant Functor)
```scala
trait Contravariant[F[_]] {
  def contramap[A, B](f: B => A): F[A] => F[B]
}
```
* Resources
  * Scalaz [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Contravariant.scala)
  * Cats [(src)](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Contravariant.scala)
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
   * Cats [src](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Profunctor.scala)
   * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Profunctor.scala)
   * Explorations in Variance - Michael Pilquist [(video)](https://www.youtube.com/watch?v=VZWLRepyNvo)
   * (Haskell) Fun with Profunctors - Phil Freeman [video](https://www.youtube.com/watch?v=OJtGECfksds)

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

Monoid that abstract over type constructor F. Fo any proper type A can produce Monoid for F[A]. 

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

### Free Monoid, Free Monad, Free Applicative
* Resources
  * Free Monads in Cats docs: http://typelevel.org/cats/datatypes/freemonad.html
  * Why the free Monad isn’t free - Kelley Robinson: https://www.youtube.com/watch?v=wvNgoeZza2g
  * Beyond Free Monads - John DeGoes: https://www.youtube.com/watch?v=A-lmrvsUi2Y
  * Free as in Monads - Daniel Spiewak: https://www.youtube.com/watch?v=aKUQUIHRGec
  * Move Over Free Monads: Make Way for Free Applicatives! - John deGoes: https://www.youtube.com/watch?v=H28QqxO7Ihc
  * Free Monoids and Free Monads - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/08/20/free-monads-and-free-monoids/)
  * (Haskell) Free Monoids in Haskell - Dan Doel [(blog post)](http://comonad.com/reader/2015/free-monoids-in-haskell/)
  * (Haskell) Many Roads to Free Monads - Dan Doel [(blog post)](https://www.schoolofhaskell.com/user/dolio/many-roads-to-free-monads)

### Arrow
* Learning Scalaz - Arrow - eed3si9n: http://eed3si9n.com/learning-scalaz/Arrow.html
* FixxBuzz using arrows [(blog post) (Haskell)](http://logicaltypes.blogspot.com/2014/02/arrow-is-spelt-fizz-buzz.html)

### Kleisli
* Resources
  * Cats [docs](http://typelevel.org/cats/datatypes/kleisli.html) [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kleisli.scala)
  * scalaz [(docs)](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/KleisliUsage.scala) [(src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kleisli.scala)

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
  * (Haskell) [(src Control.Comonad)](https://hackage.haskell.org/package/comonad/docs/Control-Comonad.html)
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
  * (Haskell) Representing Adjunctions - Edward Kmett [(blog post)](http://comonad.com/reader/2008/representing-adjunctions/)
  * (Haskell) Zapping Adjunctions - Edward Kmett [(blog post)](http://comonad.com/reader/2008/zapping-strong-adjunctions/)
  * (Haskell) Adjoint Triples - Dan Doel [(blog post)](http://comonad.com/reader/2016/adjoint-triples/) 
  * TheCatsters - Adjunctions [(vide playlist)](https://www.youtube.com/watch?v=loOJxIOmShE&list=PL54B49729E5102248)

### Yoneda
* Resources
  * Free Monads and the Yoneda Lemma - Rúnar Bjarnason [(blog post)](http://blog.higher-order.com/blog/2013/11/01/free-and-yoneda/)
  * (Scala & Haskell) How Haskell is Changing my Brain, Yay Yoneda - Alissa Pajer: https://vimeo.com/96639840
  * (Haskell) Reverse Engineering Machines with the Yoneda Lemma - Dan Piponi: [(blog post)](http://blog.sigfpe.com/2006/11/yoneda-lemma.html)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * scalaz [(Yoneda src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Yoneda.scala) [(Coyoneda src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Coyoneda.scala)


### Ran (Right Kan extension)

```scala
trait Ran[G[_], H[_], A] {

  def runRan[B](f: A => G[B]): H[B]
}
```

* Haskell

```haskell
newtype Ran g h a = Ran
   { runRan :: forall b. (a -> g b) -> h b }
```

* In category theory it is universal construction:

Lets assume we have three categories: A, B, C and two functors: X : A -> C, F : A -> C.

Right Kan Extension of X along F is a functor R : B -> C and natural transformation (eta) n: RF -> X
that is best, in a sense that for every other candidate: functor M : B -> C and natural transformation u : MF -> X
there is unique natural transformation d: M -> R.

* Resources
  * (Haskell, Category Theory) Kan Extensions - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/04/17/kan-extensions/)
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Haskell) Kan Extensions II: Adjunctions, Composition, Lifting - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions-ii/)
  * (Haskell) Kan Extensions III: As Ends and Coends - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extension-iii/)
  * (Haskell) Free Monads for Less (Part 1 of 3): Codensity - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less/)
  * (Haskell) Free Monads for Less (Part 2 of 3): Yoneda - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-2/)
  * (Haskell) Free Monads for Less (Part 3 of 3): Yielding IO - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less-3/)  
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala)

### Codensity

* Resources
  * Difference Lists and the Codensity Monad - Mio Alter [(video, slides, blog post)](https://begriffs.com/posts/2016-02-04-difference-lists-and-codennsity.html)
  * The Free and The Furious: And by 'Furious' I mean Codensity - raichoo [(video)]()https://www.youtube.com/watch?v=EiIZlX_k89Y)
  * (Haskell) Free Monads for Less (Part 1 of 3): Codensity - Edward Kmett [(blog post)](http://comonad.com/reader/2011/free-monads-for-less/)
  * (Haskell) Kan Extensions - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions/)
  * (Haskell) Kan Extensions II: Adjunctions, Composition, Lifting - Edward Kmett [blog post](http://comonad.com/reader/2008/kan-extensions-ii/)
  * (Haskell) Unnatural Transformations and Quantifiers - Edward Kmett [blog post](http://comonad.com/reader/2012/unnatural-transformations-and-quantifiers/)
  * scalaz [src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Codensity.scala)


### Representable
* TODO

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
