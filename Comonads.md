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

* Implementations:  
  CoflatMap/Cobind: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/CoflatMap.scala),  [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Cobind.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/cobind.scala)    
  Comonad: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Comonad.scala), [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Comonad.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/comonad.scala), [Haskell](https://hackage.haskell.org/package/comonad/docs/Control-Comonad.html), [Purescript](https://pursuit.purescript.org/packages/purescript-control/docs/Control.Comonad)
* Verified implementations: [statebox/idris-ct](https://github.com/statebox/idris-ct/blob/master/src/Comonad/Comonad.lidr), [agda-categories](https://github.com/agda/agda-categories/blob/master/Categories/Comonad.agda)

 
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
  * A Real-World Application with a Comonadic User Interface - Arthur Xavier[pdf](https://pdfs.semanticscholar.org/31b9/1969215a46f5d44298e1c1e67872edd7ae77.pdf)
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
