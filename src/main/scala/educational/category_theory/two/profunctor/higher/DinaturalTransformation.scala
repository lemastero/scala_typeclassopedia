package educational.category_theory.two.profunctor.higher

/**
  * Dinatural Transformation is a function that change one Profunctor P into another one Q
  * without modifying the content.
  *
  * It is Equivalent to Natural Transformation between two Functors (but for Profunctors).
  *
  * Laws:
  *  rmap f . dinat . lmap f = lmap f . dinat . rmap f
  *
  * Do we get it for free by parametricity?
  */
trait DinaturalTransformation[P[_,_],Q[_,_]]{
  def dinat[A,B](p: P[A,B]): Q[A,B]
}
