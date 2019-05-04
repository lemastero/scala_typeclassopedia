# Abstract Algebra

## Summary of structures with one operation

![](img/single_op_abstract_algebra.svg)

Summary of structures in abstract algebra consisting of:
 - a Set S 
 - binary operation *
that are total (so they form a Magma).

| Algebraic structure   | associativity | identity | invertibility | commutativity | idempotency |
| ---------------- | -------------- | --------- | -------------- | -------------- | ------------ |
| Semigroup             | associativity |          |               |               |             |
| Commutative semigroup | associativity |          |               | commutativity |             |
| Inverse semigroup     | associativity |          | invertibility |               |             |
| Monoid                | associativity | identity |               |               |             |
| Commutative Monoid    | associativity | identity |               | commutativity |             |
| Group                 | associativity | identity | invertibility |               |             |
| Abelian Group         | associativity | identity | invertibility | commutativity |             |
| Band                  | associativity |          |               |               | idempotency |
| Semilattice           | associativity |          |               | commutativity | idempotency |
| Bounded semilattice   | associativity | identity |               | commutativity | idempotency |

Properties of operation

| property           | definition                |
| -----------------  | ------------------------- | 
| closure (totality) | x * y belongs to S        |
| associativity      | x * (y * z) = (x * y) * z |
| identity           | x * id = id * x = x       |
| invertibility      | x * x' = x' * x = id      |
| commutativity      | x * y = y * x             |
| idempotency        | x * x = x                 |

## Magma

* [PR for Cats](https://github.com/typelevel/cats/pull/2197)
* [Medial Magma](https://en.wikipedia.org/wiki/Medial_magma)

## Semigroup

Abstract over associative operation `combine` on some proper type A.

```scala
trait Semigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend <>
}
```

* Semigroup Law - associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`

* Derived methods:
```scala
def combineN(a: A, n: Int): A // multiply1
def combineAllOption(as: TraversableOnce[A]): Option[A]
```

* Semigroup can be defined for:
  - containers like List, Vector, Nel with concatenation
  - numeric types like Short/Int/Long/BigInteger with + or *
  - Strings with string concatenation

* Examples how to define alternative Semigroup instances [for Option, Int with *](https://github.com/lemastero/learn_scala_cats/blob/master/src/main/scala/monoid/AlternativeMonoidInstances.scala) and [usage](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/monoid/AlternativeMonoidInstancesSpec.scala).
Examples for usage of derived methods and combine [(src)](https://github.com/lemastero/learn_scala_cats/blob/master/src/test/scala/semigroup/SemigroupExamplesSpec.scala)  

* Implementations: [Cats](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Semigroup.scala) [Scalaz 7](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Semigroup.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/semigroup.scala) [twitter/algebird](https://github.com/twitter/algebird/blob/master/algebird-core/src/main/scala/com/twitter/algebird/Semigroup.scala) [Haskell](http://hackage.haskell.org/package/base/docs/Data-Semigroup.html) [Java](http://www.functionaljava.org/javadoc/4.8.1/functionaljava/fj/Semigroup.html)

* Resources:
   * herding cats - Semigroup [(blog post)](http://eed3si9n.com/herding-cats/Semigroup.html)
   * [Cats docs](https://typelevel.org/cats/typeclasses/semigroup.html) 
   * [Scalaz example](https://github.com/scalaz/scalaz/blob/series/7.3.x/example/src/main/scala/scalaz/example/FunctorUsage.scala)
   
## Commutative Semigroup

Semigroup where operation is commutative

```scala
trait CommutativeSemigroup[A] extends Semigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. commutativity: `(x |+| y) == (y |+| x)`

## Monoid

Abstract over associative operation `combine` that have default value `empty`.
```scala
trait Monoid[A] extends Semigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
  def empty: A // mempty
}
```

* Monoid Laws
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. right identity: `(x |+| empty) == x`
  3. left identity: `(empty |+| x) == x`

* Derived methods:
```scala
def combineAll(as: TraversableOnce[A]): A
```

* Monoid can be defined for List (with list concatenation, Nil), Vector, Int with +, Strings with string concatenation. NEL is Semigroup but not a Monoid, same with non empty Strings :)

* We can create Monoid for any Semigroup by wrapping it in Option (None is empty value) same with Map

* It is possible to provide additional structure to Monoid (but not as much as in Group).
Such monoids (ReductiveMonoid, CancellativeMonoid, GCDMonoid) are implemented
in [monoid-subclasses in Haskell](https://hackage.haskell.org/package/monoid-subclasses) described in paper [Adding Structure to Monoids by Mario Blaževic](https://github.com/blamario/monoid-subclasses/wiki/Files/HaskellSymposium2013.pdf)

* We can joint two monoids into tuple of monoids.

* Implementations: [Cats](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Monoid.scala) [Scalaz](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Monoid.scala) [Scalaz 8](https://github.com/scalaz/scalaz/blob/series/8.0.x/base/shared/src/main/scala/scalaz/tc/monoid.scala) [Java functionaljava](http://www.functionaljava.org/javadoc/4.8.1/functionaljava/fj/Monoid.html) [Java DataFixerUpper](https://github.com/Mojang/DataFixerUpper/blob/master/src/main/java/com/mojang/datafixers/kinds/Monoid.java)

* Resources:
   * FSiS 4 - Semigroup, Monoid, and Foldable type classes - Michael Pilquist [video 23:02](https://www.youtube.com/watch?v=ueo_E2BxMnA&t=1381)
   * herding cats - Monoid [(blog post)](http://eed3si9n.com/herding-cats/Monoid.html)
   * [Cats docs](https://typelevel.org/cats/typeclasses/monoid.html)
   * twitter/algebird [src](https://github.com/twitter/algebird/blob/master/algebird-core/src/main/scala/com/twitter/algebird/Monoid.scala) [docs](https://twitter.github.io/algebird/typeclasses/monoid.html)
   * (Haskell) [base/Data-Monoid](http://hackage.haskell.org/package/base/docs/Data-Monoid.html)
   * (Haskell) Monoids, theme and variations - Brent Yorgey [(video)](https://www.youtube.com/watch?v=X-8NCkD2vOw) [(paper)](http://repository.upenn.edu/cgi/viewcontent.cgi?article=1773&context=cis_papers)
   * (Haskell) https://byorgey.wordpress.com/2011/04/18/monoids-for-maybe/
   * (Haskell) http://blog.sigfpe.com/2009/01/haskell-monoids-and-their-uses.html
   * (Haskell) https://apfelmus.nfshost.com/articles/monoid-fingertree.html
   * On Monoids - Rúnar Bjarnason [(blog post)](https://apocalisp.wordpress.com/2010/06/14/on-monoids/)

### Monoid homomorphisms
Function f between two monoids A, B that preserve the structure of monoid:
  1. `f(A.empty) == B.empty`
  2. `f(A.combine(a1,a2)) == B.compine( f(a1), f(a2) )`

For example size and Monoid - list with concatenation and integers with addition.

* Resources:
   * Rúnar Óli Bjarnason - Composing Programs [(video)](https://youtu.be/h8aPc8sji9Q?t=783)
   * (Haskell) Monoids, theme and variations - Brent Yorgey [(video)](https://www.youtube.com/watch?v=X-8NCkD2vOw) [(paper)](http://repository.upenn.edu/cgi/viewcontent.cgi?article=1773&context=cis_papers)

## Commutative Monoid

Monoid where operation is commutative

```scala
trait CommutativeMonoid[A] extends Monoid[A] with CommutativeSemigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
  def empty: A // mempty
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. right identity: `(x |+| empty) == x`
  3. left identity: `(empty |+| x) == x`
  4. commutativity: `(x |+| y) == (y |+| x)`

* Resources 
  * [Cats src](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/CommutativeMonoid.scala) [(laws)](https://github.com/typelevel/cats/blob/master/kernel-laws/src/main/scala/cats/kernel/laws/CommutativeMonoidLaws.scala)

## RegularSemigroup

Semigroup where element with it's inverse don't produce neutral element but produce sth that
behaves like one. 

```scala
trait RegularSemigroup[A] extends Monoid[A] {
  def combine(x: A, y: => A): A // |+| mappend <>
  def inverse(a: A): A
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. pseudoinverse 1: `x |+| -x |+| x ==  x`
  3. pseudoinverse 2: `-x |+| x |+| -x == -x`
  
* there could be multiple inverse elements (in group there is only one inverse)
* x |+| -x is idempotent, because
 (x |+| -x) |+| (x |+| -x) == (x |+| -x |+| x) |+| -x == x |+| -x

* Resources:
   * Haskell Live-Coding, Session 4.1, Regular and Inverse Semigroups - Edward Kmett [video](https://www.youtube.com/watch?v=d7JPz3Vq9YI)
 
## InverseSemigroup

Semigroup (or regular semigroup) in which every element has unique inverse.
Other definition: Semigroup in which every element has at least one inverse and
all indempotent elements commute.

```scala
trait InverseSemigroup[A] extends RegularSemigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend <>
  def inverse(a: A): A
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. pseudoinverse 1: `x |+| -x |+| x ==  x`
  3. pseudoinverse 2: `-x |+| x |+| -x == -x`
  4. pseudoinverse is unique

* Resources:
   * Haskell Live-Coding, Session 4.1, Regular and Inverse Semigroups - Edward Kmett [video](https://www.youtube.com/watch?v=d7JPz3Vq9YI)

## Group

Monoid where each element has an inverse

```scala
trait Group[A] extends Monoid[A] {
  def combine(x: A, y: => A): A // |+| mappend <>
  def empty: A // mempty
  def inverse(a: A): A
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. right identity: `(x |+| empty) == x`
  3. left identity: `(empty |+| x) == x`
  4. inverse: `(x |+| -x) == (-x |+| x) == empty`

* derived methods:
```scala
def remove(a: A, b: A): A = combine(a, inverse(b))
```

* Resources:
   * [(Cats Group src)](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Group.scala) [(laws)](https://github.com/typelevel/cats/blob/master/kernel-laws/src/main/scala/cats/kernel/laws/GroupLaws.scala)
   * twitter/algebird Group [src](https://github.com/twitter/algebird/blob/master/algebird-core/src/main/scala/com/twitter/algebird/Group.scala) [docs](https://twitter.github.io/algebird/typeclasses/group.html)
   * (Haskell) [monoids Data.Group](http://hackage.haskell.org/package/algebra/docs/Numeric-Additive-Group.html#t:Group)

## Abelian Group (Commutative Group)

Group where operation |+| is commutative

```scala
trait CommutativeGroup[A] extends Group[A] with CommutativeMonoid[A] {
  def combine(x: A, y: => A): A // |+| mappend
  def empty: A // mempty
  def inverse(a: A): A
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. right identity: `(x |+| empty) == x`
  3. left identity: `(empty |+| x) == x`
  4. inverse: `(x |+| -x) == (-x |+| x) == empty`
  5. commutativity: `(x |+| y) == (y |+| x)`

* Resources:
   * [Cats src](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/CommutativeGroup.scala) [laws](https://github.com/typelevel/cats/blob/master/kernel-laws/src/main/scala/cats/kernel/laws/CommutativeGroupLaws.scala)
   * (Haskell) [algebra/Numeric.Additive Abelian](http://hackage.haskell.org/package/algebra/docs/Numeric-Additive-Class.html#t:Abelian)

## Band (Idempotent semigroup)

Semigroup whose operation is also idempotent

```scala
trait Band[A] extends Semigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. idempotent: `(x |+| x) == x`
  
 * Resources:
    * cats [(src)](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Band.scala) [(laws)](https://github.com/typelevel/cats/blob/master/kernel-laws/src/main/scala/cats/kernel/laws/BandLaws.scala)

### bands with additional structure:

| type of band           | additional law                       |
| ---------------------- | ------------------------------------ | 
| **left zero band**     | `x + y == x`                         |
| **right zero band**    | `x + y == y`                         |
| **rectangular band**   | `x + y + x == x`                     |
| **normal band**        | `z + x + y + z == z + y + x + z`     |
| **left-regular band**  | `x + y + x == x + y`                 |
| **right-regular band** | `x + y + x == y + x`                 |
| **regular bands**      | `z + x + z + y + z == z + x + y + z` |

## Semilattice

Semilattice is commutative semigroup whose operation is also idempotent.

```scala
trait Semilattice[A] extends Band[A] with CommutativeSemigroup[A] {
  def combine(x: A, y: => A): A // |+| mappend
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. commutativity: `(x |+| y) == (y |+| x)`
  3. idempotent: `(x |+| x) == x`
  
### MeetSemilattice and JoinSemilattice
Sometimes (in typelevel/algebra) there are two definitions of Semilattice:

A meet-semilattice (or lower semilattice) is a semilattice whose operation is called "meet",
and which can be thought of as a greatest lower bound.

```scala
trait MeetSemilattice[A] {
 def meet(lhs: A, rhs: A): A
}
```

and a join-semilattice (or upper semilattice) is a semilattice whose operation is called "join",
and which can be thought of as a least upper bound

```scala
trait JoinSemilattice[A] {
 def join(lhs: A, rhs: A): A
}
```

+ Resources:
   * [Cats Semilattice src](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/Semilattice.scala) [laws](https://github.com/typelevel/cats/blob/master/kernel-laws/src/main/scala/cats/kernel/laws/SemilatticeLaws.scala)
   * [scalaz Semilattice src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/SemiLattice.scala)
   * [typelevel/algebra Meet Semilattice](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/MeetSemilattice.scala) 
   * [typelevel/algebra Join Semilattice](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/JoinSemilattice.scala)

## Bounded Semilattice

Semilattice that has identity or Commutative Monoid that idempotent.

```scala
trait BoundedSemilattice[A] extends Semilattice[A] with CommutativeMonoid[A] {
  def combine(x: A, y: => A): A // |+| mappend
  def empty: A // mempty
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. right identity: `(x |+| empty) == x`
  3. left identity: `(empty |+| x) == x`
  4. commutativity: `(x |+| y) == (y |+| x)`
  5. idempotent: `(x |+| x) == x`
  
### Bounded Join Semilattice, Bounded Meet Semilattice

Similar as with Semilattice definition we can define Bounded Join Semilattice and Bounded Meet Semilattice

```scala
trait BoundedJoinSemilattice[A] extends JoinSemilattice[A] {
  def join(lhs: A, rhs: A): A
  def zero: A
}
```

```scala
trait BoundedMeetSemilattice[A] extends MeetSemilattice[A] {
  def meet(lhs: A, rhs: A): A
  def one: A
}
```

* Resources:
   * [typelevel/cats BoundedSemilattice src](https://github.com/typelevel/cats/blob/master/kernel/src/main/scala/cats/kernel/BoundedSemilattice.scala) [laws](https://github.com/typelevel/cats/blob/master/kernel-laws/src/main/scala/cats/kernel/laws/BoundedSemilatticeLaws.scala)
   * [typelevel/algebra Bounded Join Semilattice](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/BoundedJoinSemilattice.scala)
   * [typelevel/algebra Bounded Meet Semilattice](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/BoundedMeetSemilattice.scala)

# Algebraic structures with two operations

## Lattice

Has two associativity, commutativity and idempotent operations: join and meet that obey absorption law:

```scala
trait Lattice[A] extends JoinSemilattice[A] with MeetSemilattice[A] {
  def join(lhs: A, rhs: A): A
  def meet(lhs: A, rhs: A): A
}
```

* laws:
  1. associativity: `(x join y) join z) == (x join (y join z))`
  2. commutativity: `(x join y) == (y join x)`
  3. idempotent: `(x join x) == x`
  4. associativity: `(x meet y) meet z) == (x meet (y meet z))`
  5. commutativity: `(x meet y) == (y meet x)`
  6. idempotent: `(x meet x) == x`
  7. absorption: `a meet (a join b) == a join (a meet b) == a`

* [typelevel/algebra Lattice src](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/Lattice.scala) [laws](https://github.com/typelevel/algebra/blob/master/laws/src/main/scala/algebra/laws/LatticeLaws.scala)

## Distributive Lattice

Lattice that obey distributive law

```scala
trait DistributiveLattice[A] extends Lattice[A] {
  def join(lhs: A, rhs: A): A
  def meet(lhs: A, rhs: A): A
}
```

* laws:
  1. associativity: `(x join y) join z) == (x join (y join z))`
  2. commutativity: `(x join y) == (y join x)`
  3. idempotent: `(x join x) == x`
  4. associativity: `(x meet y) meet z) == (x meet (y meet z))`
  5. commutativity: `(x meet y) == (y meet x)`
  6. idempotent: `(x meet x) == x`
  7. absorption: `a meet (a join b) == a join (a meet b) == a`
  8. distributive: `a meet (b join c) == (a meet b) join (a meet c)`
  9. distributive: `a join (b meet c) == (a join b) meet (a join c)`

* [typelevel/algebra Distributive Lattice](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/DistributiveLattice.scala)

## BoundedLattice

```scala
trait BoundedLattice[A] extends Lattice[A] with BoundedMeetSemilattice[A] with BoundedJoinSemilattice[A] {
  def join(lhs: A, rhs: A): A
  def zero: A
  def meet(lhs: A, rhs: A): A
  def one: A
}
```

* laws:
  1. associativity: `(x join y) join z) == (x join (y join z))`
  2. commutativity: `(x join y) == (y join x)`
  3. idempotent: `(x join x) == x`
  4. associativity: `(x meet y) meet z) == (x meet (y meet z))`
  5. commutativity: `(x meet y) == (y meet x)`
  6. idempotent: `(x meet x) == x`
  7. absorption: `a meet (a join b) == a join (a meet b) == a`
  8. identity of zero: `a join zero == a`
  9. identity of one: `a meet one == a`

## Bounded Distributive Lattice

```scala
trait BoundedDistributiveLattice[A] extends BoundedLattice[A] with DistributiveLattice[A] {
  def join(lhs: A, rhs: A): A
  def zero: A
  def meet(lhs: A, rhs: A): A
  def one: A
}
```

* laws:
  1. associativity: `(x join y) join z) == (x join (y join z))`
  2. commutativity: `(x join y) == (y join x)`
  3. idempotent: `(x join x) == x`
  4. associativity: `(x meet y) meet z) == (x meet (y meet z))`
  5. commutativity: `(x meet y) == (y meet x)`
  6. idempotent: `(x meet x) == x`
  7. absorption: `a meet (a join b) == a join (a meet b) == a`
  8. distributive: `a meet (b join c) == (a meet b) join (a meet c)`
  9. distributive: `a join (b meet c) == (a join b) meet (a join c)`
  10. identity of zero: `a join zero == a`
  11. identity of one: `a meet one == a`

* [typelevel/algebra Bounded Distributive Lattice](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/BoundedDistributiveLattice.scala)

## Heyting algebras

Heyting algebra is bounded distributive lattices equipped with operation `imp` (written as ->)
and `complement` operation (written as -a)

```scala
trait Heyting[A] extends BoundedDistributiveLattice[A] {
  def join(lhs: A, rhs: A): A
  def zero: A
  def meet(lhs: A, rhs: A): A
  def one: A
  def imp(a: A, b: A): A
  def complement(a: A): A // a imp 0
}
```

* laws:
  1. associativity: `(x join y) join z) == (x join (y join z))`
  2. commutativity: `(x join y) == (y join x)`
  3. idempotent: `(x join x) == x`
  4. associativity: `(x meet y) meet z) == (x meet (y meet z))`
  5. commutativity: `(x meet y) == (y meet x)`
  6. idempotent: `(x meet x) == x`
  7. absorption: `a meet (a join b) == a join (a meet b) == a`
  8. distributive: `a meet (b join c) == (a meet b) join (a meet c)`
  9. distributive: `a join (b meet c) == (a join b) meet (a join c)`
  10. identity of zero: `a join zero == a`
  11. identity of one: `a meet one == a`
  12. complement law: `a meet -a == 0`
  13. implication laws:
    `a -> a == 1`
    `a ∧ (a -> b) == a meet b`
    `b ∧ (a -> b) == b`
    `a -> (b meet c) == (a -> b) meet (a -> c)`

* derived methods:

```scala
def or(a: A, b: A): A = join(a, b)
def and(a: A, b: A): A = meet(a, b)
def xor(a: A, b: A): A = or(and(a, complement(b)), and(complement(a), b))
def nand(a: A, b: A): A = complement(and(a, b))
def nor(a: A, b: A): A = complement(or(a, b))
def nxor(a: A, b: A): A = complement(xor(a, b))
```

* [typelevel/algebra Heyting](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/Heyting.scala)

## Boolean algebras

Boolean algebra is Heyting algebras were law of the excluded middle is true

```scala
trait Bool[A] extends Heyting[A] {
  def join(lhs: A, rhs: A): A
  def zero: A
  def meet(lhs: A, rhs: A): A
  def one: A
  def imp(a: A, b: A): A
  def complement(a: A): A // a imp 0
}
```

* derived methods:
```scala
def without(a: A, b: A): A = and(a, complement(b))
```

* laws:
  1. associativity: `(x join y) join z) == (x join (y join z))`
  2. commutativity: `(x join y) == (y join x)`
  3. idempotent: `(x join x) == x`
  4. associativity: `(x meet y) meet z) == (x meet (y meet z))`
  5. commutativity: `(x meet y) == (y meet x)`
  6. idempotent: `(x meet x) == x`
  7. absorption: `a meet (a join b) == a join (a meet b) == a`
  8. distributive: `a meet (b join c) == (a meet b) join (a meet c)`
  9. distributive: `a join (b meet c) == (a join b) meet (a join c)`
  10. identity of zero: `a join zero == a`
  11. identity of one: `a meet one == a`
  12. complement law: `a meet -a == 0`
  13. implication laws:
    - `a -> a == 1`
    - `a ∧ (a -> b) == a meet b`
    - `b ∧ (a -> b) == b`
    - `a -> (b meet c) == (a -> b) meet (a -> c)`
  14. excluded middle law: `(a join (a -> 0)) == 1` 

* [typelevel/algebra Bool](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/lattice/Bool.scala)

## Rig

Ring without (n)egation

```scala
trait Ring[T] extends CommutativeGroup[T] {
  def zero: T // empty mempty 
  def plus(l: T, r: T): T // combine mappend <> |+|
  def negate(v: T): T // inverse
  def one: T
  def times(l: T, r: T): T
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. identity: `(x |+| zero) == x == (zero |+| x)`
  4. inverse: `(x |+| -x) == (-x |+| x) == zero`
  5. commutativity: `(x |+| y) == (y |+| x)`
  6. associativity: `((x * y) * z) == (x * (y * z))`
  7. right identity: `(x * one) == x`
  8. left identity: `(one * x) == x`
  9. distributive: `a * (b |+| c) == (a * b) |+| (a * c)` 

* Resources
   * [typelevel/algebra Rng](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/ring/Rig.scala)
   * (Haskell) [algebra/Numeric.Rig](http://hackage.haskell.org/package/algebra/docs/Numeric-Rig-Class.html)

## Rng (Semiring)

Ring without an identity

```scala
trait Rng[T] extends CommutativeGroup[T] {
  def zero: T // empty mempty 
  def plus(l: T, r: T): T // combine mappend <> |+|
  def negate(v: T): T // inverse
  def times(l: T, r: T): T
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. identity: `(x |+| zero) == x == (zero |+| x)`
  4. inverse: `(x |+| -x) == (-x |+| x) == zero`
  5. commutativity: `(x |+| y) == (y |+| x)`
  6. associativity: `((x * y) * z) == (x * (y * z))`
  9. distributive: `a * (b |+| c) == (a * b) |+| (a * c)` 

* Resources
   * [typelevel/algebra Rng](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/ring/Rng.scala)
   * (Haskell) [algebra/Numeric.Rng](http://hackage.haskell.org/package/algebra/docs/Numeric-Rng-Class.html#t:Rng)

## Semiring

* https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/ring/Semiring.scala

## Ring

Abelian group with a second binary operation that is associative, is distributive over the abelian group operation, and has an identity element.

```scala
trait Ring[T] extends CommutativeGroup[T] {
  def zero: T // empty mempty 
  def plus(l: T, r: T): T // combine mappend <> |+|
  def negate(v: T): T // inverse
  def one: T
  def times(l: T, r: T): T
}
```

* laws:
  1. associativity: `((x |+| y) |+| z) == (x |+| (y |+| z))`
  2. identity: `(x |+| zero) == x == (zero |+| x)`
  4. inverse: `(x |+| -x) == (-x |+| x) == zero`
  5. commutativity: `(x |+| y) == (y |+| x)`
  6. associativity: `((x * y) * z) == (x * (y * z))`
  7. right identity: `(x * one) == x`
  8. left identity: `(one * x) == x`
  9. distributive: `a * (b |+| c) == (a * b) |+| (a * c)` 

* Resources
   * typelevel/algebra [src](https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/ring/Ring.scala) [laws](https://github.com/typelevel/algebra/blob/master/laws/src/main/scala/algebra/laws/RingLaws.scala)
   * twitter/algebird [src](https://github.com/twitter/algebird/blob/master/algebird-core/src/main/scala/com/twitter/algebird/Ring.scala) [docs](https://twitter.github.io/algebird/typeclasses/ring.html)

## United monoid

"Consider two monoids (S, +, 0) and (S, ⋅, 1), which operate on the same set S,
such that + is commutative and ⋅ distributes over +.

We call these monoids united if 0 = 1."

* Resources
   * United Monoids - Andrey Mokhov [(blog post)](https://blogs.ncl.ac.uk/andreymokhov/united-monoids/)
  
## Field

* https://github.com/typelevel/algebra/blob/master/core/src/main/scala/algebra/ring/Field.scala

## Tropical semiring (Min-Plus algebra)

* twitter/algebird [src](https://github.com/twitter/algebird/blob/develop/algebird-core/src/main/scala/com/twitter/algebird/MinPlus.scala)

## Vector Space

* Resources
   * twitter/algebird [src](https://github.com/twitter/algebird/blob/develop/algebird-core/src/main/scala/com/twitter/algebird/VectorSpace.scala)
   * Life After Monoids - Tom Switzer talks about Group, Ring, Vector Space [(video)](https://www.youtube.com/watch?v=xO9AoZNSOH4)

## StarRig
* StarRig [non/irreg](https://github.com/non/irreg/blob/master/src/main/scala/irreg/StarRig.scala)

## Kleene algebra
* Resources:
  * Erik Osheim - Regexes, Kleene Algebras, and Real Ultimate Power! [(video)](https://vimeo.com/96644096)
  * (Haskell) A Very General Method of Computing Shortest Paths [(article)](http://r6.ca/blog/20110808T035622Z.html)

# Advanced Algebra Resources
* [Encoding of algebraic structures and properties in CubicalTT](https://github.com/mortberg/cubicaltt/blob/master/examples/algstruct.ctt)
* [Algorithms for Lie algebras of algebraic groups - Roozemond, D.A.](https://pure.tue.nl/ws/files/102833998/201010124.pdf)
* [Graduate algebra - Drew Anderson](http://www.math.miami.edu/~armstrong/oldcourses.php)
