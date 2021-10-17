package educational.types

import educational.category_theory.{Comonad, Monad}

object Id {
  type Id[+A] = A

  trait ComonadAux[W[_]] extends Comonad[W] {
    override def duplicate[A](wa: W[A]): W[W[A]] = extend(wa)(identity)
  }

  implicit val travId = new Monad[Id] with ComonadAux[Id] {
    override def pure[A](value: A): Id[A] = value
    override def ap[A, B](ff: Id[A => B])(fa: Id[A]): Id[B] = ff(fa)
    override def map[A, B](x: Id[A])(f: A => B): Id[B] = f(x)
    override def flatMap[A, B](ma: Id[A])(f: A => Id[B]): Id[B] = f(ma)
    override def extract[A](w: Id[A]): A = w
    override def extend[A, B](w: Id[A])(f: Id[A] => B): Id[B] = f(w)
  }
}
