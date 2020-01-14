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
  def extend[A, B](w: W[A])(f: W[A] => B): W[B] = map(duplicate(w))(f) // coKleisi composition
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
