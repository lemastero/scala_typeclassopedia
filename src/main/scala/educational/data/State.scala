package educational.data

import educational.category_theory.Monad

case class State[S,A](runState: S => (A,S))

object State {

  implicit def stateMonad[S]: Monad[State[S, *]] = new Monad[State[S, *]] {

    def pure[A](a: A): State[S, A] = State(s => (a, s))

    def flatMap[A, B](ma: State[S, A])(f: A => State[S, B]): State[S, B] = State[S, B](
      s => {
        val (a: A, s2: S) = ma.runState(s)
        f(a).runState(s2)
      }
    )
  }
}
