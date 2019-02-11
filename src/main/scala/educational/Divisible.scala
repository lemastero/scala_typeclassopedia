package educational

import scala.language.higherKinds

trait Divisible[F[_]] extends Divide[F] {
  def conquer[A]: F[A]
}
