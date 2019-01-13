package profunctor

/**
  * Profunctor is abstraction from Category Theory that models functions.
  *
  * In Category Theroy:
  * When we have Category C and D and D' the opposite category to D,
  * then a Profunctor P is a Functor F :: D' x C -> Set
  * We write D -> C
  *
  * In category of types and functions we have only one category, so Profunctor P is C' x C => C
  *
  * In programming: Profunctor abstract over
  * - type constructor with two holes P[_,_]
  * - operation:
  *
  *   def dimap(preA: NewA => A, postB: B => NewB): P[A, B] => P[NewA, NewB]
  *
  * that given P[A,B] and two functions:
  * - first apply before first type of P converting NewA => A (ast as contravariant functor)
  * - second  `postB` apply after second type of P converting B => NewB (act as (covariant) functor)
  *
  * Alternatively we can define Profunctor not using dimap but using two separate functions:
  *
  * def lmap(f: AA => A): P[A,C] => P[AA,C] = dimap(f,identity[C])
  * def rmap(f: B => BB): P[A,B] => P[A,BB] = dimap(identity[A], f)
  *
  * Profunctors in Haskell were explored by sifpe at blog A Neighborhood of Infinity in post [Profunctors in Haskell](http://blog.sigfpe.com/2011/07/profunctors-in-haskell.html)
  * Implemented in Haskell: [ekmett/profunctors](https://github.com/ekmett/profunctors)
  *
  * Intuition:
  * 1) Container[KeyT,ValueT] is a container with values of ValueT and
  * 2) Hom functor
  * 3) Relation[Source,Target]  C x C' -> Set if we have empty set then they are not related, if non empty then there is relation
  *  and each element of the set is a proof
  *
  * Laws:
  * - if we define Profunctor using dimap:
  *   dimap id id == id
  *   dimap (f . g) (h . i) == dimap g h . dimap f i
  *
  * Second law we get for free by parametricity.
  *
  * - if specify lmap or rmap
  *  lmap id ≡ id
  *  rmap id ≡ id
  *  lmap (f . g) ≡ lmap g . lmap f
  *  rmap (f . g) ≡ rmap f . rmap g
  *
  *  Last two laws we get for free by parametricity.
  *
  * - if specify both (in addition to law for dimap and laws for lmap:
  *  dimap f g ≡ lmap f . rmap g
  */
trait Profunctor[P[_, _]] {
  def dimap[X,Y,Z,W](ab: X => Y, cd: Z => W): P[Y, Z] => P[X, W]

  // derived methods
  def lmap[A,B,C](f: A => B): P[B,C] => P[A,C] = dimap[A,B,C,C](f,identity[C])
  def rmap[A,B,C](f: B => C): P[A,B] => P[A,C] = dimap[A,A,B,C](identity[A], f)
}

object ProfunctorInstance {
  trait Function1Profunctor extends Profunctor[Function1] {
    def dimap[X, Y, Z, W](f: X => Y, g: Z => W): (Y => Z) => (X => W) = h => f andThen (g compose h)
    override def lmap[A,B,C](f: A => B): (B => C) => (A => C) = f andThen
    override def rmap[A,B,C](f: B => C): (A => B) => (A => C) = f compose
  }

  val function1: Profunctor[Function1] = new Profunctor[Function1] with Function1Profunctor {}
}
