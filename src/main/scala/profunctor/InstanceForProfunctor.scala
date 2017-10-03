package profunctor

import cats.data.Kleisli
import cats.functor.Profunctor

object InstanceForProfunctor {

  val functionProfunctor = new Profunctor[Function1] {
    override def dimap[A, B, C, D](fab: A => B)(f: C => A)(g: B => D): C => D = f andThen fab andThen g
  }

  type ListKleisli[A, B] = Kleisli[List, A, B] // TODO how to for generic Kleisli in Scala (in Haskell they use liftM)
  def kleisliProfunctor[F] = new Profunctor[ListKleisli] {
    override def dimap[A, B, C, D](fab: ListKleisli[A, B])(f: C => A)(g: B => D): ListKleisli[C, D] = ??? //fab.dimap(f)(g)
  }
}
