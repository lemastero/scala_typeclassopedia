package educational

import const.Const

/** Map over both arguments at the same time. */
trait Bifunctor[P[_,_]] {

  def bimap[A, B, C, D](f: A => B, g: C => D): P[A, C] => P[B, D] = first(f) andThen second(g)

  def first[A, B, C](f: A => B): P[A,C] => P[B,C] = bimap(f, identity[C])

  def second[A, B, C](f: B => C): P[A, B] => P[A, C] = bimap(identity, f)
}

object BifunctorInstances {

  implicit val tupleBifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple2[A, C] => Tuple2[B, D] =
      t => (f(t._1), g(t._2))
  }

  implicit val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Either[A, C] => Either[B, D] = {
      case Left(a) => Left(f(a))
      case Right(c) => Right(g(c))
    }
  }

  implicit val constBifunctor: Bifunctor[Const] = new Bifunctor[Const] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Const[A, C] => Const[B, D] =
      c => Const[B, D](f(c.getConst))
  }

  implicit def tuple3Bifunctor[X]: Bifunctor[Tuple3[X, ?,?]] = new Bifunctor[Tuple3[X, ?,?]] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple3[X, A, C] => Tuple3[X, B, D] =
      c => (c._1, f(c._2), g(c._3))
  }

  implicit def tuple4Bifunctor[X1, X2]: Bifunctor[Tuple4[X1, X2, ?, ?]] = new Bifunctor[Tuple4[X1, X2, ?, ?]] {
    override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple4[X1, X2, A, C] => Tuple4[X1, X2, B, D] =
      c => (c._1, c._2, f(c._3), g(c._4))
  }
}
