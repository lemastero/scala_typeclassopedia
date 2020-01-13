package educational.category_theory.two.profunctor.higher

import educational.category_theory.two.profunctor.Profunctor

/**
  * Laws:
  *
  * promap f . promap g == promap (f . g)
  * promap id == id
  */
trait ProfunctorFunctor[T[_]] {
  def promap[P[_,_], Q[_,_]](dt: DinaturalTransformation[P,Q])(implicit PP: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[P[A,B]]], Lambda[(A,B) => T[Q[A,B]]]]
}

object ProfunctorFunctorInstances {
  // TODO
}
