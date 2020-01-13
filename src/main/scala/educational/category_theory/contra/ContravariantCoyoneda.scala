package educational.category_theory.contra

// data Coyoneda f a where
//  Coyoneda :: (a -> b) -> f b -> Coyoneda f a
trait ContravariantCoyoneda[F[_], A] {
  type B
  val fb: F[B]
  val m: A => B


  // lowerCoyoneda :: Contravariant f => Coyoneda f a -> f a
  // lowerCoyoneda (Coyoneda f m) = contramap f m
  def lowerCoyoneda(implicit CF: Contravariant[F]): F[A] = CF.contramap(fb)(m)
}

object ContravariantCoyoneda {
  // liftCoyoneda :: f a -> Coyoneda f a
  // liftCoyoneda = Coyoneda id
  def liftCoyoneda[F[_], AA](fa: F[AA]): ContravariantCoyoneda[F, AA] = new ContravariantCoyoneda[F, AA] {
    type B = AA
    val fb: F[B] = fa
    val m: AA => B = identity[AA]
  }

  // instance Contravariant (Coyoneda f) where
  //  contramap f (Coyoneda g m) = Coyoneda (g.f) m
  def cotraContraCoyo[F[_]]: Contravariant[ContravariantCoyoneda[F, ?]] = new Contravariant[ContravariantCoyoneda[F, ?]] {
    def contramap[AA, BB](fa: ContravariantCoyoneda[F, AA])(f: BB => AA): ContravariantCoyoneda[F, BB] = new ContravariantCoyoneda[F, BB] {
      type B = fa.B
      val fb: F[B] = fa.fb
      val m: BB => B = fa.m compose f
    }
  }
}
