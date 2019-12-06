package educational

import profunctor.Profunctor

/**
  * Bifunctor IO.
  *
  * IO models input and output so it is Profunctor.
  * Bifunctor IO model input, output and possible error.
  *
  * Below version from simplified ZIO implementation from
  * John A De Goes @jdegoes tweet
  * https://twitter.com/jdegoes/status/1196536274909323271
  */
final case class BIO[R, E, A](run: R => Either[E, A]) {
  def map[B](f: A => B): BIO[R, E, B] =
    BIO(r => run(r).map(f))

  def flatMap[R1 <: R, E1 >: E, B](f: A => BIO[R1, E1, B]): BIO[R1, E1, B] =
    BIO(r => run(r).flatMap(a => f(a).run(r)))
}

// more in zio/interop-cat
// https://github.com/zio/interop-cats/blob/master/interop-cats/shared/src/main/scala/zio/interop/cats.scala
// https://github.com/zio/interop-cats/blob/master/interop-cats/shared/src/main/scala/zio/interop/catsmtl.scala
object BIOInstances {
  def zioFunctor[R,E]: Functor[BIO[R,E,*]] = new Functor[BIO[R,E,*]] {
    def map[A, B](fa: BIO[R, E, A])(f: A => B): BIO[R, E, B] = fa.map(f)
  }

  trait BIOMonad[R,E] extends Monad[BIO[R,E,*]] {
    def pure[A](a: A): BIO[R, E, A] = BIO(_ => Right(a))
    def flatMap[A, B](fa: BIO[R, E, A])(f: A => BIO[R, E, B]): BIO[R, E, B] = fa.flatMap(f)
  }

  def bioMonad[R,E]: Monad[BIO[R,E,*]] = new BIOMonad[R,E] {}

  def bioContravariant[E,A]: Contravariant[BIO[*, E, A]] = new Contravariant[BIO[*,E,A]] {
    def contramap[R, RR](fa: BIO[R, E, A])(f: RR => R): BIO[RR, E, A] =
      BIO{ rr => fa.run(f(rr)) }
  }

  def bioProfunctor[E]: Profunctor[BIO[*,E,*]] = new Profunctor[BIO[*, E, *]] {
    def dimap[RR, R, A, AA](f: RR => R, g: A => AA): BIO[R, E, A] => BIO[RR, E, AA] = fa =>
      BIO{ rr => fa.map(g).run(f(rr)) }
  }

  def bioBifunctor[R]: Bifunctor[BIO[R, *, *]] = new Bifunctor[BIO[R,*,*]] {
    override def bimap[E, EE, A, AA](f: E => EE, g: A => AA): BIO[R, E, A] => BIO[R, EE, AA] = fa =>
      BIO{ rr =>
        fa.run(rr) match {
          case Right(a) => Right(g(a))
          case Left(b) => Left(f(b))
        }
      }
  }
}
