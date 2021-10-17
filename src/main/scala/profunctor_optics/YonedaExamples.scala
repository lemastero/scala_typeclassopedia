package profunctor_optics

import educational.category_theory.{Functor, Invariant}
import educational.category_theory.higher.~>
import educational.types.Reader.Reader

object YonedaExamples extends App {

  trait ~[A, B] {
    def to: B => A
    def from: A => B
  }

  def isoInvariant[C]: Invariant[~[*,C]] = new Invariant[~[*,C]] {
    override def imap[A, B](fa: A ~ C)(ab: A => B, ba: B => A): B ~ C = new ~[B,C]{
      def to: C => B = fa.to andThen ab
      def from: B => C = ba andThen fa.from
    }
  }

  abstract class Yo[F[+_], A] {
    def runYo(): Reader[A, *] ~> F[*]
  }

  type YonedaLemma[F[+_], A] = Yo[F, A] ~ F[A]
  def yonedaLemma[F[+_], A](implicit FF: Functor[F]): YonedaLemma[F, A] =
    new YonedaLemma[F, A] {
      def to: F[A] => Yo[F, A] = fa =>
        new Yo[F, A] {
          def runYo(): (A => *) ~> F =
            λ[(A => *) ~> F](atox => FF.map(fa)(atox))
        }

      def from: Yo[F, A] => F[A] =
        (fa: Yo[F, A]) => {
          val raf: Reader[A, *] ~> F = fa.runYo()
          raf.apply(identity[A])
        }
    }
}
