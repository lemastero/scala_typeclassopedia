package educational.category_theory.two.profunctor

import educational.category_theory.Functor

/** Lift Functor F: C -> D into Profunctor ProF: C -> D */
case class Star[F[_],D,C](runStar: D => F[C])

object StarInstances {
  def profunctor[F[_]](implicit FF: Functor[F]): Profunctor[Star[F,*,*]] = new Profunctor[Star[F,*,*]] {
    def dimap[X,W,Y,Z](bfc: Star[F,Y,Z])(ab: X => Y, cd: Z => W): Star[F,X,W] =
      Star[F,X,W]{ x => FF.map((bfc.runStar compose ab)(x))(cd) }
  }

  // TODO more instances
}
