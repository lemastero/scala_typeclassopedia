package educational.category_theory

trait Category[K[_,_]] {
  def id[A]: K[A,A]
  def compose[A,B,C](f: K[B,C])(g: K[A,B]): K[A,C]
}

object CategoryInstances {
  val scalaProperTypesAndPureFunction1: Category[Function1] = new Category[Function1] {
    def id[A]: A => A = identity[A]
    def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f
  }
}
