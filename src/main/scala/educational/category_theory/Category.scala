package educational.category_theory

trait Category[Morphism[_,_]] {
  def id[Object]: Morphism[Object,Object]
  def compose[A,B,C](f: Morphism[B,C])(g: Morphism[A,B]): Morphism[A,C]
}

trait CategoryLaws[Morphism[_,_]] extends Category[Morphism] {
  def leftIdentityLaw = ??? // TODO
  def rightIdentityLaw = ??? // TODO
  def compositivityLaw = ??? // TODO
}

object CategoryInstances {
  trait Function1Cat extends Category[Function1] {
    def id[A]: A => A = identity[A]
    def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f
  }

  val scalaProperTypesAndPureFunction1: Category[Function1] = new Function1Cat {}
}
