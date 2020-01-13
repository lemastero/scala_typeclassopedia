package examples

import cats.{Monad, StackSafeMonad}

object IntState {

  /** Computation that need state and change it */
  type IntState[T] = Int => (T, Int)

  implicit val instance: Monad[IntState] = new StackSafeMonad[IntState] {
    def flatMap[A, B](fa: IntState[A])(f: A => IntState[B]): IntState[B] = {
      (i: Int) =>
        val firstResult: (A, Int) = fa(i)
        val sndRes: IntState[B] = f(firstResult._1)
        sndRes(firstResult._2)
    }

    def pure[A](x: A): IntState[A] = i => (x, i)
  }
}
