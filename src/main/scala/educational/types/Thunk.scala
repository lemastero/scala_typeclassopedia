package educational.types

import cats.Functor

class Thunk {
  type Thunk[+R] = () => R

  val thunkFunctor: Functor[Thunk] = new Functor[Thunk] {
    def map[A, B](fa: Thunk[A])(f: A => B): Thunk[B] = () => f(fa())
  }
}
