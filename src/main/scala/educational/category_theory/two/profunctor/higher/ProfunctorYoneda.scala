package educational.category_theory.two.profunctor.higher

import educational.category_theory.Functor
import educational.category_theory.two.profunctor.Profunctor

trait ProfunctorYoneda[P[_,_],A,B] { self =>
  def runYoneda[X,Y](f: X => A, g: B => Y): P[X,Y]

  def dimap[AA,BB](l: AA => A, r: B => BB): ProfunctorYoneda[P, AA, BB] = new ProfunctorYoneda[P,AA,BB] {
    def runYoneda[X, Y](l2: X => AA, r2: BB => Y): P[X, Y] = {
      val f1: X => A = l compose l2
      val f2: B => Y = r2 compose r
      self.runYoneda(f1, f2)
    }
  }

  /**
    * projoin . extractYoneda ≡ id
    * extractYoneda . projoin ≡ id
    * projoin ≡ extractYoneda
    */
  def extractYoneda: P[A,B] = runYoneda(identity[A], identity[B])

  /**
    * projoin . duplicateYoneda ≡ id
    * duplicateYoneda . projoin ≡ id
    * duplicateYoneda = proreturn
    */
  def duplicateYoneda: ProfunctorYoneda[ProfunctorYoneda[P,*,*],A,B] = new ProfunctorYoneda[ProfunctorYoneda[P,*,*],A,B] {
    def runYoneda[X, Y](l: X => A, r: B => Y): ProfunctorYoneda[P, X, Y] = self.dimap(l,r)
  }
}

object ProfunctorYonedaInstances {
  def profunctor[P[_,_]]: Profunctor[ProfunctorYoneda[P,*,*]] = new Profunctor[ProfunctorYoneda[P,*,*]] {
    def dimap[A,D,B,C](pab: ProfunctorYoneda[P, B, C])(l: A => B, r: C => D): ProfunctorYoneda[P, A, D] = pab.dimap(l,r)
  }

  def functor[P[_,_],A]: Functor[ProfunctorYoneda[P,A,*]] = new Functor[ProfunctorYoneda[P,A,*]] {
    def map[C,D](x: ProfunctorYoneda[P, A, C])(cd: C => D): ProfunctorYoneda[P, A, D] = new ProfunctorYoneda[P, A, D] {
      def runYoneda[X, Y](xa: X => A, dy: D => Y): P[X, Y] = x.runYoneda(xa, cd andThen dy)
    }
  }

  // TODO more instances
}
