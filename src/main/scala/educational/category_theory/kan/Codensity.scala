package educational.category_theory.kan

import educational.category_theory.Monad

trait Codensity[F[_], A] {
  def run[B](f: A => F[B]): F[B]
}

object CodensityInstances {

  /* Create monad without requiring anything (even F to be Functor!) */
  implicit def codensityMonad[G[_]]: Monad[Codensity[G, *]] =
    new Monad[Codensity[G, *]] {
      override def map[A, B](fa: Codensity[G, A])(f: A => B): Codensity[G, B] =
        new Codensity[G, B] {
          def run[C](f2: B => G[C]): G[C] = fa.run(f andThen f2)
        }

      def pure[A](a: A): Codensity[G, A] =
        new Codensity[G, A] {
          def run[B](f: A => G[B]): G[B] = f(a)
        }

      def flatMap[A, B](c: Codensity[G, A])(f: A => Codensity[G, B]): Codensity[G, B] =
        new Codensity[G, B] {
          def run[C](f2: B => G[C]): G[C] = c.run(a => f(a).run(f2))
        }
    }
}
