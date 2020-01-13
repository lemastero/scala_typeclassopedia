package selective

import educational.category_theory.{Applicative, Monad}

// TODO WIP
/** Selective Applicatie Functors
  *
  * It is monoid with following tensor
  *
  * data (:|:) f g a where
  * (:|:) :: f (Either x a) -> g (x -> a) -> (:|:) f g a
  *
  * functor products which correspond to Applicative and Monad:
  *
  * data (:+:) f g a where
  * (:+:) :: f x -> g (x -> a) -> (f :+: g) a
  *
  * data (:*:) f g a where
  * (:*:) :: f x -> (x -> g a) -> (:*:) f g a
  *
  * Applicative/Selective/Monad could be written as follows:
  *
  * class Applicative f where
  * pure :: Identity ~> f
  * (<*>) :: f :+: f ~> f
  *
  * class Selective f where
  * pure :: Identity ~> f
  * (<*?) :: f :|: f ~> f
  *
  * class Monad f where
  * return :: Identity ~> f
  * join :: f :*: f ~> f
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
    val v2: F[Either[A,C]] = ap(v1)(fab)
    handle(v2, fac)
  }

  /* Selective handle method can express Applicative apply */
  override def ap[A, B](ff: F[A => B])(fa: F[A]): F[B] =
    handle[A, B](map(fa)(Left.apply), ff)
}

object SelectiveInstance {
  def monadSelective[F[_]](implicit MF: Monad[F]): Selective[F] = new Selective[F] {
    def handle[A, B](fab: F[Either[A, B]], ff: F[A => B]): F[B] = ???
    def pure[A](value: A): F[A] = MF.pure(value)
  }
}
