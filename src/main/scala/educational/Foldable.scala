package educational

import scala.language.higherKinds

trait Foldable[F[_]] {
  def foldLeft[A, B](fa: F[A], b: B)(f: (B, A) => B): B
}

object FoldableInstances {
  val listFoldable: Foldable[List] = new Foldable[List] {
    def foldLeft[A, B](fa: List[A], b: B)(f: (B, A) => B): B = {
      fa.foldLeft(b)(f)
    }
  }
}
