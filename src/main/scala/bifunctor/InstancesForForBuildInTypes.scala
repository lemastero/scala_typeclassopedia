package bifunctor

import cats.Bifunctor

/* Translated into Scala examples illustrating BiFunctor from fun, clear explanation by:
  George Wilson - The Extended Functor Family: https://www.youtube.com/watch?v=JUVMiRRq6wU

  - BiFunctor allow you to perform two different operations

  Haskell Bifunctor - type class & laws

  class Bifunctor p where
    bimap :: (a -> b) -> (x -> y) -> p a x -> p b y

    first ::  (a -> b) -> p a x -> p b x
    second :: (x -> y) -> p a x -> p a y

    laws:
    bimap id id = id
    - bimap using two identify function is identify
    bimap f h . bimap g i = bimap (f . g)(h . i)
    - bimap using two pair of functions is the same as bimap using composition of compositions of those functions
 */
object InstancesForForBuildInTypes {

  val tuple2Bifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {
    override def bimap[A, B, C, D](fab: (A, B))(f: A => C, g: B => D): (C, D) = (f(fab._1), g(fab._2))
  }

  val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
    override def bimap[A, B, C, D](fab: Either[A, B])(f: A => C, g: B => D): Either[C, D] = fab match {
      case Left(v) => Left(f(v))
      case Right(v) => Right(g(v))
    }
  }

  import cats.data.Validated
  val validatedBifunctor: Bifunctor[Validated] = new Bifunctor[Validated] {
    override def bimap[A, B, C, D](fab: Validated[A, B])(f: A => C, g: B => D): Validated[C, D] = fab.map(g).leftMap(f)
  }

  // TODO implement property test for laws, if f collapse elements it would change structure of map, combine elements in that case?
  /*
  val mapBifunctor: Bifunctor[Map] = new Bifunctor[Map] {
    override def bimap[A, B, C, D](fab: Map[A, B])(f: A => C, g: B => D): Map[C, D] = {
      fab.foldLeft(Map[C, D]()){ (soFar, entry) => soFar + ( f(entry._1) -> g(entry._2)) }
    }
  }
   */
}
