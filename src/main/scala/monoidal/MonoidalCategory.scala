package monoidal

import educational.Bifunctor

/**
  * Monoidal Categories based on Category of Scala types and functions
  *
  * Edward Kmett Discrimination is Wrong: Improving Productivity
  * http://yowconference.com.au/slides/yowlambdajam2015/Kmett-DiscriminationIsWrong.pdf
  */
object MonoidalCategory {
  import scala.language.higherKinds

  trait MonoidalCategory[M[_, _], I] {
    val mcBif: Bifunctor[M]
    val mcId: I

    def rho[A]    (fa: M[A,I]): A
    def rho_inv[A](a:  A):      M[A, I]

    def lambda[A]      (fa: M[I,A]): A
    def lambda_inv[A,B](a:  A):      M[I, A]

    def alpha[A,B,C](    fa: M[M[A,B], C]): M[A, M[B,C]]
    def alpha_inv[A,B,C](fa: M[A, M[B,C]]): M[M[A,B], C]
  }

  trait MonoidalCategoryLaws[M[_, _], I] extends MonoidalCategory[M,I] {

    def triangleEquations[A, B](fa: M[M[A, I], B]): Boolean = {
      //               ro[A] * id[B]
      // (A * I) * C ----------------> A * B
      val v1: M[A, B] = mcBif.bimap(rho[A], identity[B])(fa)

      //              alpha[A,I,B]                  id[A] * lambda[B]
      // (A * I) * C -------------->  A * (I * C) --------------------> A * B
      val w1: M[A, M[I, B]] = alpha[A,I,B](fa)
      val w2: M[A, B] = mcBif.bimap[A,A, M[I,B],B](identity[A],lambda[B])(w1)

      v1 == w2
    }

    def pentagonEquations[A,B,C,D](fa: M[M[M[A,B],C],D]): Boolean = {
      //                    alpha[A,B,C] * 1D                      alpha[A,B*C,D]
      // ((A * B) * C) * D -------------------> (A * (B * C)) * D ------------------> A * ((B * C) * D)
      //                    1A * alpha[B,C,D]
      // A * ((B * C) * D) ------------------> A * (B * (C * D))
      val v1: M[M[A, M[B, C]], D] = mcBif.bimap(alpha[A,B,C],identity[D])(fa)
      val v2: M[A, M[M[B,C], D]] = alpha[A,M[B,C],D](v1)
      val v3: M[A, M[B, M[C,D]]] = mcBif.bimap(identity[A],alpha[B,C,D])(v2)

      //                     alpha[A*B,C,D]                       alpha[A,B,C*D]
      // ((A * B) * C) * D -----------------> (A * B) * (C * D) -----------------> A * (B * (C * D))
      val w1: M[M[A,B], M[C,D]] = alpha[M[A,B],C,D](fa)
      val w2: M[A,M[B,M[C,D]]] = alpha[A,B,M[C,D]](w1)

      v3 == w2
    }
  }

  val tupleBifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple2[A, C] => Tuple2[B, D] = { case (a,c) => (f(a), g(c)) }
  }

  val productMonoidalCategory: MonoidalCategory[Tuple2, Unit] = new MonoidalCategory[Tuple2, Unit] {
    val mcBif: Bifunctor[Tuple2] = tupleBifunctor
    val mcId: Unit = ()
    def rho[A](fa: (A, Unit)): A = fa._1
    def rho_inv[A](a: A): (A, Unit) = (a, mcId)
    def lambda[A](fa: (Unit, A)): A = fa._2
    def lambda_inv[A, B](a: A): (Unit, A) = (mcId, a)
    def alpha[A, B, C](fa: ((A, B), C)): (A, (B, C)) = fa match {case ((a,b),c) => (a, (b, c)) }
    def alpha_inv[A, B, C](fa: (A, (B, C))): ((A, B), C) = fa match {case (a,(b,c)) => ((a, b), c) }
  }

  type Void <: Nothing

  val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Either[A, C] => Either[B, D] = {
      case Left(a) => Left(f(a))
      case Right(c) => Right(g(c))
    }
  }

  val coproductMonoidalCategory: MonoidalCategory[Either, Void] = new MonoidalCategory[Either, Void] {
    val mcBif: Bifunctor[Either] = eitherBifunctor
    val mcId: Void = throw new RuntimeException("This exception was not thrown!")
    def rho[A](fa: Either[A, Void]): A = fa match { case Left(a) => a }
    def rho_inv[A](a: A): Either[A, Void] = Left(a)
    def lambda[A](fa: Either[Void, A]): A = fa match { case Right(a) => a }
    def lambda_inv[A, B](a: A): Either[Void, A] = Right(a)
    def alpha[A, B, C](fa: Either[Either[A, B], C]): Either[A, Either[B, C]] =
      fa match {
        case Left(Left(a)) => Left(a)
        case Left(Right(b)) => Right(Left(b))
        case Right(c) => Right(Right(c))
      }
    def alpha_inv[A, B, C](fa: Either[A, Either[B, C]]): Either[Either[A, B], C] = fa match {
      case Left(a) => Left(Left(a))
      case Right(Left(b)) => Left(Right(b))
      case Right(Right(c)) => Right(c)
    }
  }
}
