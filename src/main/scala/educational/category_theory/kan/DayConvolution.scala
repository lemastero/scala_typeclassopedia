package educational.category_theory.kan

import educational.category_theory.higher.~>
import educational.category_theory.{Applicative, Comonad, Functor}
import educational.types.Id.Id

object DayConvolution {

  /** The Day convolution of two covariant functors.
    *
    * Day convolution provides a monoidal product.
    *
    * Identity functor is the unit of Day convolution on both sides:
    * - intro1(elim1) = id
    * - elim1(intro1) = id
    *
    * - intro2(elim2) = id
    * - elim2(intro2) = id
    *
    * The monoid for Day convolution on the cartesian monoidal structure is symmetric:
    * - map(f)(d.swapped) = swapped(map(f)(d))
    *
    * The associativity of the monoid is witnessed by 'assoc' and 'disassoc'
    * - assoc . disassoc = id
    * - disassoc . assoc = id
    * - fmap f . assoc = assoc . fmap f
    *
    * Naturality of trans1 and trans2:
    * - fmap f . trans1 fg = trans1 fg . fmap f
    * - fmap f . trans2 fg = trans2 fg . fmap f
    */
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

    /** Swap type constructors order */
    def swapped: Day[G, F, A] = new Day[G, F, A] {
      type X = self.Y
      type Y = self.X
      val fx: G[X] = self.gy
      val gy: F[Y] = self.fx
      def xya: (X, Y) => A = (x, y) => self.xya(y, x)
    }

    /** Apply a natural transformation to the left-hand side of a Day convolution. */
    def trans1[H[_]](nat: ~>[F, H]): Day[H, G, A] = new Day[H, G, A] {
      type X = self.X
      type Y = self.Y
      val fx: H[X] = nat.apply(self.fx)
      val gy: G[Y] = self.gy
      def xya: (X, Y) => A = self.xya
    }

    /** Apply a natural transformation to the right-hand side of a Day convolution. */
    def trans2[H[_]](nat: ~>[G, H]): Day[F, H, A] = new Day[F, H, A] {
      type X = self.X
      type Y = self.Y
      val fx: F[X] = self.fx
      val gy: H[Y] = nat.apply(self.gy)
      def xya: (X, Y) => A = self.xya
    }
  }

  object Day {

    /** Construct the Day convolution */
    def day[F[_], G[_], A, B](fab: F[A => B], ga: G[A]): Day[F, G, B] = new Day[F, G, B] {
      type X = A => B
      type Y = A
      val fx: F[X] = fab
      val gy: G[Y] = ga
      def xya: (X, Y) => B = (x, y) => x(y)
    }

    def intro1[F[_], A](fa: F[A]): Day[Id, F, A] = new Day[Id, F, A] {
      type X = Unit
      type Y = A
      val fx: Id[X] = ()
      val gy: F[Y] = fa
      def xya: (X, Y) => A = (_, a) => a
    }

    def intro2[F[_], A](fa: F[A]): Day[F, Id, A] = new Day[F, Id, A] {
      type X = A
      type Y = Unit
      val fx: F[X] = fa
      val gy: Id[Y] = ()
      def xya: (X, Y) => A = (a, _) => a
    }

    /** Collapse to second type constructor if first one is Identity */
    def elim1[F[_], A](d: Day[Id, F, A])(implicit FunF: Functor[F]): F[A] = FunF.map(d.gy)(d.xya(d.fx, _))

    /** Collapse to first type constructor if second one is Identity */
    def elim2[F[_], A](d: Day[F, Id, A])(implicit FunF: Functor[F]): F[A] = FunF.map(d.fx)(d.xya(_, d.gy))

    /** Collapse to type constructor if both of them are the same */
    def dap[F[_], A](d: Day[F, F, A])(implicit AF: Applicative[F]): F[A] = AF.liftA2(d.xya)(d.fx, d.gy)

    def assoc[F[_], G[_], H[_], A, B](d: Day[F, Day[G, H, ?], A]): Day[Day[F, G, ?], H, A] = {
      new Day[Day[F, G, ?], H, A] {
        type X = (d.X, d.gy.X)
        type Y = d.gy.Y
        val fx: Day[F, G, (d.X, d.gy.X)] = new Day[F, G,(d.X, d.gy.X)] {
          type X = d.X
          type Y = d.gy.X
          val fx: F[X] = d.fx
          val gy: G[Y] = d.gy.fx
          def xya: (X, Y) => (d.X, d.gy.X) = (x, y) => (x, y)
        }
        val gy: H[Y] = d.gy.gy
        def xya: (X, Y) => A = (a, e) => d.xya(a._1, d.gy.xya(a._2, e))
      }
    }

    def disassoc[F[_], G[_], H[_], A](d: Day[Day[F, G, ?], H, A]): Day[F, Day[G, H, ?], A] = new Day[F, Day[G, H, ?], A] {
      type X = d.fx.X
      type Y = (d.fx.Y, d.Y)
      val fx: F[X] = d.fx.fx
      val gy:  Day[G, H, (d.fx.Y, d.Y)] = new Day[G, H, (d.fx.Y, d.Y)] {
        type X = d.fx.Y
        type Y = d.Y
        val fx: G[X] = d.fx.gy
        val gy: H[Y] = d.gy
        def xya: (X, Y) => (d.fx.Y, d.Y) = (x, y) => (x,y)
      }
      def xya: (X, Y) => A = (x: d.fx.X, y: (d.fx.Y, d.Y) ) => d.xya(d.fx.xya(x, y._1), y._2)
    }
  }

  object DayInstances {

    /** Functor (for free) for Day convolution */
    def functorDay[F[_], G[_]]: Functor[Day[F, G, ?]] = new Functor[Day[F, G, ?]] {
      def map[C, D](d: Day[F, G, C])(f: C => D): Day[F, G, D] = d.map(f)
    }

    /** Applicative instance for Day convolution */
    def applicativeDay[F[_], G[_]](implicit AF: Applicative[F], AG: Applicative[G]): Applicative[Day[F, G, ?]] = new Applicative[Day[F, G, ?]] {

      def ap[A, B](df: Day[F, G, A => B])(dg: Day[F, G, A]): Day[F, G, B] = {
        new Day[F, G, B] {
          type X = (df.X, dg.X)
          type Y = (df.Y, dg.Y)
          val fx: F[X] = AF.ap(AF.map(df.fx)(a => b => (a, b)) : F[dg.X => (df.X, dg.X)])(dg.fx)
          val gy: G[Y] = AG.ap(AG.map(df.gy)(a => b => (a, b)) : G[dg.Y=> (df.Y, dg.Y)])(dg.gy)
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

        def duplicate[C](wa: Day[F, G, C]): Day[F, G, Day[F, G, C]] = new Day[F, G, Day[F, G, C]] {
          type X = F[wa.X]
          type Y = G[wa.Y]
          val fx: F[X] = CF.duplicate(wa.fx)
          val gy: G[Y] = CG.duplicate(wa.gy)
          def xya: (X, Y) => Day[F, G, C] = (x,y) => {new Day[F, G, C] {
            type X = wa.X
            type Y = wa.Y
            val fx: F[X] = x
            val gy: G[Y] = y
            def xya: (X, Y) => C = (x,y) => wa.xya(x,y)
          }}
        }
        def map[C, D](d: Day[F, G, C])(f: C => D): Day[F, G, D] = d.map(f)
      }
  }
}
