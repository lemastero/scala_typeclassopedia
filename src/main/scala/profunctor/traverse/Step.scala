package profunctor.traverse

import profunctor.choice.Choice
import profunctor.strong.Strong

trait Step[P[_,_]] extends Choice[P] with Strong[P] {
  def step[A,B,C,D](pab: P[A,B]): P[Either[D, (A,C)], Either[D, (B,C)]] = right(first(pab))
}