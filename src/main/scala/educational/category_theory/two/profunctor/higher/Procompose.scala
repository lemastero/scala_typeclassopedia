package educational.category_theory.two.profunctor.higher

import educational.category_theory.two.Category
import educational.category_theory.two.profunctor.Profunctor

/** Composition of Profunctors */
trait Procompose[P[_,_],Q[_,_],D,C] {
  type X
  val p: P[X,C]
  val q: Q[D,X]
}

object Precompose {
  def apply[XX,P[_,_],Q[_,_],D,C](pxxc: P[XX,C], qdxx: Q[D,XX]): Procompose[P,Q,D,C] = new Procompose[P,Q,D,C] {
    type X = XX
    val p: P[X,C] = pxxc
    val q: Q[D,X] = qdxx
  }

  def precomposed[P[_,_],A,B](p: Procompose[P,P,A,B])(implicit CP: Category[P]): P[A,B] = CP.compose(p.p)(p.q)
}

object PrecomposeInstances {
  // def profunctorMonad[P[_,_],D,C]: ProfunctorMonad[Procompose[P, *[_], D, C]] = ??? TODO is it possible to express this in Scala ?
  def profunctor[P[_,_],Q[_,_]]: Profunctor[Procompose[P,Q,*,*]] = new Profunctor[Procompose[P,Q,*,*]] {
    def dimap[XX,W,Y,Z](pab: Procompose[P,Q,Y,Z])(ab: XX => Y, cd: Z => W): Procompose[P,Q,XX,W] = ??? // TODO implement rmap and lmap and then use thos defs here
  }
}
