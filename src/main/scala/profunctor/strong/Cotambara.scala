package profunctor.strong

import profunctor.higher.DinaturalTransformation

trait Cotambara[Q[_,_],A,B]{
  type R[_,_]
  def CR: Costrong[R]
  def rq: DinaturalTransformation[R,Q]
  def rab: R[A,B]
}
