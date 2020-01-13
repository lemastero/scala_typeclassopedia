package educational.category_theory.two.profunctor.higher

import educational.category_theory.two.profunctor.Profunctor

/**
  * Laws
  *
  * promap f . proreturn ≡ proreturn . f
  * projoin . proreturn ≡ id
  * projoin . promap proreturn ≡ id
  * projoin . projoin ≡ projoin . promap projoin
  */
trait ProfunctorMonad[T[_]] extends ProfunctorFunctor[T] {
  def proreturn[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[P, Lambda[(A,B) => T[P[A,B]]]]
  def projoin[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[T[P[A,B]]]], Lambda[(A,B) => T[P[A,B]]]]
}

object ProfunctorMonadInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Monad.html#i:ProfunctorMonad
  // TODO Profunctor Monad instances
}
