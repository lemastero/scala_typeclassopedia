package filterable

import educational.category_theory.Functor

// https://github.com/masaeedu/filterable/blob/master/src/Data/Filterable.hs
class Filterable {

  type \/[A,B] = Either[A,B]

  // Monoidal structure
  def assocE[A,B,C]: Either[A, Either[B,C]] => Either[Either[A,B],C] = {
    case Left(a) => ???
  }

  trait Filterable[F[_]] extends Functor[F] {
    def partition[A,B]: F[Either[A,B]] => (F[A], F[B])
  }

  // TODO laws

  object Filterable {
    def trivial[F[_]](implicit FF: Filterable[F]): F[Void] => Unit = fv => ()

    val filterableOption: Filterable[Option] = new Filterable[Option] {
      def map[A, B](x: Option[A])(f: A => B): Option[B] = x.map(f)
      def partition[A, B]: Option[Either[A, B]] => (Option[A], Option[B]) = {
        case None           => (None, None)
        case Some(Left(a))  => (Some(a), None)
        case Some(Right(b)) => (None, Some(b))
      }
    }

    val filterableList: Filterable[List] = new Filterable[List] {
      def map[A, B](x: List[A])(f: A => B): List[B] = x.map(f)
      def partition[A, B]: List[Either[A, B]] => (List[A], List[B]) = a => (
        a.collect{case Left(a) => a},
        a.collect{case Right(b) => b})
    }

    // TODO Logic
  }

}
