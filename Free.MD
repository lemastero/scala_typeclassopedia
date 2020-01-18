## Free constructions

| abstraction         | free construction     |
| ------------------- | --------------------- |
| Monoid              | List, Vector | 
| Functor             | [Yoneda](#yoneda), [Coyoneda](#coyoneda), [Density](#density-comonad), [Codensity](#codensity), [Right Kan Extension](#right-kan-extension), [Left Kan Extension](#left-kan-extension), [Day Convolution](#day-convolution) |
| Applicative         | [FreeApplicative](#free-applicative) |
| Alternative         | [Free Alternative](#free-alternative) |
| Traversable         | [CoFree Traverse](#cofree-traverse) |
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

ADT (sometimes implemented using Fix point data type)

```scala
sealed trait FreeMonad[F[_], A]
final case class Return[F[_], A](a: A) extends FreeMonad[F, A]
final case class Suspend[F[_], A](s: F[FreeMonad[F,A]]) extends FreeMonad[F,A]
```

that form a Monad, if F is a functor

```scala
def freeMonad[F[_]](implicit FF: Functor[F]): Monad[FreeMonad[F, *]] = new Monad[FreeMonad[F, *]] {
  def flatMap[A, B](ma: FreeMonad[F, A])(f: A => FreeMonad[F, B]): FreeMonad[F, B] =
    ma match {
      case Return(a) => f(a)
      case Suspend(m) => Suspend{
        def ff: FreeMonad[F, A] => FreeMonad[F, B] = x => flatMap(x)(f)
        FF.map(m)(ff)
      }
  }
  def pure[A](a: A): FreeMonad[F, A] = Return(a)
}
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
  * Cofree Comonads and their Uses - λC 2018 - Nihil Shah [(video)](https://www.youtube.com/watch?v=ydZ_fLwo9yI)

### Cofree Traverse

* Resources
  * Cofree Traversable Functors - Love Waern [(paper)](https://uu.diva-portal.org/smash/get/diva2:1369180/FULLTEXT01.pdf)
  * [KingoftheHomeless/Cofree-Traversable-Functors](https://github.com/KingoftheHomeless/Cofree-Traversable-Functors)
  * The Cofree Traversable [(Reddit)](https://www.reddit.com/r/haskell/comments/ajamc0/the_cofree_traversable/)
  * [Twitter discussion](https://twitter.com/Iceland_jack/status/1199524789523828738)

### Free Alternative

* Resources
  * [Haskell free/Control.Alternative.Free](http://hackage.haskell.org/package/free/docs/Control-Alternative-Free.html)
  * [Structurally enforced Free Alternative, without left distributivity - SO](https://stackoverflow.com/questions/45647253/structurally-enforced-free-alternative-without-left-distributivity)
  * Routing With Cofree Comonad - Marcin Szamotulski [(video)](https://www.youtube.com/watch?v=O78UOsKAXsc) [(slides)](https://coot.github.io/routing-with-cofree-comonad/#/) [(repo)](https://github.com/coot/routing-with-cofree-comonad)
  * Applicative Regular Expressions using the Free Alternative - Justin Le [(blog pos)](https://blog.jle.im/entry/free-alternative-regexp.html)

### Free Arrow

* Implementations: [Purescript](http://blog.sigfpe.com/2017/01/building-free-arrows-from-components.html)

* Resources
  * Building free arrows from components [(blog post)](http://blog.sigfpe.com/2017/01/building-free-arrows-from-components.html) 
 