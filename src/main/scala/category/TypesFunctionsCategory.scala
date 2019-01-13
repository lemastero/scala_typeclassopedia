package category

object TypesFunctionsCategory extends Category[Function1] {
  def id[A]: A => A = identity[A]
  def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f
}
