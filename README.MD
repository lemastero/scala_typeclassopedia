# Scala typeclassopedia

* Resources covering all topis about FP in greater details in way better and more approachable form:
  * Functional Programming in Scala - Paul Chiusano and Runar Bjarnason [(best book about FP in Scala (I have bought it for myself and higly recommend it. Worth reading, doing exercises and re-reading.))](https://www.manning.com/books/functional-programming-in-scala)
  * Functional Structures in Scala - Michael Pilquist: [videos explaining concepts, implementations and usage](https://www.youtube.com/watch?v=Dsd4pc99FSY&list=PLFrwDVdSrYE6dy14XCmUtRAJuhCxuzJp0)
  * herding cats - eed3si9n: [blog posts](http://eed3si9n.com/herding-cats/)

## Abstract Algebra

### Monoid, Semigroup
* Brent Yorgey: Monoids - theme and variations 
[(video)](https://www.youtube.com/watch?v=X-8NCkD2vOw)
[(paper)](http://repository.upenn.edu/cgi/viewcontent.cgi?article=1773&context=cis_papers)
* On Monoids - Runar: https://apocalisp.wordpress.com/2010/06/14/on-monoids/

## Cathegory Theory

### Functor

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
  1. identify: `xs.map(identity) = xs` map identify function does nothing
  2. composition: `xs.map(f).map(g) = xs.map(x => g(f(x))` you can map using f and then using g or map once using composition
* Examples: Instancec can be [implemented](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/functor/InstancesForBuiltInTypes.scala) for: List, Option, Either, Validated, Tuple1, Tuple2, [Function varying return type](https://www.youtube.com/watch?v=Dsd4pc99FSY&t=1075)
* Functor must preserve structure, so Set is not a Functor (map constant function).
* Functors can be composed
* Derived methods of Functor: [(examples)](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/functor/FunctorExamplesSpec.scala)
```scala
def lift[A, B](f: A => B): F[A] => F[B] // lift regular function to function inside container/under effect
def fproduct[A, B](fa: F[A])(f: A => B): F[(A, B)] // zip every element with result of executing functiion f on it
def as[A, B](fa: F[A], b: B): F[B] // replace every element with b
def void[A](fa: F[A]): F[Unit] // clear preserving structure
def tupleLeft[A, B](fa: F[A], b: B): F[(B, A)]
def tupleRight[A, B](fa: F[A], b: B): F[(A, B)]
def widen[A, B >: A](fa: F[A]): F[B]
def compose[G[_]](other: Functor[G]): Functor[Lambda[X => F[G[X]]]] // compose with other functor G
```
* Resources:
  * (Scala) herding cats - Functor: [blog post](http://eed3si9n.com/herding-cats/Functor.html)
  * (Scala) FSiS 1, Type Constructors, Functors, and Kind Projector - Michael Pilquist: [video](https://www.youtube.com/watch?v=Dsd4pc99FSY)
  * (Haskell) The Extended Functor Family - George Wilson: [video](https://www.youtube.com/watch?v=JUVMiRRq6wU)
  * (Scala) Cats : [docs](https://typelevel.org/cats/typeclasses/functor.html) [source](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Functor.scala)

### Contravariant

### BiFunctor

### Profunctor
* (Haskell) Fun with Profunctors - Phil Freeman: https://www.youtube.com/watch?v=OJtGECfksds

### Applicative

### Monad

```scala
trait Monad[M[_]] extends Functor[M] {
  def flatMap[A, B](a: M[A])(f: A => M[B]): M[B]
  def pure[A](a : A): M[A]
}
``` 

### State monad
* Scalaz State Monad - Michael Pilquist: https://www.youtube.com/watch?v=Jg3Uv_YWJqI

### Reader monad
* (Scala) The Reader Monad for Dependency Injection - Json Arhart: https://www.youtube.com/watch?v=xPlsVVaMoB0

### Writer Monad

### IO monad

### MonadPlus

### Free monad
* (Scala) Free Monads in Cats docs: http://typelevel.org/cats/datatypes/freemonad.html
* (Scala) Free Monoids and Free Monads - Runar: http://blog.higher-order.com/blog/2013/08/20/free-monads-and-free-monoids/
* (Scala) Why the free Monad isn’t free - Kelley Robinson: https://www.youtube.com/watch?v=wvNgoeZza2g
* (Scala) Beyond Free Monads - John DeGoes: https://www.youtube.com/watch?v=A-lmrvsUi2Y
* (Scala) Free as in Monads - Daniel Spiewak: https://www.youtube.com/watch?v=aKUQUIHRGec

### Free applicative
* Move Over Free Monads: Make Way for Free Applicatives! - John deGoes: https://www.youtube.com/watch?v=H28QqxO7Ihc

### Arrow
* Learning Scalaz - Arrow - eed3si9n: http://eed3si9n.com/learning-scalaz/Arrow.html
* FixxBuzz using arrows [(blog post) (Haskell)](http://logicaltypes.blogspot.com/2014/02/arrow-is-spelt-fizz-buzz.html)

### Kleisli
* (Scala) Kleisli Arrow in Cats docs: http://typelevel.org/cats/datatypes/kleisli.html

### Yoneda
* (Scala) (Haskell) How Haskell is Changing my Brain, Yay Yoneda - Alissa Pajer: https://vimeo.com/96639840

### Comonad
* (Haskell) Getting a Quick Fix on Comonads - Kenneth Foner: https://www.youtube.com/watch?v=F7F-BzOB670
* (Scala) Streams for (Co)Free! - John DeGoes: https://www.youtube.com/watch?v=R_nYc4FItcI 

### Foldable

### Traverse
* Finding all permutations of list: [(blog post haskell)](https://byorgey.wordpress.com/2007/06/26/deducing-code-from-types-filterm/) [(translation to Scala using Cats)](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/traverse/ListPermutationsUsingFilterM.scala)

## Recursive schemas

### Cathamorphism
* (Haskell) - Catamorphisms - Edward Kmett: [(article)](https://www.schoolofhaskell.com/user/edwardk/recursion-schemes/catamorphisms)

## Optics

### Iso
* (Scala) Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA)

### Prism
* (Scala) Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA&t=960)

### Lens
* (Scala) Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA&t=1694)

## Effects
* (Scala) The Eff monad, one monad to rule them all - Eric Torreborre: https://www.youtube.com/watch?v=KGJLeHhsZBo

## Functional data structures
* (Scala) Zippers by Example - George Wilson: https://www.youtube.com/watch?v=woK7ntZRwXQ
* (Scala) Zippers, Comonads & Data Structures in Scala - Mark Hibberd & Tony Morris: https://www.youtube.com/watch?v=WpA8VPekcK4

## WIP Collections of interesting papers in (Haskell) / (OCaml)
* Functional pearls:  https://wiki.haskell.org/Research_papers/Functional_pearls
* School of Haskell: articles: https://www.schoolofhaskell.com/
* The Comonad.Reader: http://comonad.com/reader/
* Monads and all that - John Hughes: https://www.cs.uoregon.edu/research/summerschool/summer12/curriculum.html
* The Monad.Reader: https://themonadreader.wordpress.com/
