package educational.category_theory.contra

import educational.abstract_algebra.Monoid

trait Divide[F[_]] extends Contravariant[F] {
  def divide[A,B,C](f: A => (B,C), fb: F[B], fc: F[C]): F[A] // contramap2
}

trait DivideLaws[F[_]] extends ContravariantLaws[F] with Divide[F] {

  def delta[A]: A => (A, A) = a => (a, a)

  // divide delta (divide delta m n) o = divide delta m (divide delta n o)
  def divideComposition[A](fa1: F[A], fa2: F[A], fa3: F[A]): Boolean = {
    //                divide(delta)
    //  F[A1], F[A2] ===============> F[A12]
    val fa12: F[A] = divide(delta[A], fa1, fa2)

    //                 divide(delta)
    // F[A12], F[A3] =================> F[A123]
    val l: F[A] = divide( delta[A], fa12, fa3)


    //                divide(delta)
    //  F[A2], F[A3] ===============> F[A23]
    val fa23: F[A] = divide(delta[A], fa2, fa3)

    //                  divide(delta)
    //  F[A1], F[A23] ===============> F[A123]
    val r: F[A] = divide( delta[A], fa1, fa23 )
    l == r
  }

  // TODO more general laws: http://hackage.haskell.org/package/contravariant-1.5/docs/Data-Functor-Contravariant-Divisible.html#g:4
}

object Divide {
  def fun1Divide[R](implicit MR: Monoid[R]): Divide[Function1[*, R]] = new Divide[Function1[*, R]] {
    def divide[A, B, C](f: A => (B, C), fb: B => R, fc: C => R): A => R = a => {
      val (b, c) = f(a)
      MR.combine(fb(b), fc(c))
    }

    def contramap[A, B](fa: Function[A, R])(f: B => A): Function[B, R] = f andThen fa
  }
}
