package profunctor.traverse

import traverse.TraverseSimpleImpl.Traverse

trait FreeTraversing[P[_,_],A,B] {
  type F[_]
  type X
  type Y
  val FT: Traverse[F]
  def fyb: F[Y] => B
  def pxy: P[X,Y]
  def afx: A => F[X]
}

object FreeTraversingInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Traversing.html#i:FreeTraversing
  // TODO need FunctorProfunctor and MonadProfunctor
  // TODO Profunctor/Strong/Choice/Traversing for free
}
