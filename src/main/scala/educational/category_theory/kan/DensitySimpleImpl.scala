package educational.category_theory.kan

import educational.category_theory.kan.LeftKanExtension.Lan
import educational.category_theory.{Comonad, Functor}

object DensitySimpleImpl {

  /**
    * Density Comonad for a Functor.
    *
    * It is left Kan extension of a Functor along itself (Lan f f).
    */
  trait Density[F[_], A] { self =>
    type Z
    val fb: F[Z]
    def f: F[Z] => A

    // Derived methods
    def map[B](fab: A => B): Density[F, B] =
      Density[F,B,Z](f andThen fab, fb)

    def densityToLan: Lan[F,F,A] = Lan[F,F,A,Z](fb, self.f)

    def densityToCoyoneda: Coyoneda[F,Z] = Coyoneda[F,Z,Z](identity[Z],fb) // TODO is it lawfull Coyoneda ?
  }

  object Density {
    def apply[F[_], A, B](kba: F[B] => A, kb: F[B]): Density[F, A] = new Density[F, A] {
      type Z = B
      val fb: F[Z] = kb
      def f: F[Z] => A = kba
    }

    /** Density is a Functor for free */
    def functorInstance[K[_]]: Functor[Density[K, ?]] = new Functor[Density[K, ?]] {
      def map[A, B](x: Density[K, A])(fab: A => B): Density[K, B] = Density[K,B,x.Z](x.f andThen fab, x.fb)
    }

    /** Density is a Comonad for Free */
    def comonadInstance[K[_]]: Comonad[Density[K, ?]] = new Comonad[Density[K, ?]] {
      def extract[A](w: Density[K, A]): A = w.f(w.fb)
      def duplicate[A](wa: Density[K, A]): Density[K, Density[K, A]] =
        Density[K, Density[K, A], wa.Z](kx => Density[K, A, wa.Z](wa.f, kx), wa.fb)
      def map[A, B](x: Density[K, A])(f: A => B): Density[K, B] = x.map(f)
    }
  }
}
