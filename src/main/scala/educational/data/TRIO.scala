package educational.data

import educational.abstract_algebra.Monoid
import educational.category_theory.contra.{Contravariant, Divide}
import educational.category_theory.two.bifunctors.Bifunctor
import educational.category_theory.{Functor, Monad}
import educational.category_theory.two.profunctor.Profunctor

/**
  * Trifunctor IO models input, output and possibility of errors.
  *
  * Modeling input and output means we have a Profunctor.
  * Fact that we could have error instead of result so output is Either or Bifunctor
  * Bifunctor IO model input, output and possible error.
  *
  * Below version from simplified ZIO implementation from
  * John A De Goes @jdegoes tweet
  * https://twitter.com/jdegoes/status/1196536274909323271
  */
case class TRIO[R,E,A](run: R => Either[E,A]) {
  def map[B](f: A => B): TRIO[R, E, B] =
    TRIO(r => run(r).map(f))

  def flatMap[B](f: A => TRIO[R,E,B]): TRIO[R,E,B] =
    TRIO(r => run(r).flatMap(a => f(a).run(r)))
}

// more in zio/interop-cat
// https://github.com/zio/interop-cats/blob/master/interop-cats/shared/src/main/scala/zio/interop/cats.scala
// https://github.com/zio/interop-cats/blob/master/interop-cats/shared/src/main/scala/zio/interop/catsmtl.scala
object TRIOInstances {
  def trioFunctor[R,E]: Functor[TRIO[R,E,*]] = new Functor[TRIO[R,E,*]] {
    def map[A, B](fa: TRIO[R, E, A])(f: A => B): TRIO[R, E, B] = fa.map(f)
  }

  trait TRIOMonad[R,E] extends Monad[TRIO[R,E,*]] {
    def pure[A](a: A): TRIO[R,E,A] = TRIO(_ => Right(a))
    def flatMap[A, B](fa: TRIO[R, E, A])(f: A => TRIO[R,E,B]): TRIO[R,E,B] = fa.flatMap(f)
  }

  def trioMonad[R,E]: Monad[TRIO[R,E,*]] = new TRIOMonad[R,E] {}

  def trioContravariant[E,A]: Contravariant[TRIO[*, E, A]] = new Contravariant[TRIO[*,E,A]] {
    def contramap[R, RR](fa: TRIO[R, E, A])(f: RR => R): TRIO[RR, E, A] =
      TRIO{ rr => fa.run(f(rr)) }
  }

  def trioDivide[F[_], E, A](implicit ME: Monoid[E], MA: Monoid[A]): Divide[TRIO[*,E,A]] = new Divide[TRIO[*,E,A]] {
    def divide[RR, B, C](f: RR => (B,C), fb: TRIO[B,E,A], fc: TRIO[C,E,A]): TRIO[RR,E,A] = {
      TRIO[RR,E,A]( (rr: RR) => {
        val (b,c) = f(rr)
        val ea: Either[E,A] = fb.run(b)
        val eb: Either[E,A] = fc.run(c)
        (ea, eb) match {
          case (Left(e1), Left(e2)) => Left(ME.combine(e1,e2))
          case (Right(a1), Right(a2)) => Right(MA.combine(a1,a2))
          case (Left(e), _) => Left(e)
          case (_, Left(e)) => Left(e)
        }
      }
      )
    }

    override def contramap[R, RR](fa: TRIO[R, E, A])(f: RR => R): TRIO[RR, E, A] =
      TRIO{ rr => fa.run(f(rr)) }
  }

  def trioProfunctor[E]: Profunctor[TRIO[*,E,*]] = new Profunctor[TRIO[*,E,*]] {
    def dimap[RR,AA,R,A](fa: TRIO[R,E,A])(f: RR => R, g: A => AA): TRIO[RR,E,AA] =
      TRIO{ rr => fa.map(g).run(f(rr)) }
  }

  def trioBifunctor[R]: Bifunctor[TRIO[R, *, *]] = new Bifunctor[TRIO[R,*,*]] {
    override def bimap[E,EE,A,AA](fa: TRIO[R,E,A])(f: E => EE, g: A => AA): TRIO[R,EE,AA] =
      TRIO{ rr =>
        fa.run(rr) match {
          case Right(a) => Right(g(a))
          case Left(b) => Left(f(b))
        }
      }
  }
}
