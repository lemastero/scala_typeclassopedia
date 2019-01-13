package profunctor.closed

import profunctor.Profunctor

/**
  * Laws:
  *
  * lmap (. f) . closed == rmap (. f) . closed
  * closed . closed == dimap uncurry curry . closed
  * dimap const ($()) . closed == id
  */
trait Closed[P[_,_]] extends Profunctor[P] {
  def closed[A,B,X](pab: P[A,B]): P[X=>A, X=>B]
}

object ClosedInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Closed.html#i:Closed
  // TODO
}
