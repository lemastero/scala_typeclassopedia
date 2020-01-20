package educational.category_theory.two.profunctor.higher

import educational.category_theory.two.profunctor.Profunctor

trait ProfunctorCoyoneda[P[_,_],A,B] {
  type X
  type Y
  def f1: A => X
  def f2: Y => B
  def pxy: P[X,Y]

  def dimap[C, W](l: C => A, r: B => W): ProfunctorCoyoneda[P, C, W] =
    ProfunctorCoyoneda[X, Y, P, C, W](f1 compose l, r compose f2, pxy)
}

object ProfunctorCoyoneda {

  def apply[XX,YY,P[_,_],A,B](ax: A => XX, yb: YY => B, p: P[XX,YY]): ProfunctorCoyoneda[P,A,B] = new ProfunctorCoyoneda[P,A,B] {
    type X = XX
    type Y = YY
    def f1: A => X = ax
    def f2: Y => B = yb
    def pxy: P[X,Y] = p
  }

  /**
    * returnCoyoneda . proextract ≡ id
    * proextract . returnCoyoneda ≡ id
    * produplicate ≡ returnCoyoneda
    */
  def returnCoyoneda[P[_,_],A,B](pab: P[A,B]): ProfunctorCoyoneda[P,A,B] =
    ProfunctorCoyoneda(identity[A],identity[B],pab)

  /**
    * joinCoyoneda . produplicate ≡ id
    * produplicate . joinCoyoneda ≡ id
    * joinCoyoneda ≡ proextract
    */
  def joinCoyoneda[P[_,_],A,B](p: ProfunctorCoyoneda[ProfunctorCoyoneda[P, *, *],A,B]): ProfunctorCoyoneda[P,A,B] = ???
}

object ProfunctorCoyonedaInstances {
  def profunctor[P[_,_]]: Profunctor[ProfunctorCoyoneda[P,*,*]] = new Profunctor[ProfunctorCoyoneda[P,*,*]] {
    def dimap[X,W,Y,Z](p: ProfunctorCoyoneda[P,Y,Z])(l: X => Y, r: Z => W): ProfunctorCoyoneda[P,X,W] =
      p.dimap(l,r)
  }
}
