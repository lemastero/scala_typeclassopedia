package kan

import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

object LeftKanExtensionSimpleImpl {

  trait Lan[G[_], H[_], A] {
    type I
    val v: H[I]
    def f: G[I] => A
  }

  object Lan extends LanInstances {
    def apply[G[_], H[_], A, B](hhb: H[B], ff: G[B] => A): Lan[G,H,A] = new Lan[G,H,A] {
      type I = B
      val v: H[I] = hhb
      def f: G[I] => A = ff
    }
  }

  sealed abstract class LanInstances {
    implicit def lanFunctor[F[_], H[_]]: Functor[Lan[F, H, ?]] = new Functor[Lan[F, H, ?]]() {
      def map[A, X](x: Lan[F, H, A])(fax: A => X): Lan[F, H, X] = {
        new Lan[F, H, X] {
          type I = x.I
          val v: H[I] = x.v

          def f: F[I] => X = x.f andThen fax
        }
      }
    }
  }

  private trait LanFunctor[G[_], H[_]] extends Functor[Lan[G, H, ?]] {
    def map[A, X](x: Lan[G, H, A])(fax: A => X): Lan[G, H, X] = {
      new Lan[G, H, X] {
        type I = x.I
        val v: H[I] = x.v
        def f: G[I] => X = x.f andThen fax
      }
    }
  }
}
