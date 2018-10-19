package yoneda

import functor.FunctorSimpleImpl.Functor

import scala.language.higherKinds

// newtype Yoneda f a = Yoneda (forall r. (a -> r) -> f r)
trait Yoneda[F[_], A] {
  def run[R](f: A => R): F[R]
}

object Yoneda {

  /*
    instance Functor (Yoneda f) where
    fmap f (Yoneda m) = Yoneda (\k -> m (k . f))
  */
  def yonedaFunctor[F[_]]: Functor[Yoneda[F, ?]] =
    new Functor[Yoneda[F, ?]] {
      def map[A, B](fa: Yoneda[F, A])(f: A => B): Yoneda[F, B] =
        new Yoneda[F, B] {
          def run[C](f2: B => C): F[C] = fa.run(f andThen f2)
        }
    }

  //liftYoneda :: Functor f => f a -> Yoneda f a
  //liftYoneda a = Yoneda (\f -> fmap f a)
  def liftYoneda[F[_], A](fa: F[A])(implicit FunctorF: Functor[F]): Yoneda[F, A] =
    new Yoneda[F, A] {
      def run[R2](f: A => R2): F[R2] = FunctorF.map(fa)(f)
    }

  //lowerYoneda :: Yoneda f a -> f a
  //lowerYoneda (Yoneda f) = f id
  def lowerYoneda[F[_], A](y: Yoneda[F, A]): F[A] = y.run(identity[A])
}