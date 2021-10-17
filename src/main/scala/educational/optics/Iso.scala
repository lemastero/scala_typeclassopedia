package educational.optics

trait ~[A,B] {
  def to: B => A
  def from: A => B
}

object Iso {
  type Equivalence[A, B] = ~[A, B]
  type Bijection[A, B] = ~[A, B]
}

case class Iso[S,A](from: S => A, to: A => S) { self => // Adapter
  def modifyB(f: A => A): S => S =
    (from andThen f) andThen to
//  def andThen[B](other: Iso[A,B]): Iso[S,B] = Iso[S,B](
//    self.from andThen other.from,
//
//  )
}

