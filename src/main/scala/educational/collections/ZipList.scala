package educational.collections

import educational.category_theory.Applicative

/**
  * ZipList is a List that when mapped over do not explode result like regular list
  * but pick the shortest list and combine them.
  *
  * It is example of Applicative that is not a Monad
  *
  * https://en.wikibooks.org/wiki/Haskell/Applicative_functors
  */
case class ZipList[A](getZipList: List[A]) { // TODO should this be FiniteStream ?
  def zipWith[B,C](f: (A,B) => C, other: ZipList[B]): ZipList[C] =
    ZipList(
      getZipList
        .zip(other.getZipList)
        .map(f.tupled)
    )
}

object ZipListInstances {
  val applicativeZipList: Applicative[ZipList] = new Applicative[ZipList] {
    def pure[A](value: A): ZipList[A] = ??? // TODO how to add invinite ZipList ?
    def ap[A, B](ff: ZipList[A => B])(fa: ZipList[A]): ZipList[B] = {
      def aply: (A=>B,A) => B = { case(f,a) => f(a) }
      ff.zipWith(aply, fa)
    }
  }
}