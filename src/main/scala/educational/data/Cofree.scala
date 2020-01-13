package educational.data

import educational.category_theory.{Comonad, Functor}

case class Cofree[A, F[_]](extract: A, sub: F[Cofree[A, F]])(implicit functor: Functor[F]) {
  def map[B](f: A => B): Cofree[B, F] = Cofree(f(extract), functor.map(sub)(_.map(f)))
  def duplicate: Cofree[Cofree[A, F], F] = Cofree(this, functor.map(sub)(_.duplicate))
  def extend[B](f: Cofree[A, F] => B): Cofree[B, F] = duplicate.map(f) // coKleisi composition
}

case class CofreeList[A](extract: A, sub: List[CofreeList[A]])

object CofreeList {

  def cofreeListComonad: Comonad[CofreeList] = new Comonad[CofreeList] {
    def map[A, B](ca: CofreeList[A])(f: A => B): CofreeList[B] = CofreeList(f(ca.extract), ca.sub.map(i => map(i)(f)))
    def extract[A](ca: CofreeList[A]): A = ca.extract
    def duplicate[A](ca: CofreeList[A]): CofreeList[CofreeList[A]] = CofreeList(ca, ca.sub.map(duplicate))
  }
}
