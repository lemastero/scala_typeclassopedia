package educational.category_theory

/* Comonads are dual to Monads
 ------------------------------------------
 |  Monad            |    Comonad         |
 |----------------------------------------|
 | put valus inside  | get out value      |
 | remove one layer  | add one more layer |
 ------------------------------------------
 */
trait Comonad[W[_]] extends Functor[W] {
  def extract[A](w: W[A]): A
  def duplicate[A](wa: W[A]): W[W[A]]
  def extend[A, B](w: W[A])(f: W[A] => B): W[B] =
    map(duplicate(w))(f) // coKleisi composition
}

trait ComonadLaws {
  /* Left identity law: wa.duplicate.extract == wa

      duplicate
    ------------>
W[A]                 W[W[A]]
    <------------
       extract                           */

  /* Right identity law: wa.extend(extract) == wa

                extend(extract)
        W[A]  ------------------> W[A]         */

  /* Associativity law: wa.duplicate.duplicate == wa.extend(duplicate)

           duplicate
    W[A] ------------> W[W[A]]
      \                    |
          \                |  duplicate
              \            |
extend(duplicate)   \        |
                     \|    \/
                     W[W[W[A]]]

   */
}

object Comonad {
  implicit def TupleComonad[T]: Comonad[(T,*)] = new Comonad[(T, *)] {
    override def extract[A](wa: (T, A)): A = wa._2

    override def duplicate[A](wa: (T, A)): (T, (T, A)) = (wa._1, wa)

    override def map[A, B](wa: (T, A))(f: A => B): (T, B) = (wa._1, f(wa._2))
  }
}
