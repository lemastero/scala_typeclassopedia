package educational.category_theory.two.profunctor

import educational.category_theory.Functor

/** Lift Functor F: C -> D into Profunctor ProF: C -> D */
case class Star[F[_],D,C](runStar: D => F[C])

object StarInstances {
  def profunctor[F[_]](implicit FF: Functor[F]): Profunctor[Star[F, ?,?]] = new Profunctor[Star[F, ?, ?]] {
    def dimap[X, Y, Z, W](ab: X => Y, cd: Z => W): Star[F, Y, Z] => Star[F, X, W] = bfc =>
      Star[F,X, W]{ x =>
        val f: Y => F[Z] = bfc.runStar
        val fz: F[Z] = f(ab(x))
        FF.map(fz)(cd)
      }
    //Star[F, X, W]{ x => FF.map((bfc.runStar compose ab)(x))(cd) }

    override def lmap[A,B,C](k: A => B): Star[F,B,C] => Star[F,A,C] = f =>
      Star[F,A,C](f.runStar compose k)
  }

  // TODO more instances
}
