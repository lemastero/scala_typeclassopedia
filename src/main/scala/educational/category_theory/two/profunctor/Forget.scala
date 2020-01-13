package educational.category_theory.two.profunctor

case class Forget[R,A,B](runForget: A => R)

object ForgetInstances { // https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor.html#i:Forget
  def profunctor[R]: Profunctor[Forget[R, ?, ?]] = new Profunctor[Forget[R, ?, ?]] {
    def dimap[X, Y, Z, W](f: X => Y, cd: Z => W): Forget[R, Y, Z] => Forget[R, X, W] =
      k => Forget(k.runForget compose f)
  }

  // TODO more instances
}
