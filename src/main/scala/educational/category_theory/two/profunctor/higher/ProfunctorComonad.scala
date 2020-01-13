package educational.category_theory.two.profunctor.higher

import educational.category_theory.two.profunctor.Profunctor

/**
  * Laws
  *
  * proextract . promap f ≡ f . proextract
  * proextract . produplicate ≡ id
  * promap proextract . produplicate ≡ id
  * produplicate . produplicate ≡ promap produplicate . produplicate
  */
trait ProfunctorComonad[T[_]] extends ProfunctorFunctor[T] {
  def proextract[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[P[A,B]]], P]
  def produplicate[P[_,_]](implicit P: Profunctor[P]): DinaturalTransformation[Lambda[(A,B) => T[P[A,B]]], Lambda[(A,B) => T[T[P[A,B]]]]]
}

object ProfunctorComonadInstance{
  // TODO Cotambara
  // TODO Tambara
  // TODO Closure
  // TODO need CotambaraSum
  // TODO TambaraSum
  // TODO CofreeTraversing
  // TODO CofreeMapping
  // TODO Coyoneda
  // TODO Yoneda
  // TODO Rift Ran Cayley
  // TODO Bifunctor Product
  // TODO BIfunctor Tannen
}
