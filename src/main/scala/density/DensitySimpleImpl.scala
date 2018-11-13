package density

import comonad.ComonadSimpleImpl.Comonad
import functor.FunctorSimpleImpl.Functor
import kan.LeftKanExtensionSimpleImpl.Lan

import scala.language.higherKinds

object DensitySimpleImpl {

  /**
    * Density Comonad for a Functor.
    *
    * It is left Kan extension of a Functor along itself (Lan f f).
    */
  trait Density[F[_], Y] { self =>
    type X
    val fb: F[X]
    def f: F[X] => Y

    // Derived methods
    def map[A](fab: Y => A): Density[F, A] =
      Density[F,A,X](f andThen fab, fb)

    def densityToLan: Lan[F,F,Y] = new Lan[F,F,Y] {
      type B = X
      val hb: F[B] = fb
      def f: F[B] => Y = self.f
    }
  }

  object Density {
    def apply[F[_], A, B](kba: F[B] => A, kb: F[B]): Density[F, A] = new Density[F, A] {
      type X = B
      val fb: F[X] = kb
      def f: F[X] => A = kba
    }

    /** Density is a Functor for free */
    def functorInstance[K[_]]: Functor[Density[K, ?]] = new Functor[Density[K, ?]] {
      def map[A, B](x: Density[K, A])(fab: A => B): Density[K, B] = Density[K,B,x.X](x.f andThen fab, x.fb)
    }

    /** Density is a Comonad for Free */
    def comonadInstance[K[_]]: Comonad[Density[K, ?]] = new Comonad[Density[K, ?]] {
      def extract[A](w: Density[K, A]): A = w.f(w.fb)
      def duplicate[A](wa: Density[K, A]): Density[K, Density[K, A]] =
        Density[K, Density[K, A], wa.X](kx => Density[K, A, wa.X](wa.f, kx), wa.fb)
      def map[A, B](x: Density[K, A])(f: A => B): Density[K, B] = x.map(f)
    }
  }
}
