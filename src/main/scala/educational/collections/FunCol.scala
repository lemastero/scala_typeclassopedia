package educational.collections

case class MultiSetFun[-A](exists: A => Int)
case class SetFu[-A](exists: A => Boolean)
case class ListFun[+A](get: Int => Option[A], exists: Int => Boolean)
case class MapFun[-I,+A](get: I => Option[A], exists: I => Boolean)