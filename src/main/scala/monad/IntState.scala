package monad

import cats.Monad

object IntState {

  /** Computation that need state and change it */
  type IntState[T] = Int => (T, Int)

  implicit val instance: Monad[IntState] = new Monad[IntState] {
    override def flatMap[A, B](fa: IntState[A])(f: A => IntState[B]): IntState[B] = {
      (i: Int) =>
        val firstResult: (A, Int) = fa(i)
        val sndRes: IntState[B] = f(firstResult._1)
        sndRes(firstResult._2)
    }

    override def tailRecM[A, B](a: A)(f: A => IntState[Either[A, B]]): IntState[B] = ???

    override def pure[A](x: A): IntState[A] = i => (x, i)
  }
}
