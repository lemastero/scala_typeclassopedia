package educational

import scala.language.higherKinds

trait Functor[F[_]] {
  def map[A, B](x: F[A])(f: A => B): F[B]
}

trait FunctorLaws[F[_]] extends Functor[F]{

  def covariantIdentity[A](fa: F[A]): Boolean = {
    //           identity
    // F[A] =================> F[A]
    val l: F[A] = map(fa)(identity)
    val r: F[A] = fa
    l == r
  }

  def covariantComposition[A, B, C](fa: F[A], f: A => B, g: B => C): Boolean = {
    //          f
    // F[A] ========> F[B]
    val l1: F[B] = map(fa)(f)
    //           g
    // F[B] =========> F[C]
    val l2: F[C] = map(l1)(g)

    //        f andThen g
    // F[A] ==============> F[C]
    val r: F[C] = map(fa)(f andThen g)

    l2 == r
  }
}
