package educational.category_theory.kan

import educational.category_theory.Functor

object LeftKanExtension {

  trait Lan[G[_], H[_], A] {
    type I
    val fb: H[I]
    def f: G[I] => A
  }

  object Lan extends LanInstances {
    def apply[G[_], H[_], A, B](hhb: H[B], ff: G[B] => A): Lan[G,H,A] = new Lan[G,H,A] {
      type I = B
      val fb: H[I] = hhb
      def f: G[I] => A = ff
    }
  }

  sealed abstract class LanInstances {
    implicit def lanFunctor[F[_], H[_]]: Functor[Lan[F, H, ?]] = new Functor[Lan[F, H, ?]]() {
      def map[A, X](x: Lan[F, H, A])(fax: A => X): Lan[F, H, X] = {
        new Lan[F, H, X] {
          type I = x.I
          val fb: H[I] = x.fb
          def f: F[I] => X = x.f andThen fax
        }
      }
    }
  }

  private trait LanFunctor[G[_], H[_]] extends Functor[Lan[G, H, ?]] {
    def map[A, X](x: Lan[G, H, A])(fax: A => X): Lan[G, H, X] = {
      new Lan[G, H, X] {
        type I = x.I
        val fb: H[I] = x.fb
        def f: G[I] => X = x.f andThen fax
      }
    }
  }
}
