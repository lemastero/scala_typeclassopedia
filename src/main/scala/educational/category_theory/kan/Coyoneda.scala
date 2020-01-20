package educational.category_theory.kan

import educational.category_theory.Functor
import educational.category_theory.higher.~>
import educational.category_theory.kan.LeftKanExtension.Lan
import educational.types.Id.Id

/*
Functor with some a's in it is isomorphic to some other type
that has function b -> a and some functor with b's inside

 f a ~ forall b. ((b -> a), f b)
 */
trait Coyoneda[F[_], A] {
  type Z
  def fb: F[Z]
  def f: Z => A

  /* If we have proof that type constructor F is a Functor then we can create F[A].
  So we lower Coyoneda to Functor F. */
  def lowerCoyoneda(implicit fun: Functor[F]): F[A] = fun.map(fb)(f)

  /* Coyoneda is the Left Kan extension of F along the Identity functor */
  // coyonedaToLan :: Coyoneda f a -> Lan Identity f a
  def coyonedaToLan(): Lan[Id,F,A] = Lan[Id,F,A,Z](fb, a => f(a))
}

object Coyoneda {

  def apply[F[_],AA,BB](ff: BB => AA, ffb: F[BB]): Coyoneda[F,AA] = new Coyoneda[F,AA] {
    type Z = BB
    def fb: F[Z] = ffb
    def f: Z => AA = ff
  }

  /* lift any type constructor F to Coyoneda F A */
  def liftCoyoneda[F[_], A](fa: F[A]): Coyoneda[F, A] = Coyoneda[F, A, A](identity[A], fa)

  /*Instance of Functor for Coyoneda F */
  implicit def coyoFunctor[F[_]]: Functor[Coyoneda[F, ?]] = new Functor[Coyoneda[F, ?]] {
    def map[A, AA](fa: Coyoneda[F, A])(ff: A => AA): Coyoneda[F, AA] = new Coyoneda[F, AA] {
      type Z = fa.Z
      def f: Z => AA = fa.f andThen ff
      def fb: F[Z] = fa.fb
    }
  }

  /* lift natural transformation to natural transformation on Coyoneda */
  def hoistCoyoneda[F[_], G[_], A, C](fab : F ~> G)(coyo: Coyoneda[F, A]): Coyoneda[G, A] =
    new Coyoneda[G, A] {
      type Z = coyo.Z
      def f: Z => A = coyo.f
      def fb: G[Z] = fab(coyo.fb)
    }
}
