package educational.category_theory

trait CoflatMap[F[_]] extends Functor[F] {
  def duplicate[A](fa: F[A]): F[F[A]]
  def extend[A, B](w: F[A])(f: F[A] => B): F[B] =
    map(duplicate(w))(f) // coKleisi composition
}

/* Comonads are dual to Monads
 ------------------------------------------
 |  Monad            |    Comonad         |
 |----------------------------------------|
 | put value inside  | get out value      |
 | remove one layer  | add one more layer |
 ------------------------------------------
 */
trait Comonad[F[_]] extends CoflatMap[F] {
  def extract[A](w: F[A]): A
}

trait ComonadLaws {
  /* Left identity law: fa.duplicate.extract == fa

      duplicate
    ------------>
F[A]                 F[F[A]]
    <------------
       extract                           */

  /* Right identity law: fa.extend(extract) == fa

                extend(extract)
        F[A]  ------------------> F[A]         */

  /* Associativity law: fa.duplicate.duplicate == fa.extend(duplicate)

           duplicate
    F[A] ------------> F[F[A]]
      \                    |
          \                |  duplicate
              \            |
extend(duplicate)  \       |
                     \|    \/
                     F[F[F[A]]]

   */
}

object Comonad {
  implicit def TupleComonad[T]: Comonad[(T,*)] = new Comonad[(T, *)] {
    override def extract[A](fa: (T, A)): A = fa._2

    override def duplicate[A](fa: (T, A)): (T, (T, A)) = (fa._1, fa)

    override def map[A, B](fa: (T, A))(f: A => B): (T, B) = (fa._1, f(fa._2))
  }
}
