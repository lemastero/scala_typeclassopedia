package educational.types

sealed trait Void {
  def absurd[A]: A
}