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
   * [Boolean Limits statebox/idris-ct](https://github.com/statebox/idris-ct/blob/master/src/Booleans/BooleanLimits.lidr)

### Colimit

* Resources
   * (Haskell) [category-extras Colimit](http://hackage.haskell.org/package/category-extras-0.53.4/docs/Control-Functor-Limit.html#t:Colimit)
   * (Haskell) [data-category Colimit](http://hackage.haskell.org/package/data-category/docs/Data-Category-Limit.html#g:5)
   * [Boolean CoLimits statebox/idris-ct](https://github.com/statebox/idris-ct/blob/master/src/Booleans/BooleanCoLimits.lidr)
