package free

import scala.language.higherKinds

object FreeMonadSimpleImpl {

  /* copied from: http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/ */
  sealed trait Free[F[_],A]
  case class Return[F[_],A](a: A) extends Free[F,A]
  case class Suspend[F[_],A](s: F[Free[F,A]]) extends Free[F,A]
}
