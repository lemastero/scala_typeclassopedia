package educational.category_theory

import cats.data.NonEmptyList

trait MonoidK[F[_]] {
  def empty[A]: F[A]
  def combine[A](lhs: F[A], rhs: F[A]): F[A]
}

object MonoidK {
  val listMonoidK: MonoidK[List] = new MonoidK[List] {
    override def empty[A]: List[A] = Nil
    override def combine[A](lhs: List[A], rhs: List[A]): List[A] = lhs ++ rhs
  }

  val vectorMonoidK: MonoidK[Vector] = new MonoidK[Vector] {
    override def empty[A]: Vector[A] = Vector.empty
    override def combine[A](lhs: Vector[A], rhs: Vector[A]): Vector[A] = lhs ++ rhs
  }

  val optionMonoidK: MonoidK[Option] = new MonoidK[Option] {
    override def empty[A]: Option[A] = None

    override def combine[A](lhs: Option[A], rhs: Option[A]): Option[A] = (lhs, rhs) match {
      case (None, r) => r
      case (l, _) => l
    }
  }
}

trait Alternative[F[_]] extends MonoidK[F] with Applicative[F] {
  def some[A]: F[A] => F[NonEmptyList[A]] = v => map(v)(NonEmptyList.one)
  def many[A]: F[A] => F[List[A]] = v => map(v)(List(_))
}
//
//object Alternative {
//  val vectorMonoidK: Alternative[Vector] = new Alternative[Vector] {
//    override def empty[A]: Vector[A] = Vector.empty
//    override def combine[A](lhs: Vector[A], rhs: Vector[A]): Vector[A] = lhs ++ rhs
//    override def pure[A](a: A): Vector[A] = Vector(a)
//    override def ap[A, B](ff: Vector[A => B])(fa: Vector[A]): Vector[B] = ???
//  }
//
//  val optionMonoidK: Alternative[Option] = new Alternative[Option] {
//    override def empty[A]: Option[A] = None
//
//    override def combine[A](lhs: Option[A], rhs: Option[A]): Option[A] = (lhs, rhs) match {
//      case (None, r) => r
//      case (l, _) => l
//    }
//  }
//}
