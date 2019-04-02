package free

import scala.language.higherKinds

object FreeMonadSimpleImpl {

  /* from: http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/ */
  object free1 {
    sealed trait Free[F[_], A]
    case class Return[F[_], A](a: A) extends Free[F, A]
    case class Suspend[F[_], A](s: F[Free[F, A]]) extends Free[F, A]
  }

  // from https://www.youtube.com/watch?v=M258zVn4m2M
  object free2 {
    sealed trait Free[F[_], A]
    case class Return[F[_], A](a: A) extends Free[F,A]
    case class Bind[F[_],I,A](i: F[I], k: I => Free[F,A]) extends Free[F,A]
  }
}
