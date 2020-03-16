package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.ProfunctorLaws
import educational.category_theory.two.profunctor.ProfunctorInstance.Function1Profunctor

// class Profunctor p => Closed p where
//    closed :: p a b -> p (x -> a) (x -> b)
trait Closed[=:>[_,_]] extends Profunctor[=:>] {
  def closed[A,B,C](pab: A =:> B): (C => A) =:> (C => B)
}

object ClosedInstances {
  val Function1Closed: Closed[Function1] = new Closed[Function1] with Function1Profunctor {
    override def closed[A,B,C](f: A => B): (C => A) => C => B = _ andThen f
  }
}

trait ClosedLaws[=:>[_,_]] extends Closed[=:>] with ProfunctorLaws[=:>] {
  // lmap (. f) . closed ≡ rmap (. f) . closed
  def lmapClosedEqRmapClosed[A, B, C, D](p: A =:> B, f: (D => A) => (C => A), g: (D => B) => (C => B)): Boolean = {
    val l1: (C => A) =:> (C => B) = closed(p)
    val l2: (D => A) =:> (C => B) = lmap(l1)(f)

    val r1: (D => A) =:> (D => B) = closed(p)
    val r2: (D => A) =:> (C => B) = rmap(r1)(g)
    l2 == r2 // TODO wh haskell uses 1 method? C == D and it is polymorphic? wired?
  }

  // closed . closed ≡ dimap uncurry curry . closed
  def closedClosedEqDimapClosed[A, B, C, D](p: A =:> B): Boolean = {
    val l1: (C => A) =:> (C => B) = closed(p)
    val l2: (D => C => A) =:> (D => C => B) = closed(l1)

    def uncurry[X, Y, Z]: (X => Y => Z) => Tuple2[X, Y] => Z = Function.uncurried(_).tupled
    def curry[X, Y, Z]: (Tuple2[X, Y] => Z) => X => Y => Z = Function.untupled(_).curried

    val r1: (((D, C)) => A) =:> (((D, C)) => B) = closed[A, B, (D, C)](p)
    val r2: (D => C => A) =:> (D => C => B) = dimap(r1)(uncurry[D, C, A], curry[D, C, B])
    l2 == r2
  }

  // dimap const ($()) . closed ≡ id
  def dimapCloseIsIdentity[A, B, C, D](p: A =:> B): Boolean = {
    val l1: (C => A) =:> (C => B) = closed(p)
    def const[X,Y]: X => (Y => X) = a => _ => a
    def dolar[X,Y]: (X => Y) => Y = f => f(dolar(identity[X])) // TODO this seems impossible
    val l2: A =:> B = dimap(l1)(const[A,C],dolar)
    l2 == p
  }

  // dimap const ($()) . closed ≡ id
  def dimapCloseIsIdentity2[A, B, C, D](p: A =:> B): Boolean = {
    val l1: (C => A) =:> (C => B) = closed(p)
    def const[X,Y]: X => (Y => X) = a => _ => a
    def dolar[X,Y]: (X => Y) => Y = f => f(dolar(identity[X])) // TODO this seems impossible
    val l2: A =:> B = dimap(l1)(const[A,C],dolar)
    l2 == p
  }
}
