package educational.category_theory.two.profunctor.traverse

import educational.category_theory.Traverse

// free ProfunctorComonad
trait CofreeTraversing[P[_,_],A,B]{
  def runCofreeTraversing[F[_]](implicit FT: Traverse[F]): P[F[A], F[B]]
}

object CofreeTraversingInstances {
  // instances CofreeTraversing https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Traversing.html#i:CofreeTraversing
  // TODO need ProfunctorFunctor
  // TODO need ProfunctorComonad
  // TODO when P is Profunctor then
  //  - Profunctor
  //  - Strong
  //  - Choice
  //  - Traversing
}
