package educational.category_theory.higher

import educational.category_theory.Functor

trait ~>[F[_], G[_]] {
  def apply[A](fa: F[A]): G[A]
}

trait NaturalTransformationLaws[F[_], G[_]] extends ~>[F,G] {

  def naturalitySquare[A,B](fa: F[A], ff: ~>[F,G], g: A => B)(implicit FF: Functor[F], FG: Functor[G]): Boolean = {
    val v1: G[A] = ff(fa)
    val v2: G[B] = FG.map(v1)(g)

    val w1: F[B] = FF.map(fa)(g)
    val w2: G[B] = ff(w1)

    v2 == w2
  }
}

case class VerticalComposition[F[_],G[_],H[_]](f: F~>G, g: G~>H) extends ~>[F,H] {
  def apply[A](fa: F[A]): H[A] = g(f(fa))
}

// https://github.com/unktomi/stuff/blob/master/containers/AbstractNonsense.scala#L85-L125
case class HorizontalComposition[F[_], G[_], H[_], I[_]](f: Functor[F], h: Functor[H]) {
  type FG[X] = F[G[X]]
  type HI[X] = H[I[X]]

  def innerThenOuter(n1: F ~> H, n2: G ~> I): FG ~> HI = {
    new ~>[FG,HI] {
      override def apply[X](fg: F[G[X]]): H[I[X]] = {
        val gi: G[X] => I[X] = gx => n2(gx)
        val fi: F[I[X]] = f.map(fg)(gi)
        n1(fi)
      }
    }
  }

  def outerThenInner(n1: F ~> H, n2: G ~>I): FG ~> HI = {
    new ~>[FG, HI] {
      def apply[X](fg: F[G[X]]): H[I[X]] = {
        val gi: G[X] => I[X] = gx => n2(gx)
        val hg: H[G[X]] = n1(fg)
        h.map(hg)(gi)
      }
    }
  }
}

case class IdNat[F[_]]() extends ~>[F,F] {
  def apply[A](fa: F[A]): F[A] = fa
}

object NaturalTransformationExamples {
  val headOption: List ~> Option = new ~>[List,Option] {
    def apply[A](fa: List[A]): Option[A] = fa.headOption
  }
}
