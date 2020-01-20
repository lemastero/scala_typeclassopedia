package educational.category_theory.two.profunctor.strong

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.higher.DinaturalTransformation

// TODO adjunction Pastro -| Tambara
// Pastro p ~ exists z. Costar ((,)z) Procompose p Procompose Star ((,)z)

trait Pastro[P[_,_],A,B] {
  type X
  type Y
  type Z
  def f1: (Y,Z) => B
  val pxy: P[X,Y]
  def f2: A => (X,Z)
}

/**
  *  Laws
  *
  * pastro (unpastro f) ≡ f
  * unpastro (pastro f) ≡ f
  */
object Pastro {
  def pastro[P[_,_],Q[_,_]](pq: DinaturalTransformation[P,Q])(implicit SQ: Strong[Q]): DinaturalTransformation[Pastro[P,*,*],Q] = ???
  def unpastro[P[_,_],Q[_,_]](pq: DinaturalTransformation[Pastro[P, ?, ?],Q]): DinaturalTransformation[P,Q] = ???
}

object PastroInstances {
  // TODO Profunctor Monad
  // TODO Profunctor Adjunction

  def profuntorPastro[P[_,_]]: Profunctor[Pastro[P,*,*]] = new Profunctor[Pastro[P,*,*]] {
    def dimap[X,W,Y,Z](pab: Pastro[P,Y,Z])(ab: X => Y, cd: Z => W):Pastro[P,X,W] = ???
  }
}
