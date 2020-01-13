package educational.data

import educational.category_theory.contra.Contravariant
import educational.category_theory.{Functor, Monad}

case class Reader[-R, +V](run: R => V)

object Reader {

  def readerFunctor[C]: Functor[Reader[C, ?]] = new Functor[Reader[C, *]] {
    def map[A, B](x: Reader[C, A])(f: A => B): Reader[C, B] = Reader(x.run andThen f)
  }

  def readerMonad[C]: Monad[Reader[C, ?]] = new Monad[Reader[C,*]] {
    override def map[A, B](x: Reader[C, A])(f: A => B): Reader[C, B] = Reader(x.run andThen f)
    def pure[A](a: A): Reader[C, A] = new Reader(_ => a)
    def flatMap[A, B](ma: Reader[C, A])(f: A => Reader[C, B]): Reader[C, B] = ??? // TODO
  }

  def readerContra[V]: Contravariant[Reader[?, V]] = new Contravariant[Reader[*, V]] {
    def contramap[A, B](fa: Reader[A, V])(f: B => A): Reader[B, V] = Reader(f andThen fa.run)
  }
}

/*
case class Reader[-In, +R](run: In => R) {

  def map[R2](f: R => R2): Reader[In, R2] =
    Reader(run andThen f)

  def flatMap[R2, In2 <: In](f: R => Reader[In2, R2]): Reader[In2, R2] =
    Reader(x => f(run(x)).run(x))
}
*/
