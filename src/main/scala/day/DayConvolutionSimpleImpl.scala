package day

import comonad.ComonadSimpleImpl.Comonad
import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

object DayConvolutionSimpleImpl {

  // data Day f g a where
  //  Day :: (x -> y -> a) -> f x -> g y -> Day f g a
  trait DayConvolution[F[_], G[_], A] {
    type X
    type Y
    val fx: F[X]
    val gy: G[Y]
    def xya: X => Y => A
  }

  // day :: f (a -> b) -> g a -> Day f g b
  // day fa gb = Day fa gb id
def day[F[_], G[_], A, B](fab: F[A => B], ga: G[A]): DayConvolution[F, G, B] = new DayConvolution[F, G, B] {
  type X = A=>B
  type Y = A
  val fx: F[X] = fab
  val gy: G[Y] = ga
  def xya: X => Y => B = identity[A => B]
}

  // instance Functor (Day f g) where
  //  fmap f (Day fb gc bca) = Day fb gc $ \b c -> f (bca b c)
def functorDay[F[_], G[_]]: Functor[DayConvolution[F, G, ?]] = new Functor[DayConvolution[F, G, ?]] {
  def map[C, D](d: DayConvolution[F, G, C])(f: C => D): DayConvolution[F, G, D] =
    new DayConvolution[F, G, D] {
      type X = d.X
      type Y = d.Y
      val fx: F[X] = d.fx
      val gy: G[Y] = d.gy

      def xya: X => Y => D = x => y => f(d.xya(x)(y))
    }
}

  def comonadDay[F[_], G[_]](implicit CF: Comonad[F], CG: Comonad[G]): Comonad[DayConvolution[F, G, ?]] =
    new Comonad[DayConvolution[F, G, ?]] {
      def extract[C](w: DayConvolution[F, G, C]): C = w.xya(CF.extract(w.fx))( CG.extract(w.gy))

      def duplicate[C](wa: DayConvolution[F, G, C]): DayConvolution[F, G, DayConvolution[F, G, C]] = ???
//        new DayConvolution[F, G, DayConvolution[F, G, C]] {
//          type X = wa.X
//          type Y = wa.Y
//          val fx: F[X] = wa.fx
//          val gy: G[Y] = wa.gy
//
//          def xya: X => Y => DayConvolution[F, G, C] = x => y => wa
//        }

      def map[C, D](d: DayConvolution[F, G, C])(f: C => D): DayConvolution[F, G, D] =
        new DayConvolution[F, G, D] {
          type X = d.X
          type Y = d.Y
          val fx: F[X] = d.fx
          val gy: G[Y] = d.gy

          def xya: X => Y => D = x => y => f(d.xya(x)(y))
        }
    }
}
