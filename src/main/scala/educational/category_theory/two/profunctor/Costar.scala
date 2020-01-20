package educational.category_theory.two.profunctor

import educational.category_theory.Functor

/** Lift backward Functor into Profunctor */
case class Costar[F[_],D,C](runCostar: F[D] => C)

object CostarInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor.html#i:Costar
  def profunctor[F[_]](FF: Functor[F]): Profunctor[Costar[F,*,*]] = new Profunctor[Costar[F,*,*]] {
    def dimap[A,D,B,C](fbc: Costar[F,B,C])(ab: A => B, cd: C => D): Costar[F,A,D] =
      Costar{ fa =>
        val v: F[B] = FF.map(fa)(ab)
        val c: C = fbc.runCostar(v)
        cd(c)
      }
  }

  // TODO more instances
}
