package educational.category_theory.adjunct_rep

// http://blog.higher-order.com/blog/2015/10/04/scala-comonad-tutorial-part-2/

trait Adjunction[F[_], G[_]] {
  def left[A, B](f: F[A] => B): A => G[B]
  def right[A, B](f: A => G[B]): F[A] => B
}

class Examples {

  def tupleFunctionFromAdjunction[R]: Adjunction[Tuple2[*, R], Function1[R, *]] = new Adjunction[(*, R), R => *] {
    def left[A, B](f: ((A, R)) => B): A => R => B = Function.untupled(f).curried
    def right[A, B](f: A => R => B): ((A, R)) => B = Function.uncurried(f).tupled
  }
}
