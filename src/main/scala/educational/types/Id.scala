package educational.types

import educational.category_theory.Applicative

object Id {
  type Id[A] = A

  implicit val travId: Applicative[Id] = new Applicative[Id] {
    override def pure[A](value: A): Id[A] = value
    override def ap[A, B](ff: Id[A => B])(fa: Id[A]): Id[B] = ff(fa)
    override def map[A, B](x: Id[A])(f: A => B): Id[B] = f(x)
  }
}
