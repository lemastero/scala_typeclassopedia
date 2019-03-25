package functor

import cats.Functor
import simple.Id

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
val listFunctor1: Functor[List] = new Functor[List]() {
  def map[A, B](fa: List[A])(ab: A => B): List[B] = fa match {
    case Nil => Nil
    case head :: tail => ab(head) :: map(tail)(ab)
  }
}

  val listFunctor: Functor[List] = new Functor[List]() {
    def map[A, B](fa: List[A])(f: A => B): List[B] = fa map f
  }

  val optionFunctor2: Functor[Option] = new Functor[Option]() {
    def map[A, B](opt: Option[A])(ab: A => B): Option[B] = opt match {
      case None => None
      case Some(v) => Some( ab(v) )
    }
  }

  val idFunctor: Functor[Id] = new Functor[Id] {
    def map[A, B](fa: Id[A])(f: A => B): Id[B] =
      Id( f(fa.value) )
  }

  /* Haskell - Functor instance for Pair (Tuple) x with

  instance Functor (x, ) where
   fmap :: (a -> b) -> (x, a) -> (x, b)
   fmap f (x, a) = (x, f a)
   */
  type TupleRightInt[X] = Tuple2[Int, X]
  val rightTupleFunctor: Functor[TupleRightInt] = new Functor[TupleRightInt] {
    def map[A, B](fa: TupleRightInt[A])(f: A => B): TupleRightInt[B] = (fa._1, f(fa._2))
  }

  type TupleLeftInt[X] = Tuple2[X, Int]
  val leftTupleFunctor: Functor[TupleLeftInt] = new Functor[TupleLeftInt] {
    def map[A, B](fa: TupleLeftInt[A])(f: A => B): TupleLeftInt[B] = (f(fa._1), fa._2)
  }

  /* Haskell - Functor instance for Either

  instance Functor (Either e) where
   fmap :: (a -> b) -> Either e a -> Either e b
   fmap f (left e) = Left e
   fmap f (Right x) = Right (f x)
   */
  type EitherRightInt[X] = Either[Throwable, X] // map
  val eitherRightIntFunctor: Functor[EitherRightInt] = new Functor[EitherRightInt] {
    override def map[A, B](fa: EitherRightInt[A])(f: A => B): EitherRightInt[B] = fa match {
      case Left(v) => Left(v)
      case Right(v) => Right(f(v))
    }
  }

  type EitherLeftInt[X] = Either[X, Int] // leftMap
  val eitherLeftIntFunctor: Functor[EitherLeftInt] = new Functor[EitherLeftInt] {
    override def map[A, B](fa: EitherLeftInt[A])(f: A => B): EitherLeftInt[B] = fa match {
      case Left(v) => Left(f(v))
      case Right(v) => Right(v)
    }
  }

  // from typelevel Config
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


    case class Const[A,B](a: A)

    def constFunctor[X]: Functor[Const[X, ?]] = new Functor[Const[X,?]] {
      def map[A, B](fa: Const[X, A])(f: A => B): Const[X, B] = ???
    }

    type Thunk[A] = () => A

    val thunkFunctor: Functor[Thunk] = new Functor[Thunk] {
      def map[A, B](fa: Thunk[A])(f: A => B): Thunk[B] =
        ???
    }
}
