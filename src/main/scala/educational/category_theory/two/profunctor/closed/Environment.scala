package educational.category_theory.two.profunctor.closed

trait Environment[P[_,_],A,B] {
  type X
  type Y
  type Z
  def f1: (Z => Y) => B
  def pxy: P[X,Y]
  def f2: A => Z => X
}

object EnvironmentInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Closed.html#i:Environment
  // TODO
}
