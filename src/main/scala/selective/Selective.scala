package selective

import applicative.ApplicativeSimpleImpl.Applicative
import monad.MonadSimpleImplementation.Monad

/** Selective Applicatie Functors
  *
  * Abstraction that
  *
  * @see [[https://blogs.ncl.ac.uk/andreymokhov/selective/ Andrey Mokhov Selective applicative functors]]
  */
trait Selective[F[_]] extends Applicative[F] {

  /**
    * Handle errors.
    *
    * Apply handler A => B if you provide Left[A] but skip the handler (and Effect) if you provide Right[B]
   */
  def handle[A, B](fab: F[Either[A, B]], ff: F[A => B]): F[B]

  /**
    * Handle both cases of Either
    *
    * TODO is this correct implementation ???
   */
  def select[A, B, C](fab: F[Either[A, B]], fac: F[A => C], fbc: F[B => C]): F[C] = {
    val v0 : (B => C) => Either[A, B] => Either[A,C] = bc => _.map(bc)
    val v1: F[Either[A, B] => Either[A,C]] = map(fbc)(v0)
    val v2: F[Either[A,C]] = apply(v1)(fab)
    handle(v2, fac)
  }

  /* Selective handle method can express Applicative apply */
  override def apply[A, B](ff: F[A => B])(fa: F[A]): F[B] =
    handle[A, B](map(fa)(Left.apply), ff)
}

object SelectiveInstance {
  def monadSelective[F[_]](implicit MF: Monad[F]): Selective[F] = new Selective[F] {
    def handle[A, B](fab: F[Either[A, B]], ff: F[A => B]): F[B] = ???
    def pure[A](value: A): F[A] = MF.pure(value)
    def map[A, B](x: F[A])(f: A => B): F[B] = MF.map(x)(f)
  }
}
