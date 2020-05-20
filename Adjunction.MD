## Representable & Adjunctions

### Representable

```scala
// TODO Haskell extends Distrivutive, Scalaz require F to be Functor
trait Representable[F[_], Rep] {
  def tabulate[X](f: Rep => X): F[X]
  def index[X](fx: F[X])(f: Rep): X
}
```

* Implementations: [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Representable.scala), [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/Representable.scala), [Haskell](https://hackage.haskell.org/package/adjunctions/docs/Data-Functor-Rep.html), [Agda](https://github.com/agda/agda-categories/blob/master/Categories/Functor/Representable.agda), [UniMath](https://github.com/UniMath/UniMath/tree/master/UniMath/CategoryTheory/RepresentableFunctors), [nLab](https://ncatlab.org/nlab/show/representable+functor)

* Resources:
  * (Haskell) Representing Applicatives - Gershom Bazerman [(blog post)](http://comonad.com/reader/2013/representing-applicatives/)
  * (Category Theory, Haskell) Representable Functors - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2015/07/29/representable-functors/)
  * (Category Theory, Haskell) Category Theory II 4.1: Representable Functors - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=KaBz45nZEZw) [Scala code translation](https://github.com/typelevel/CT_from_Programmers.scala/blob/master/src/main/tut/2.4-representable-functors.md)
  * (Haskell) Zippers Using Representable And Cofree - Chris Penner [(blog post)](http://chrispenner.ca/posts/representable-cofree-zippers):
  * Reasoning with representable functors - Adelbert Chang [(blog post)](https://adelbertc.github.io/posts/2017-08-09-representable-functors.html)
  * (Haskell) Radix Sort, Trie Trees, And Maps From Representable Functors - Chris Penner [(blog post)](https://chrispenner.ca/posts/representable-discrimination)
  * (Haskell) [Monad.Representable.Reader](hackage.haskell.org/package/adjunctions/docs/Control-Monad-Representable-Reader.html), [Monad.Representable.State](http://hackage.haskell.org/package/adjunctions/docs/Control-Monad-Representable-State.html), [Comonad.Representable.Store](http://hackage.haskell.org/package/adjunctions/docs/Control-Comonad-Representable-Store.html)
  * Moore for Less - Edward Kmett [(blog post)](https://www.schoolofhaskell.com/user/edwardk/moore/for-less)
  * Representable Functors - Danny Gratzer [(blog post)](https://jozefg.bitbucket.io/posts/2013-10-21-representable-functors.html)
  * Which Haskell Functors are equivalent to the Reader functor - pigworker [SO](https://stackoverflow.com/a/46502280)
  * Representable Functor isomorphic to (Bool -> a) [SO](https://stackoverflow.com/questions/6177950/representable-functor-isomorphic-to-bool-a)
  * [masaeedu/representable](https://github.com/masaeedu/representable/blob/master/index.hs) (different formulation of Representable functors that carries the domain around)
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
  * (Haskell) [SO - Monads as adjunctions](https://stackoverflow.com/questions/4697320/monads-as-adjunctions)
  * (Haskell) [SO - Which common monads arise from adjunctions in Hask?](https://stackoverflow.com/questions/46878106/which-common-monads-arise-from-adjunctions-in-hask)
  * (Haskell) [SO - How to Factorize Continuation Monad into Left & Right Adjoints?](https://stackoverflow.com/questions/61267827/how-to-factorize-continuation-monad-into-left-right-adjoints)
  * (Haskell) [SO - What are the adjoint functor pairs corresponding to common monads in Haskell?](https://stackoverflow.com/questions/13937289/what-are-the-adjoint-functor-pairs-corresponding-to-common-monads-in-haskell)

### Adjoint Triples

* Resources:
  * (Haskell) Adjoint Triples - Dan Doel [(blog post)](http://comonad.com/reader/2016/adjoint-triples/)
  * (Haskell) [adjunctions/Control.Comonad.Trans.Adjoint](http://hackage.haskell.org/package/adjunctions/docs/Control-Comonad-Trans-Adjoint.html#t:AdjointT)
  * [Adjoint functors and triples - Samuel Eilenberg and John C. Moore](https://projecteuclid.org/euclid.ijm/1256068141)
  * [Fancy Algebra (Graduate Topics Course) - Drew Armstrong](http://www.math.miami.edu/~armstrong/FA.php)
  * [nLab adjoint triple](https://ncatlab.org/nlab/show/adjoint+triple)
