package educational.category_theory.two.bifunctors

trait Joker[F[_,_]] {
  def map[A,B,BB](fa: F[A,B])(g: B => BB): F[A,BB]
}

trait JokerLaws[P[_,_]] extends Joker[P] {

  def mapDistributeOverCompositionLaw[A,B,C,X](fa: P[X,A], f: B => C, g: A => B): Boolean = {
    val v1: P[X,C] = map(fa)(f compose g)
    val w1: P[X,B] = map(fa)(g)
    val w2: P[X,C] = map(w1)(f)
    v1 == w2
  }

  def mapIdentityLaw[A,B](fa: P[A,B]): Boolean = {
    map(fa)(identity[B]) == fa
  }
}
