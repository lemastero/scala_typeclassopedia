package educational.data

import educational.category_theory.{Comonad, Functor}

case class Cofree[A, F[_]](extract: A, sub: F[Cofree[A, F]])(implicit
    functor: Functor[F]
) {
  def map[B](f: A => B): Cofree[B, F] =
    Cofree(f(extract), functor.map(sub)(_.map(f)))
  def duplicate: Cofree[Cofree[A, F], F] =
    Cofree(this, functor.map(sub)(_.duplicate))
  def extend[B](f: Cofree[A, F] => B): Cofree[B, F] =
    duplicate.map(f) // coKleisi composition
}

object Cofree {

  implicit def CofreeComonad[F[_]]: Comonad[Cofree[*, F]] = new Comonad[Cofree[*, F]] {
    override def extract[A](w: Cofree[A, F]): A = w.extract
    override def duplicate[A](wa: Cofree[A, F]): Cofree[Cofree[A, F], F] = wa.duplicate
    override def map[A, B](fa: Cofree[A, F])(f: A => B): Cofree[B, F] = fa.map(f)
  }
}

case class CofreeList[A](extract: A, sub: List[CofreeList[A]])

object CofreeList {

  def cofreeListComonad: Comonad[CofreeList] =
    new Comonad[CofreeList] {
      def map[A, B](ca: CofreeList[A])(f: A => B): CofreeList[B] =
        CofreeList(f(ca.extract), ca.sub.map(i => map(i)(f)))
      def extract[A](ca: CofreeList[A]): A = ca.extract
      override def duplicate[A](ca: CofreeList[A]): CofreeList[CofreeList[A]] =
        CofreeList(ca, ca.sub.map(duplicate))
    }
}
