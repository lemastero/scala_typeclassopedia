package educational

import scala.language.higherKinds

/*
  class Contravariant f where
    contramap :: (b -> a) -> f a -> f b
 */

trait Contravariant[F[_]] {
  def contramap[A, B](fa: F[A])(f: B => A): F[B]
}


// contramap id = id
// contramap f . contramap g = contramap (g . f)
trait ContravariantLaws[F[_]] extends Contravariant[F]{

  def contramapIdentity[A](fa: F[A]): Boolean = {
    //         contramap(id)
    // F[A]  ================> F[A]
    contramap(fa)(identity[A]) == fa
  }

  def contravariantComposite[A, B, C](fa: F[A], f: B => A, g: C => B): Boolean = {

    //        contramap f
    // F[A] ==============> F[B]
    val fb: F[B] = contramap(fa)(f)

    //        contramap g
    // F[B] ===============> F[C]
    val l: F[C] = contramap(fb)(g)

    //        contramap (g . f)
    // F[A] =====================> F[B]
    val r: F[C] = contramap(fa)(f compose g)

    l == r
  }
}