package educational.category_theory.two.profunctor

import educational.category_theory.Functor
import educational.data.{CoKleisli, Kleisli}

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
trait Profunctor[=>:[_, _]] {
  def dimap[S,T,A,B](pab: A=>:B)(ab: S => A, cd: B => T): S=>:T

  // derived methods
  def lmap[S,A,B](pab: A =>: B)(f: S => A): S =>: B = dimap[S,B,A,B](pab)(f,identity[B])
  def rmap[A,B,T](pab: A =>: B)(f: B => T): A =>: T = dimap[A,T,A,B](pab)(identity[A],f)
}

trait ProfunctorLaws[=>:[_, _]] extends Profunctor[=>:] {

  // dimap id id == id
  def dimapIdentity[A,B](p: A =>: B): Boolean = {
    //          dimap(id, id)
    // P[A,B] ================> P[A,B]
    dimap(p)(identity[A], identity[B]) == p
  }

  // dimap (f . g) (h . i) == dimap g h . dimap f i
  def dimapComposition[A,B,C,D,E,F](pad: A=>:D, fcb: C => B, fba: B => A, fde: D => E, fef: E => F): Boolean = {
    //          dimap B=>A D=>E
    // P[A,D] ===================> F[B,E]
    val pbe: B =>: E = dimap(pad)(fba,fde)
    //          dimap C=>B E=>F
    // P[B,E] ====================> P[C,F]
    val l: C =>: F = dimap(pbe)(fcb,fef)

    val fca: C => A = fba compose fcb
    val fdf: D => F = fef compose fde
    //         dimap C=>A D=> F
    // P[A,D] ===================> P[C,F]
    val r: C =>: F = dimap(pad)(fca, fdf)

    l == r
  }
}

object ProfunctorInstance {
  trait Function1Profunctor extends Profunctor[Function1] {
    override def dimap[S,T,A,B](pab: A => B)(f: S => A, g: B => T): S => T = g compose (f andThen pab)
    override def lmap[S,A,B](pab: A => B)(f: S => A): S => B = f andThen pab
    override def rmap[A,B,T](pab: A => B)(g: B => T): A => T = g compose pab
  }

  val function1: Profunctor[Function1] = new Profunctor[Function1] with Function1Profunctor {}

  def cokleisliProfunctor[M[_]]: Profunctor[CoKleisli[M,*,*]] = new Profunctor[CoKleisli[M,*,*]] {
    def dimap[S,T,A,B](pab: CoKleisli[M,A,B])(ab: S => A, cd: B => T): CoKleisli[M,S,T] = ???
  }
}
