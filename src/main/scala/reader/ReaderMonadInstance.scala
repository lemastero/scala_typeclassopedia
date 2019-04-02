package reader

import cats.{Monad, StackSafeMonad}

class ReaderMonadInstance {

  implicit def readerInstance[In]: Monad[Reader[In, ?]] = new StackSafeMonad[Reader[In, ?]] {

    def flatMap[A, B](fa: Reader[In, A])(f: A => Reader[In, B]): Reader[In, B] =
      fa.flatMap(f)

    def pure[A](x: A): Reader[In, A] = Reader(_ => x)
  }
}
