package educational.types

import educational.category_theory.{Monad, MonoidK, StateMonad}

object State {
  type State[S,A] = S => (A,S)

  trait StateAsMonad[S] extends Monad[State[S,*]] {
    def flatMap[A, B](st: S => (A,S))(f: A => S => (B,S)): S => (B,S) =
      s => {
        val (v, s2) = st(s)
        f(v)(s2)
      }
    def pure[A](v: A): State[S,A] = s => (v, s)
  }

  implicit def stateMonad[S]: Monad[State[S,*]] = new StateAsMonad[S] {}

  implicit def statStateMonad[S]: StateMonad[State[S,*],S] = new StateMonad[State[S,*],S] with StateAsMonad[S]  {
    def update: (S => S) => State[S, S] = f => s => (s, f(s))
  }

  // parametrised state-transformer monad
  type StateM[F[_],S,A] = S => F[(A,S)]

  def statemStateMonad[F[_],S](implicit FM: Monad[F]): StateMonad[StateM[F,S,*],S] = new StateMonad[StateM[F,S,*],S] {
    override def update: (S => S) => StateM[F, S, S] = f => s => FM.pure((s, f(s)))

    def flatMap[A, B](stm: StateM[F, S, A])(f: A => StateM[F, S, B]): StateM[F, S, B] = s => {
      val f1: F[(A, S)] = stm(s)
      FM.flatMap(f1){ case (v,s2) =>
        f(v)(s2)
      }
    }
    def pure[A](a: A): StateM[F, S, A] = s => FM.pure((a,s))
  }

  implicit def statemMonoidK[F[_],S](implicit FM: MonoidK[F]): MonoidK[StateM[F,S,*]] = new MonoidK[StateM[F,S,*]] {
    override def empty[A]: StateM[F, S, A] = _ => FM.empty[(A,S)]
    override def combine[A](lhs: StateM[F, S, A], rhs: StateM[F, S, A]): StateM[F, S, A] =
      s => FM.combine(lhs(s),rhs(s))
  }
}
