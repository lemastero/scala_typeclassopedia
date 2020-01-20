package educational.category_theory

trait Functor[F[_]] {
  def map[A,B](fa: F[A])(f: A => B): F[B]
  def lift[A,B](f: A => B): F[A] => F[B] = map(_)(f)
}

trait FunctorLaws[F[_]] extends Functor[F]{

  def covariantIdentity[A](fa: F[A]): Boolean = {
    //           identity
    // F[A] =================> F[A]
    val l: F[A] = map(fa)(identity)
    val r: F[A] = fa
    l == r
  }

  def covariantComposition[A, B, C](fa: F[A], f: A => B, g: B => C): Boolean = {
    //          f
    // F[A] ========> F[B]
    val l1: F[B] = map(fa)(f)
    //           g
    // F[B] =========> F[C]
    val l2: F[C] = map(l1)(g)

    //        f andThen g
    // F[A] ==============> F[C]
    val r: F[C] = map(fa)(f andThen g)

    l2 == r
  }
}

object FunctorInstances {
  val listFunctor1: Functor[List] = new Functor[List]() { // TODO add custom impl of List and move this guy there
    def map[A, B](fa: List[A])(ab: A => B): List[B] = fa match {
      case Nil => Nil
      case head :: tail => ab(head) :: map(tail)(ab)
    }
  }

  val listFunctor: Functor[List] = new Functor[List]() {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa map f
  }

  def tupleRFunctor[X]: Functor[(X, *)] = new Functor[(X, *)] {
    def map[A, B](fa: (X,A))(f: A => B): (X, B) = (fa._1, f(fa._2))
  }

  type TupleLeftInt[X] = (X, Int)
  val leftTupleFunctor: Functor[TupleLeftInt] = new Functor[TupleLeftInt] {
    def map[A, B](fa: TupleLeftInt[A])(f: A => B): TupleLeftInt[B] = (f(fa._1), fa._2)
  }

  type EitherRightInt[X] = Either[Throwable, X] // map
  val eitherRightIntFunctor: Functor[EitherRightInt] = new Functor[EitherRightInt] {
    def map[A, B](fa: EitherRightInt[A])(f: A => B): EitherRightInt[B] = fa match {
      case Left(v) => Left(v)
      case Right(v) => Right(f(v))
    }
  }

  type EitherLeftInt[X] = Either[X, Int] // leftMap
  val eitherLeftIntFunctor: Functor[EitherLeftInt] = new Functor[EitherLeftInt] {
    def map[A, B](fa: EitherLeftInt[A])(f: A => B): EitherLeftInt[B] = fa match {
      case Left(v) => Left(f(v))
      case Right(v) => Right(v)
    }
  }

  implicit val oneArgFunctionsFromInt: Functor[Int => *] = new Functor[Int => *] {
    def map[A, B](g: Int => A)(h: A => B): Int => B =
      g andThen h
  }

  implicit def oneArgFunctionsFromX[Input]: Functor[Input => *] = new Functor[Input => *] {
    def map[A, B](fun: Input => A)(g: A => B): Input => B =
      fun andThen g
  }

  // TODO write test showing it does not meet functor laws because it is not lazy
  /*
  implicit val futureFunctor: Functor[Future] = new Functor[Future] {
    import scala.concurrent.ExecutionContext.Implicits.global

    def map[A, B](future: Future[A])(g: A => B): Future[B] =
      future.map(g)
  }
  */
}
