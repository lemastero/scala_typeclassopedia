package educational

case class Reader[C, V](run: C => V)

object Reader {

  def readerFunctor[C]: Functor[Reader[C, ?]] = new Functor[Reader[C, ?]] {
    def map[A, B](x: Reader[C, A])(f: A => B): Reader[C, B] = Reader(x.run andThen f)
  }

  def readerMonad[C]: Monad[Reader[C, ?]] = new Monad[Reader[C,?]] {
    override def map[A, B](x: Reader[C, A])(f: A => B): Reader[C, B] = Reader(x.run andThen f)
    def pure[A](a: A): Reader[C, A] = new Reader(_ => a)
    def flatMap[A, B](ma: Reader[C, A])(f: A => Reader[C, B]): Reader[C, B] = ???
  }

  def readerContra[V]: Contravariant[Reader[?, V]] = new Contravariant[Reader[?, V]] {
    def contramap[A, B](fa: Reader[A, V])(f: B => A): Reader[B, V] = Reader(f andThen fa.run)
  }
}
