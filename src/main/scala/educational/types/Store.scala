package educational.types

import educational.category_theory.Comonad

// store comonad == costate comonad
object Store {
  type Store[S, A] = (S => A, S)

  def storeComonad[S]: Comonad[Store[S,*]] = new Comonad[Store[S,*]] {
    override def extract[A](w: (S => A, S)): A = ??? // { case (f, s) => f(s) }
    override def duplicate[A](wa: (S => A, S)): (S => (S => A, S), S) = ???
    override def map[A, B](fa: (S => A, S))(f: A => B): (S => B, S) = ???
  }
}
