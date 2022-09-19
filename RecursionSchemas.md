# Recursion Schemes

* Implementations:  
   Scala for Cats: [andyscott/droste](https://github.com/andyscott/droste) for Scalaz: [slamdata/matryoshka](https://github.com/slamdata/matryoshka)  
   Haskell [ekmett/recursion-schemes](http://hackage.haskell.org/package/recursion-schemes) [vmchale/recursion](http://hackage.haskell.org/package/recursion/docs/Control-Recursion.html)  
   Idris [vmchale/recursion_schemes](https://github.com/vmchale/recursion_schemes)  
   Purescript [slamdata/purescript-matryoshka](https://github.com/slamdata/purescript-matryoshka)  
   Kotlin [aedans/Katalyst](https://github.com/aedans/Katalyst)  

* Resources:
  * [slamdata/matryoshka](https://github.com/slamdata/matryoshka) main page contains very good overwie, [PDF with summary of Recursion schemes](https://github.com/slamdata/matryoshka/blob/master/resources/recursion-schemes.pdf)
  * Introduction to Recursion Schemes with Matryoshka - Anatolii Kmetiuk [(tutorial)](https://akmetiuk.com/posts/2017-03-10-matryoshka-intro.html)
  * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
  * [Resources for learning and using recursion schemes - Pascal Hartig](https://github.com/passy/awesome-recursion-schemes)
  * (Haskell) Functional Programming with Bananas, Lenses, Envelopes and Barbed Wire - Erik Meijer, Maarten Fokkinga, Ross Paterson: [(paper Haskell)](https://maartenfokkinga.github.io/utwente/mmf91m.pdf)
  * (Haskell) Rotten Bananas - Edward Kmett: [(blog post)](http://comonad.com/reader/2008/rotten-bananas/)
  * (Haskell) Unnatural Transformations - Edward Kmett [(blog post)](http://comonad.com/reader/2008/unnatural-transformations/)
  * (Haskell) Program Reduction: A Win for Recursion Schemes - John Wiegley [(blog post)](http://newartisans.com/2018/04/win-for-recursion-schemes/)
  * https://scrapbox.io/haskell-shoen/Recursion_Schemes

# Algebra, Coalgebra, ElgotAlgebra

* Resources
   * [slamdata/matryoshka docs - Algebras](https://github.com/slamdata/matryoshka#algebras)
   * (Haskell) Constructions on Typeclasses, Part 1: F-Algebras - Luke Palmer [(blog post)](https://lukepalmer.wordpress.com/2013/03/12/constructions-on-typeclasses-part-1-f-algebras/)
   * (Haskell) loop/recur F-Algebras Part 1 [(video)](https://vimeo.com/122715366)
   * (Haskell) loop/recur F-Algebras Part 2 [(video)](https://vimeo.com/122716014)
   * (Haskell) loop/recur F-Algebras Part 3 [(video)](https://vimeo.com/122716071)

# Fixpoint types

* Resources
   * [slamdata/matryoshka docs - Fixpoint Types](https://github.com/slamdata/matryoshka#fixpoint-types)

## Fix

```scala
case class Fix[F[_]](unFix: F[Fix[F]])
```

* Implementations: [Scalaz](https://github.com/slamdata/matryoshka/blob/master/core/shared/src/main/scala/matryoshka/data/Fix.scala) [ekmett/recursion-schemes](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#t:Fix) [andyscott/droste](https://github.com/andyscott/droste/blob/master/modules/core/src/main/scala/qq/droste/data/Fix.scala)

## Mu

* Implementations: [matryoshka](https://github.com/slamdata/matryoshka/blob/master/core/shared/src/main/scala/matryoshka/data/Mu.scala), [andyscott/droste](https://github.com/andyscott/droste/blob/master/modules/core/src/main/scala/qq/droste/data/Mu.scala), [ekmett/recursion-schemes](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#t:Mu), [purescript-fixed-points](https://pursuit.purescript.org/packages/purescript-fixed-points/docs/Data.Functor.Mu)

## Nu

* Implementations: [Scala matryoshka](https://github.com/slamdata/matryoshka/blob/master/core/shared/src/main/scala/matryoshka/data/Nu.scala), [andyscott/droste](https://github.com/andyscott/droste/blob/master/modules/core/src/main/scala/qq/droste/data/Nu.scala), [ekmett/recursion-schemes](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#t:Nu), [purescript-fixed-points](https://pursuit.purescript.org/packages/purescript-fixed-points/docs/Data.Functor.Nu)

# Recursion schemas - Folds

## Cathamorphism

* Implementations ekmett/recursion-schemes [cata](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:cata) [gcata](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gcata)

* Resources
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
   * (Haskell) Catamorphisms Folding data structures - Edward Kmett: [(blog post)](http://comonad.com/haskell/catamorphisms.html)
   * (Haskell) - Catamorphisms - Edward Kmett: [(article)](https://www.schoolofhaskell.com/user/edwardk/recursion-schemes/catamorphisms)

## Paramorphism

* Implementations ekmett/recursion-schemes [para](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:para) [gpara](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gpara)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Zygomorphism

* Implementations ekmett/recursion-schemes [zygo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:zygo) [gzygo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gzygo)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Histomorphism

* Implementations ekmett/recursion-schemes [histo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:histo) [ghisto](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:ghisto)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Prepromorphism

* Implementations ekmett/recursion-schemes [prepro](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:prepro) [gprepro](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gprepro)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

# Recursion schemas - Unfolds

## Anamorphism

* Implementations ekmett/recursion-schemes [ana](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:ana) [gana](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gana)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Apomorphism

* Implementations ekmett/recursion-schemes [apo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:apo) [gapo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gapo)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Futumorphism

* Implementations ekmett/recursion-schemes [futu](http://hackage.haskell.org/package/recursion-schemes-5.1/docs/Data-Functor-Foldable.html#v:futu) [gfutu](http://hackage.haskell.org/package/recursion-schemes-5.1/docs/Data-Functor-Foldable.html#v:gfutu)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Postpromorphism

* Implementations ekmett/recursion-schemes [postpro](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:postpro) [gpostpro](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gpostpro)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

# Recursion schemas - Refolds

## Hylomorphism

* Implementations ekmett/recursion-schemes [hylo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:hylo) [ghylo](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:ghylo)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
   * (Haskell) Generalized Hylomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/generalized-hylomorphisms/)
   * Stalking a Hylomorphism in the Wild - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/12/29/stalking-a-hylomorphism-in-the-wild/)
   * Open Season on Hylomorphisms - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2018/12/20/open-season-on-hylomorphisms/)

## Chronomorphism

* Implementations ekmett/recursion-schemes [chrono](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:chrono) [gchrono](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:gchrono)

* Resources:
  * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
  * (Haskell) Time for Chronomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/time-for-chronomorphisms/)
  * (Haskell) Dynamorphisms as Chronomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/dynamorphisms-as-chronomorphisms/)
  * [Histo- and Dynamorphisms Revisited- Ralf Hinze, Nicolas Wu](http://www.cs.ox.ac.uk/ralf.hinze/publications/WGP13.pdf)

## Synchromorphism

* Implementation [Synchro](http://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Morphism-Synchro.html)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Exomorphism

* Implementation [Exo](http://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Morphism-Exo.html)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Metamorphism Erwig

* Implementation [Erwig](http://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Morphism-Meta-Erwig.html)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Metamorphism Gibbons

* Implementation [Gibbons](http://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Morphism-Meta-Gibbons.html)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)

## Dynamorphism

* Implementation [Dyna](http://hackage.haskell.org/package/category-extras-0.53.5/docs/Control-Morphism-Dyna.html)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
   * (Haskell) Dynamorphisms as Chronomorphisms - Edward Kmett [(blog post)](http://comonad.com/reader/2008/dynamorphisms-as-chronomorphisms/)
   * [Histo- and Dynamorphisms Revisited- Ralf Hinze, Nicolas Wu](http://www.cs.ox.ac.uk/ralf.hinze/publications/WGP13.pdf)

## Elgot algebra

* Implementations ekmett/recursion-schemes [elgot](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:elgot)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
   * (Haskell) Elgot (Co)Algebras - Edward Kmett [(blog post)](http://comonad.com/reader/2008/elgot-coalgebras/)

## Elgot coalgebra

* Implementations ekmett/recursion-schemes [coelgot](http://hackage.haskell.org/package/recursion-schemes/docs/Data-Functor-Foldable.html#v:coelgot)

* Resources:
   * (Haskell) Recursion Schemes: A Field Guide (Redux) - Edward Kmett: [(blog post)](http://comonad.com/reader/2009/recursion-schemes/)
   * (Haskell) Elgot (Co)Algebras - Edward Kmett [(blog post)](http://comonad.com/reader/2008/elgot-coalgebras/)
