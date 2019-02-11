import scalaz._

final case class Predicate[A](run: A => Boolean)

implicit val cotraPredicate: Contravariant[Predicate] = new Contravariant[Predicate] {
  def contramap[A, B](pa: Predicate[A])(fba: B => A): Predicate[B] = {
    val fb: B => Boolean = fba andThen pa.run
    Predicate[B](fb)
  }
}

val p1 = Predicate()