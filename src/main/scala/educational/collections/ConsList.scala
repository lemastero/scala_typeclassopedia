package educational.collections

sealed trait ConsList[+T]
case object ConsNil$ extends ConsList[Nothing]
case class Cons[T](h: T, t: ConsList[T]) extends ConsList[T]
