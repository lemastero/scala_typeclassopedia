package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.Profunctor

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
  // TODO
}
