package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.{Profunctor, ProfunctorLaws}
import educational.category_theory.two.profunctor.ProfunctorInstance.Function1Profunctor

import scala.Function.{const, uncurried, untupled}

/**
  * Closed Profunctor
  *
  * Laws:
  *
  * lmap (. f) . closed == rmap (. f) . closed
  * closed . closed == dimap uncurry curry . closed
  * dimap const ($()) . closed == id
  */
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
  def closedClosedEqDimapClosed[A, B](p: A =:> B): Boolean = {
    val l1: (B => A) =:> (B => B) = closed(p)
    val l2: (A => B => A) =:> (A => B => B) = closed(l1)
    val r1: (((A, B)) => A) =:> (((A, B)) => B) = closed[A, B, (A, B)](p)
    val r2: (A => B => A) =:> (A => B => B) = dimap(r1)(uncurry[A, B, A], curry[A, B, B])
    l2 == r2
  }

  // dimap const ($()) . closed ≡ id
  def dimapCloseIsIdentity2[A,B](p: A =:> B): Boolean = {
    val l1: (Unit => A) =:> (Unit => B) = closed(p)
    def foo[YY]: (Unit => YY) => YY = f => f(())
    val l2: A =:> B = dimap(l1)(const[A,Unit],foo)
    l2 == p
  }

  def uncurry[X, Y, Z]: (X => Y => Z) => Tuple2[X, Y] => Z = uncurried(_).tupled
  def curry[X, Y, Z]: (Tuple2[X, Y] => Z) => X => Y => Z = untupled(_).curried
}
