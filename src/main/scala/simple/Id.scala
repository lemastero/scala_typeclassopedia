package simple /* naming package identity may cause very interesting issues :) */

import educational.Applicative

case class Id[A](value: A)

object IdType {
  type Id[A] = A
  implicit val travId: Applicative[Id] = new Applicative[Id] {
    override def pure[A](value: A): Id[A] = value
    override def ap[A, B](ff: Id[A => B])(fa: Id[A]): Id[B] = ff(fa)
    override def map[A, B](x: Id[A])(f: A => B): Id[B] = f(x)
  }
}
