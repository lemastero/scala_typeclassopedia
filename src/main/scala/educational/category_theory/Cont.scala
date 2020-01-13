package educational.category_theory

/** Continutation monad */
trait Cont[M[_],A] {
  def run[Z](f: A => M[Z]): M[Z]
}

object ContInstances {

  def contMonad[M[_]]: Monad[Cont[M, *]] = new Monad[Cont[M, *]] {
    def pure[A](a: A): Cont[M, A] = new Cont[M,A] {
      def run[Z](f: A => M[Z]): M[Z] = f(a)
    }

    override def map[A, B](x: Cont[M, A])(f: A => B): Cont[M, B] = new Cont[M, B] {
      def run[Z](ff: B => M[Z]): M[Z] = x.run(f andThen ff)
    }

    def flatMap[A, B](ma: Cont[M, A])(f: A => Cont[M, B]): Cont[M, B] = ??? // TODO implement me
  }
}
