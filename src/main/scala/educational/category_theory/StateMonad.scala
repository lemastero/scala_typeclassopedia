package educational.category_theory

import Function.const

trait StateMonad[F[_],S] extends Monad[F] {
  def update: (S => S) => F[S]
  def set: S => F[S] = s => update( const(s) )
  def fetch: F[S] = update( identity[S] )
}
