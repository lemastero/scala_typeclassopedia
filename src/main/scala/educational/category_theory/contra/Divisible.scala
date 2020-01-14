package educational.category_theory.contra

import educational.abstract_algebra.Monoid

/*
George Wilson - The Extended Functor Family: https://www.youtube.com/watch?v=JUVMiRRq6wU

Divisible is contravariant for Applicative:
 https://hackage.haskell.org/package/contravariant-1.4/docs/Data-Functor-Contravariant-Divisible.html
 https://stackoverflow.com/questions/32059754/are-there-useful-applications-for-the-divisible-type-class
*/
trait Divisible[F[_]] extends Divide[F] {
  def conquer[A]: F[A] // unit
}

trait DivisibleLaws[F[_]] extends DivideLaws[F] with Divisible[F] {

  // divide delta m conquer = m
  def rightIdentity[A](fa: F[A]): Boolean = {
    //                 divide(delta)
    // F[A], conquer ===============> F[A]
    val l: F[A] = divide(delta, fa, conquer[A])

    l == fa
  }

  // divide delta conquer m = m
  def leftIdentity[A](fa: F[A]): Boolean = {
    //                 divide(delta)
    // conquer, F[A] ===============> F[A]
    val l: F[A] = divide(delta, conquer[A], fa)

    l == fa
  }

  // TODO more eneral laws: http://hackage.haskell.org/package/contravariant-1.5/docs/Data-Functor-Contravariant-Divisible.html#g:4
}

object Divisible {

  def fun1Divisible[R](implicit MR: Monoid[R]): Divisible[Function1[*, R]] = new Divisible[Function1[*, R]] {
    def divide[A, B, C](f: A => (B, C), fb: B => R, fc: C => R): A => R = a => {
      val (b, c) = f(a)
      MR.combine(fb(b), fc(c))
    }
    def contramap[A, B](fa: A => R)(f: B => A): B => R = f andThen fa
    def conquer[A]: A => R = _ => MR.empty
  }
}