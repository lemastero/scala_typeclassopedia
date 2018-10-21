package day

import applicative.ApplicativeSimpleImpl.Applicative
import comonad.ComonadSimpleImpl.Comonad
import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

object DayConvolutionSimpleImpl {

  /** Day convolution */
  trait Day[F[_], G[_], A] { self =>
    type X
    type Y
    val fx: F[X]
    val gy: G[Y]
    def xya: (X, Y) => A

    def map[B](f: A => B): Day[F, G, B] =
      new Day[F, G, B] {
        type X = self.X
        type Y = self.Y
        val fx: F[X] = self.fx
        val gy: G[Y] = self.gy
        def xya: (X, Y) => B = (x, y) => f(self.xya(x, y))
      }
  }

  object Day {

    /** Construct the Day convolution */
    def apply[F[_], G[_], A, B](fab: F[A => B], ga: G[A]): Day[F, G, B] = new Day[F, G, B] {
      type X = A=>B
      type Y = A
      val fx: F[X] = fab
      val gy: G[Y] = ga
      def xya: (X, Y) => B = (x,y) => x(y)
    }

    /** Functor (for free) for Day convolution */
    def functorDay[F[_], G[_]]: Functor[Day[F, G, ?]] = new Functor[Day[F, G, ?]] {
      def map[C, D](d: Day[F, G, C])(f: C => D): Day[F, G, D] = d.map(f)
    }

    /** Applicative instance for Day convolution */
    def applicativeDay[F[_], G[_]](implicit AF: Applicative[F], AG: Applicative[G]): Applicative[Day[F, G, ?]] = new Applicative[Day[F, G, ?]] {
      def map[C, D](d: Day[F, G, C])(f: C => D): Day[F, G, D] = d.map(f)

      def apply[A, B](df: Day[F, G, A => B])(dg: Day[F, G, A]): Day[F, G, B] = {
        new Day[F, G, B] {
          type X = (df.X, dg.X)
          type Y = (df.Y, dg.Y)
          val fx: F[X] = AF.apply(AF.map(df.fx)(a => b => (a, b)) : F[dg.X => (df.X, dg.X)])(dg.fx)
          val gy: G[Y] = AG.apply(AG.map(df.gy)(a => b => (a, b)) : G[dg.Y=> (df.Y, dg.Y)])(dg.gy)
          def xya: (X, Y) => B = (a, b) => df.xya(a._1, b._1)(dg.xya(a._2, b._2))
        }
      }

      def pure[A](a: A): Day[F, G, A] = new Day[F, G, A] {
        type X = Unit
        type Y = Unit
        val fx: F[X] = AF.pure(())
        val gy: G[Y] = AG.pure(())
        def xya: (X, Y) => A = (_, _) => a
      }
    }

    /** Comonad instance for Day convolution */
    def comonadDay[F[_], G[_]](implicit CF: Comonad[F], CG: Comonad[G]): Comonad[Day[F, G, ?]] =
      new Comonad[Day[F, G, ?]] {
        def extract[C](w: Day[F, G, C]): C = w.xya(CF.extract(w.fx), CG.extract(w.gy))

        def duplicate[C](wa: Day[F, G, C]): Day[F, G, Day[F, G, C]] = ???

        def map[C, D](d: Day[F, G, C])(f: C => D): Day[F, G, D] = d.map(f)
      }
  }
}
