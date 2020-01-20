package educational.category_theory.kan

import educational.category_theory.Functor
import educational.category_theory.higher.~>
import educational.types.Id.Id

object Yoneda {

  /** Yoneda lemma
    *
    * http://hackage.haskell.org/package/kan-extensions/docs/Data-Functor-Yoneda.html
    * newtype Yoneda f a = Yoneda (forall r. (a -> r) -> f r)
    */
  trait Yoneda[F[_], A] { self =>
    def run[R](f: A => R): F[R]

    // derived method
    def lowerYoneda(): F[A] = run(identity[A])

    /* Yoneda[F] is a right Kan extension of F along the Identity Functor */
    def yonedaToRan: Ran[Id,F,A] = new Ran[Id,F,A] {
      def runRan[B](f: A => Id[B]): F[B] = run(a => f(a))
    }

    def hoistYoneda[G[_]](fg: F~>G): Yoneda[G,A] = new Yoneda[G,A] {
      def run[R](f: A => R): G[R] = fg(self.run(f))
    }
  }

  object Yoneda {

    def liftYoneda[F[_], A](fa: F[A])(implicit FunctorF: Functor[F]): Yoneda[F, A] =
      new Yoneda[F, A] {
        def run[R2](f: A => R2): F[R2] = FunctorF.map(fa)(f)
      }

    def ranToYoneda[F[_],A](r: Ran[Id,F,A]): Yoneda[F, A] = new Yoneda[F,A] {
      def run[R](f: A => R): F[R] = r.runRan(a => f(a))
    }

    /**
      * Yoneda is a Funcor for free
      *
      * It is kind of Free Functor as we need Functor when we create Yoneda.
      * But we don't use it to define Functor.
      */
    def yonedaFunctor[F[_]]: Functor[Yoneda[F, ?]] =
      new Functor[Yoneda[F, ?]] {
        def map[A, B](fa: Yoneda[F, A])(f: A => B): Yoneda[F, B] =
          new Yoneda[F, B] {
            def run[C](f2: B => C): F[C] = fa.run(f andThen f2)
          }
      }
  }
}
