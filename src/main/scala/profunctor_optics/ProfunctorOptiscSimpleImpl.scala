package profunctor_optics

import scala.language.higherKinds

trait Profunctor[P[_,_]] {
  def dimap[A, B, C, D](ab: A => B, cd: C => D): P[B, C] => P[A, D]
}

trait Strong[P[_, _]] extends Profunctor[P] {
  def first[A, B, C](pab: P[A, B]): P[(A,C), (B,C)]
}

trait Choice[P[_, _]] extends Profunctor[P] {
  def left[A, B, C](pab: P[A,B]): P[Either[A, C], Either[B, C]]
}

trait Lens[S, T, A, B] {
  def run[P[_,_]](pab: P[A, B])(implicit SP: Strong[P]) : P[S, T]
}

trait Prism[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(PC: Choice[P]): P[S, T]
}
