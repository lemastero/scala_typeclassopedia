package educational.category_theory.two.profunctor.higher

trait ProfunctorRan[P[_,_],Q[_,_],A,B] {
  def runRan[X](pxa: P[X,A]): Q[X,B]
}
