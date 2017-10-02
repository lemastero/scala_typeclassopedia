package functor

import cats.Functor

import scala.concurrent.Future

/* Translated into Scala examples ilustrating Functor from fun, clear explanation by:
  George Wilson - The Extended Functor Family: https://www.youtube.com/watch?v=JUVMiRRq6wU

  Haskell - Functor type class & laws

  class Functor f where
    fmap :: (a -> b) -> f a -> f b

  "functor is cool because has laws" :)
  fmap id = id
  - fmap identify function is identify function (fmap identity does nothing)

  fmap f . fmap g = f map (f . g)
  - fmap f and then fmap g is the same as fmap composition of f and g
 */
object InstancesForBuiltInTypes {

  /* Haskell - Functor type class

   instance Functor [] where
   fmap :: (a -> b) -> [a] -> [b]
   fmap f [] = []
   fmap f (x:xs) = f x : fmap f xs
   */
  val listFunctor: Functor[List] = new Functor[List]() {
    override def map[A, B](fa: List[A])(f: (A) => B): List[B] = fa map f // already defined with CanBuildFrom magic
  }

  val optionFunctor: Functor[Option] = new Functor[Option]() {
    override def map[A, B](fa: Option[A])(f: (A) => B): Option[B] = fa map f
  }

  /* Haskell - Functor instance for Pair (Tuple) x with

  instance Functor (x, ) where
   fmap :: (a -> b) -> (x, a) -> (x, b)
   fmap f (x, a) = (x, f a)
   */
  type TupleRightInt[X] = Tuple2[Int, X]
  val rightTupleFunctor: Functor[TupleRightInt] = new Functor[TupleRightInt] {
    override def map[A, B](fa: TupleRightInt[A])(f: A => B): TupleRightInt[B] = (fa._1, f(fa._2))
  }

  type TupleLeftInt[X] = Tuple2[X, Int]
  val leftTupleFunctor: Functor[TupleLeftInt] = new Functor[TupleLeftInt] {
    override def map[A, B](fa: TupleLeftInt[A])(f: A => B): TupleLeftInt[B] = (f(fa._1), fa._2)
  }

  /* Haskell - Functor instance for Either

  instance Functor (Either e) where
   fmap :: (a -> b) -> Either e a -> Either e b
   fmap f (left e) = Left e
   fmap f (Right x) = Right (f x)
   */
  type EitherRightInt[X] = Either[Int, X]
  val eitherRightIntFunctor: Functor[EitherRightInt] = new Functor[EitherRightInt] {
    override def map[A, B](fa: EitherRightInt[A])(f: A => B): EitherRightInt[B] = fa match {
      case Left(v) => Left(v)
      case Right(v) => Right(f(v))
    }
  }

  type EitherLeftInt[X] = Either[X, Int]
  val eitherLeftIntFunctor: Functor[EitherLeftInt] = new Functor[EitherLeftInt] {
    override def map[A, B](fa: EitherLeftInt[A])(f: A => B): EitherLeftInt[B] = fa match {
      case Left(v) => Left(f(v))
      case Right(v) => Right(v)
    }
  }

  implicit val oneArgFunctionsFromInt: Functor[Int => ?] =
    new Functor[Int => ?] {
      override def map[A, B](g: Int => A)(h: A => B): Int => B =
        g andThen h
    }

  implicit def oneArgFunctionsFromX[Input]: Functor[Input => ?] =
    new Functor[Input => ?] {
      override def map[A, B](fun: Input => A)(g: A => B): Input => B =
        fun andThen g
    }


  implicit val futureFunctor: Functor[Future] =
    new Functor[Future] {
      import scala.concurrent.ExecutionContext.Implicits.global

      def map[A, B](future: Future[A])(g: A => B): Future[B] =
        future.map(g)
    }
}
