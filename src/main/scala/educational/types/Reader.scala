package educational.types

import educational.category_theory.Functor
import educational.category_theory.contra.Contravariant

object Reader {
  type Reader[-R, +A] = R => A

  def readerFunctor[C]: Functor[Reader[C, *]] =
    new Functor[Reader[C, *]] {
      def map[A, B](ca: Reader[C, A])(ab: A => B): Reader[C, B] = ca andThen ab
    }

  def readerContra[C]: Contravariant[Reader[*, C]] =
    new Contravariant[Reader[*, C]] {
      def contramap[A, B](fa: Reader[A, C])(ba: B => A): Reader[B, C] =
        ba andThen fa
    }
}
