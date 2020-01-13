package educational.data

trait Show[A] {
  def asString(a: A): String
}
