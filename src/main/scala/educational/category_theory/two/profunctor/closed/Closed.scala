package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.ProfunctorLaws
import educational.category_theory.two.profunctor.ProfunctorInstance.Function1Profunctor
import Function.const
import Function.uncurried
import Function.untupled

// https://ncatlab.org/nlab/show/closed+category

// class Profunctor p => Closed p where
//    closed :: p a b -> p (x -> a) (x -> b)
/**
  * Laws:
  *
  * lmap (. f) . closed == rmap (. f) . closed
  * closed . closed == dimap uncurry curry . closed
  * dimap const ($()) . closed == id
  */
trait Closed[=>:[_,_]] extends Profunctor[=>:] {
  def closed[A,B,C](pab: A =>: B): (C => A) =>: (C => B)
}

object ClosedInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Closed.html#i:Closed
  val Function1Closed: Closed[Function1] = new Closed[Function1] with Function1Profunctor {
    override def closed[A,B,C](f: A => B): (C => A) => C => B = _ andThen f
  }
}

trait ClosedLaws[=:>[_,_]] extends Closed[=:>] with ProfunctorLaws[=:>] {
  // lmap (. f) . closed ≡ rmap (. f) . closed
  def lmapClosedEqRmapClosed[A, B, C, D](p: A =:> B, f: (D => A) => (C => A), g: (D => B) => (C => B)): Boolean = {
    val l1: (C => A) =:> (C => B) = closed(p)
    val l2: (D => A) =:> (C => B) = lmap(l1)(f)
    ???
  }
}
