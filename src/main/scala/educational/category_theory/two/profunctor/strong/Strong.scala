package educational.category_theory.two.profunctor.strong

import educational.category_theory.two.profunctor.{Profunctor, ProfunctorInstance}

/**
  * Laws:
  *
  * in term of first:
  *
  * first' ≡ dimap swap swap . second'
  * lmap fst ≡ rmap fst . first'
  * lmap (second f) . first' ≡ rmap (second f) . first
  * first' . first' ≡ dimap assoc unassoc . first' where
  * assoc ((a,b),c) = (a,(b,c))
  * unassoc (a,(b,c)) = ((a,b),c)
  *
  * in term of second:
  *
  * second' ≡ dimap swap swap . first'
  * lmap snd ≡ rmap snd . second'
  * lmap (first f) . second' ≡ rmap (first f) . second'
  * second' . second' ≡ dimap unassoc assoc . second' where
  * assoc ((a,b),c) = (a,(b,c))
  * unassoc (a,(b,c)) = ((a,b),c)
  */
trait Strong[P[_, _]] extends Profunctor[P] {
  def first[X,Y,Z](pab: P[X, Y]): P[(X, Z), (Y, Z)]

  def second[X,Y,Z](pab: P[X, Y]): P[(Z, X), (Z, Y)] = {
    val ds: P[(X, Z), (Y, Z)] => P[(Z, X), (Z, Y)] = dimap(_.swap, _.swap)
    ds(first(pab): P[(X, Z), (Y, Z)])
  }
}

object Strong {
  def uncurry[P[_,_],A,B,C](pa: P[A, B => C])(implicit S: Strong[P]): P[(A,B),C] = {
    S.rmap{bc:(B => C, B) => bc._1(bc._2)}(S.first(pa))
  }
}

object StrongInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Strong.html#i:Strong
  val Function1Strong: Strong[Function1] = new Strong[Function1] with ProfunctorInstance.Function1Profunctor {
    def first[X, Y, Z](f: Function1[X, Y]): Function1[(X,Z), (Y, Z)] = { case (x,z) => (f(x), z) }
  }

  // TODO need Profunctor Yoneda
  // TODO need Profunctor Coyoneda
  // TODO need Profunctor Star
  // TODO need Profunctor Pastro
  // TODO need Profunctor Cayley
  // TODO need Profunctor Compose
  // TODO need FreeMapping
  // TODO need CofreeMapping

  // TODO FreeTraversing

  // TODO need Bifunctor Tannen
  // TODO need Bifunctor Clown
  // TODO need Bifunctor Compose
}
