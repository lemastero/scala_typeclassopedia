# Hitchhiker's Guide to the Quadrinitarianism
## Discovering Proof Theory, Category Theory, Type Theory, Homotopy Theory by looking at functional programming in Scala and proofs in Agda.

* Vol I: From FP in Scala to Abstract Algebra and Category Theory and back
* Vol II: HoTT take on Type Theory, Cubical TT and Process Calculi
* Vol III: Proof Theory in Agda or logic, categories and types as computer game
* Vol IV: Space the final frontier of Homotopy Theory
* Vol V: Automated <s>Theorem Proving</s> generating FP programs

# 1. Category

### Spotting common pattern of category

Observation 1:
Scala one argument functions connect two types (proper types, without any type hole).
If the functions are pure and results and argument types matches:

```scala
def f: String => Int = s => s.length
def g: Int => Boolean = n % 2 == 0
def h: Boolean => Double = if(h) 355.0/113.0 else 22.0/7.0
```

then it does not matter in what order we compose them

`f andThen (g andThen h)`
`(f andThen g) andThen h`

we end up with the same result. So we can compose them.

We could also define one function that will do all the work, instead of 

For every type e.g. `Int` there is a pure function `identity[Int]: Int => Int` that does nothing

Observation 2:
Lets consider Scala type hierarchy presented at [https://docs.scala-lang.org/tour/unified-types.html](https://docs.scala-lang.org/tour/unified-types.html)
![](https://docs.scala-lang.org/resources/images/tour/unified-types-diagram.svg)

For every type that extends directly another type we can write judgement that first is subtype of the other. For example:
* `AnyRef` is subtype for `Any` 
* `List[String]` is subtype for `AnyRef` 
* `Nothing` is subtype for `List[String]`

If the types line up as above then we can say that:
* `List[String]` is subtype of `Any`
* `Nothing` is subtype of `AnyRef`
* `Nothing` is subtype of `Any`


### Definition of [Category](https://ncatlab.org/nlab/show/category) in Category Theory
A category C consists of:
* a collection `C0` of `objects`
* a collection `C1` of `morphisms` (sometimes named `arrows`)
* two functions `s,t: C1-> C0` which assign to every morphism , its `source` object (or `domain`) and a `target object` (or `codomain`).
If `f` is a morphism with `X` as its source and `Y` as its target, we write  `f: X -> Y` and we say that f is a morphism from x to y.
* for every  pair of morphisms `f` and `g` such that `t(f) = s(g)` their `composition of morphisms f and g` written as`g ∘ f`  
* function `id: C0->C1` which assigns to each object `x` a morphism `id(x)` or `1(x)` - `identity morphism` on `x`
* such that following properties are satisfied:
  * source and target are respected by composition: `s(g ∘ f) = s(f)` and `t(g ∘ f) = t(g)`
  * source and target are respected by identity: `s(id(x)) = x` and `t(id(x)) = x`
  * composition is associative: `(h ∘ g) ∘ f = h ∘ (g ∘ f)` whenever `t(f) = s(g)` and `t(g) = s(h)`

If we omit the requirement of identity morphism we get [semicategory](https://ncatlab.org/nlab/show/semicategory)

### Category of Scala types and pure functions
* objects: Scala types: e.g. `Boolean`, `Int`, `String`, `Option[Int]`, `List[String]`
* morphisms: pure functions (`Function1`)
* identity for object A: `identity[A]`
is most often used category.

Abstraction based on definition of Category in Scala - allows to specify:
* morphism as type constructor with 2 holes: `F[A,B]`
* identity for type A `def id[A]: F[A, A]`
* composition of morphisms `def compose[A, B, C](f: F[B, C], g: F[A, B]): F[A, C]`

```scala
trait Category[F[_, _]] {
  def id[A]: F[A, A]
  def compose[A, B, C](f: F[B, C], g: F[A, B]): F[A, C]
}
```

Implementations in libraries:  
*  Compose/Semicategory [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Compose.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/semicategory.scala)  
  Category: [Cats](https://github.com/typelevel/cats/blob/master/core/src/main/scala/cats/arrow/Category.scala), [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/category.scala), [Haskell](https://hackage.haskell.org/package/base/docs/Control-Category.html)
* Implementations in proof assistants: [Agda](https://github.com/agda/agda-categories/blob/master/Categories/Category/Core.agda), [CubicalTT](https://github.com/mortberg/cubicaltt/blob/master/examples/category.ctt)

[Cats Category laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/CategoryLaws.scala), [Cats Compose laws](https://github.com/typelevel/cats/blob/master/laws/src/main/scala/cats/laws/ComposeLaws.scala):
 * associativity `f.compose(g.compose(h)) == f.compose(g).compose(h)`
 * left `f.compose(id) == id.compose(f) == f`

* Resources
  * (Category Theory) Category Theory 1.2: What is a category? - Bartosz Milewski [(video)](https://www.youtube.com/watch?v=i9CU4CuHADQ)
  * Hask is not a category - Andrej Bauer [(blog)](http://math.andrej.com/2016/08/06/hask-is-not-a-category/)

### Category 1

Category containing only single object and identity morphism is usually written as `1`.
Example implementation in Scala:
```scala
type One[A,B] = Unit

val oneCategory: Category[One] = new Category[One] {
  def source[A, B](f: One[A, B]): TheObject[One, A] = ()
  def target[A, B](f: One[A, B]): TheObject[One, B] = ()
  def compose[A, B, C](f: One[B, C], g: One[A, B]): One[A, C] = ()
}
```
based on approach from [Data.Category.Unit in Haskell data-category](https://hackage.haskell.org/package/data-category-0.7/docs/Data-Category-Unit.html)

# 2. Opposite Category

## Implementation in Scala

Implementation of opposite category in Scala: 

```scala
case class Op[K[_,_],A,B](unOp: K[B,A])

def oppositeCategory[K[_,_]](implicit CC: Category[K]): Category[Op[K, *, *]] = new Category[Op[K, *, *]] {
  def source[A, B](f: Op[K, A, B]): TheObject[Op[K, *, *], A] = Op(CC.target(f.unOp))
  def target[A, B](f: Op[K, A, B]): TheObject[Op[K, *, *], B] = Op(CC.source(f.unOp))
  def compose[A, B, C](f: Op[K, B, C], g: Op[K, A, B]): Op[K, A, C] = Op(CC.compose(g.unOp, f.unOp))
}
```
based on approach from [Data.Category.Op in Haskell data-category](https://hackage.haskell.org/package/data-category/docs/Data-Category.html#g:2)

# 2. Functor

# 4. Contravariant functors

# 5. Natural transformation

# 6. Monad

# 7. Comonad

# 8. Profunctors

# 8.1. Strong Profunctor

# 9. Limits and Colimits

## 10.1 Terminal object

## 10.2. Initial object

## 10.3. Cartesian Product

## 10.4. Coproduct

## 10.5. Exponenial object

## 10.6. Semiring of ADT and Curry-Howard Isomorphism 1

## 10.6. Pullback

## 10.7. Pushout

## 10.8. Equalizer

## 10.9. Coequalizer

# 11. Yoneda lemma

## 11.1. Conravariant yoneda lemma

## 11.2. Coyoneda lemma

## 11.3. Contravariant coyoneda lemma

# 12. Adjuncions and Representable functors

# 12.1 Monads from adjunctions

# 13. Kan extensions

## 13.1 Left Kan extension

## 13.2 Right Kan Extension

# 14. Final Algebras and data structures

# 15. Initial Algebras

# 16. Monoidal category

# 16.1. Monoid object

# 16.2. Monoid as monoid object in monoidal category of endofunctors

# 16.3. Day convolution

# 16.4. Monoids in monoidal category - Applicative Functor

# 16.5. Monoids in monoidal category op - Divisible

# 16.6. Monoidal Category of Profunctors

# 16.7. Arrow as monoid object is category of Profunctors

# 16.8. Symmetric Monoidal Categories

# 16.9. Braided Monoidal Categories

# 17.1. Ends and Coends

# 18. Optics

# 18.1. Classic optics and category of optics

# 18.2. Functor optics

# 18.3. Profunctor optics

# 18. Fibrations

# 17. n-category

# 17.1. 2-category

# 17.2. contravariant morphisms in n-categories

# 17.3. Periodic Table of n-categories

# 18. Topos

# 18.1. Subobject classifier

# 18.2. Grothendieck topos

# 19 Higher Topos

# 19.1 Higher Elementary Topos

# 20. Curry-Howard-Lambek Correspondence
