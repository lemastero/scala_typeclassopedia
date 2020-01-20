package educational.category_theory.two

trait Semicategory[Morphism[_,_]] {
  def compose[A,B,C](f: Morphism[B,C])(g: Morphism[A,B]): Morphism[A,C]
}

trait SemicategoryLaws[M[_,_]] extends Semicategory[M] {

  def compositivityLaw[A,B,C,D](g: M[B,C], f: M[A,B], h:M[C,D]): Boolean = {
    val gf: M[A, C] = compose(g)(f)
    val v2: M[A, D] = compose(h)(gf)

    val hg: M[B, D] = compose(h)(g)
    val w2: M[A, D] = compose(hg)(f)

    v2 == w2
  }
}

trait Category[Morphism[_,_]] extends Semicategory[Morphism] {
  def id[Object]: Morphism[Object,Object]
}

trait CategoryLaws[M[_,_]] extends Category[M] {
  def leftIdentityLaw[A,B](fa: M[A,B]): Boolean = {
    compose(id[B])(fa) == fa
  }

  def rightIdentityLaw[A,B](fa: M[A,B]): Boolean = {
    compose(fa)(id[A]) == fa
  }
}

object CategoryInstances {
  trait Function1Cat extends Category[Function1] {
    def id[A]: A => A = identity[A]
    def compose[A, B, C](f: B => C)(g: A => B): A => C = g andThen f
  }

  val scalaProperTypesAndPureFunction1: Category[Function1] = new Function1Cat {}
}
