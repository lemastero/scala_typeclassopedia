package educational

case class Reader[C, V](run: C => V)

object Reader {
  def readerMonad[C] = new Monad[Reader[C,?]] {
    def pure[A](a: A): Reader[C, A] = new Reader(_ => a)
    def flatMap[A, B](ma: Reader[C, A])(f: A => Reader[C, B]): Reader[C, B] = ???
  }

def readerContra[V]: Contravariant[Reader[?, V]] = new Contravariant[Reader[?, V]] {
  def contramap[A, B](fa: Reader[A, V])(f: B => A): Reader[B, V] = Reader(f andThen fa.run)
}
}
