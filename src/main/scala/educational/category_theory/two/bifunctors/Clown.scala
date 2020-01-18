package educational.category_theory.two.bifunctors

trait Clown[F[_,_]] {
  def mapLeft[A,AA,B](fa: F[A,B])(g: A => AA): F[AA,B]
}

trait ClownLaws[P[_,_]] extends Clown[P] {

  def mapLeftDistributeOverCompositionLaw[A,B,C,X](fa: P[A,X], f: B => C, g: A => B): Boolean = {
    val v1: P[C,X] = mapLeft(fa)(f compose g)
    val w1: P[B,X] = mapLeft(fa)(g)
    val w2: P[C,X] = mapLeft(w1)(f)
    v1 == w2
  }

  def mapLeftIdentityLaw[A,B](fa: P[A,B]): Boolean = {
    mapLeft(fa)(identity[A]) == fa
  }
}
