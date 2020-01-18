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

* Implementations scalaz [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Yoneda.scala), [Purescript](https://pursuit.purescript.org/packages/purescript-free/docs/Data.Yoneda), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/covyoneda.v), [Agda](https://github.com/agda/agda-categories/blob/master/Categories/Yoneda.agda), [HoTT](https://github.com/HoTT/HoTT/blob/master/theories/Categories/Yoneda.v), [nlab](https://ncatlab.org/nlab/show/Yoneda+lemma)

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

* Implementations: [Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala), [Haskell](http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Kan-Ran.html), [Purescript](https://github.com/freebroccolo/purescript-kan-extensions/blob/master/src/Data/Functor/Kan/Ran.purs), [Agda](https://github.com/agda/agda-categories/blob/master/Categories/Kan.agda), [UniMath](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/RightKanExtension.v), [nLab](https://ncatlab.org/nlab/show/Kan+extension)

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

[Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Kan.scala), [Haskell](http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Kan-Lan.html), [Purescript](https://github.com/freebroccolo/purescript-kan-extensions/blob/master/src/Data/Functor/Kan/Lan.purs), [Agda](https://github.com/agda/agda-categories/blob/master/Categories/Kan.agda), [nLab](https://ncatlab.org/nlab/show/Kan+extension)

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
