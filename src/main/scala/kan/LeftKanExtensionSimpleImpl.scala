package kan

import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

object LeftKanExtensionSimpleImpl {

  trait Lan[F[_], H[_], A] {
    type B
    val hb: H[B]
    def f: F[B] => A
  }

  object Lan {
    def apply[F[_], H[_], A, BB](hhb: H[BB], ff: F[BB] => A): Lan[F,H,A] = new Lan[F,H,A] {
      type B = BB
      val hb: H[B] = hhb
      def f: F[B] => A = ff
    }

    def lanFunctor[F[_], H[_]]: Functor[Lan[F, H, ?]] = new Functor[Lan[F, H, ?]]() {
      def map[A, X](x: Lan[F, H, A])(fax: A => X): Lan[F, H, X] = {
        new Lan[F, H, X] {
          type B = x.B
          val hb: H[B] = x.hb

          def f: F[B] => X = x.f andThen fax
        }
      }
    }
  }
}
