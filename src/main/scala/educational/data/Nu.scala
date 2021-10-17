package educational.data

trait Nu[F[_]] {
  type A
  val a: A
  val unNu: A => F[A]
}
