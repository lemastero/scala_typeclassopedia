package reader

import cats.Monad

class ReaderMonadInstance {

  implicit def readerInstance[In]: Monad[Reader[In, ?]] = new Monad[Reader[In, ?]] {

    override def flatMap[A, B](fa: Reader[In, A])(f: A => Reader[In, B]): Reader[In, B] =
      fa.flatMap(f)

    override def tailRecM[A, B](a: A)(f: A => Reader[In, Either[A, B]]) = ???

    override def pure[A](x: A): Reader[In, A] = Reader(_ => x)
  }
}
