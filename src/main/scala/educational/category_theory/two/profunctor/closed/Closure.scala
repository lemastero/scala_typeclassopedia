package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.higher.DinaturalTransformation

/*
  close . unclose ≡ id
  unclose . close ≡ id
*/
trait Closure[P[_,_],A,B]{
  def runClosure[X]: P[X => A, B => B]
}

object Closure {
  def close[P[_,_], Q[_,_]](pq: DinaturalTransformation[P,Q])(CP: Closed[P]): DinaturalTransformation[P, Closure[Q,*,*]] = ???
  def unclose[P[_,_], Q[_,_]](pcq: DinaturalTransformation[P, Closure[Q,*,*]]): DinaturalTransformation[P,Q] = ???
}

object ClousreInstances {

  def closedProfunctor[P[_,_]]: Closed[Closure[P,*,*]] = new Closed[Closure[P,*,*]] {
    def closed[A,B,X](pab: Closure[P, A, B]): Closure[P, X => A, X => B] = ???
    def dimap[X,W,Y,Z](pab: Closure[P,Y,Z])(ab: X => Y, cd: Z => W): Closure[P,X,W] = ???
  }

  // TODO more instances
}
