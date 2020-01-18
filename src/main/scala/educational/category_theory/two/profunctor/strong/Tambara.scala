package educational.category_theory.two.profunctor.strong

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.higher.DinaturalTransformation

trait Tambara[P[_,_],A,B]{
  def runTambara[C]: P[(A,C),(B,C)]
}

/**
  * tambara (untambara f) == f
  * untambara (tambara f) == f
  */
object Tambara {
  def tambara[P[_,_],Q[_,_]](pq: DinaturalTransformation[P,Q])(implicit SP: Strong[P]): DinaturalTransformation[P,Tambara[Q,*,*]] = ???
  def untambara[P[_,_], Q[_,_]](pq: DinaturalTransformation[P, Tambara[Q,*,*]])(implicit PQ: Profunctor[P]): DinaturalTransformation[P,Q] = ???
}

object TablaraInstances {

  // TODO https://hackage.haskell.org/package/profunctors-5.3/docs/Data-Profunctor-Strong.html#i:Tambara


  // Lambda[A => (A, A)]
  // Lambda[(A,B) => T[P[A,B]]]
  // Lambda[(A,B) => T[T[P[A,B]]]

  //    // instance ProfunctorFunctor Tambara where
  //    //  promap f (Tambara p) = Tambara (f p)
  //def strongProfunctorFunctor[PP[_,_],XX]  /* new ProfunctorFunctor[Tambara[PP, XX, ?]] { */

  //}

  trait ProfunctorTambara[P[_, _]] extends Profunctor[Tambara[P, ?, ?]] {
    def PP: Profunctor[P]

    def dimap[X,W,Y,Z](f: X => Y, g: Z => W): Tambara[P,Y,Z] => Tambara[P,X,W] = (tp: Tambara[P,Y,Z]) => new Tambara[P,X,W] {

      import StrongInstances.Function1Strong

      def runTambara[C]: P[(X,C), (W,C)] = {
        val fp: P[(Y,C), (Z,C)] => P[(X,C), (W,C)] = PP.dimap(
          Function1Strong.first[X,Y,C](f),
          Function1Strong.first[Z,W,C](g)
        )
        val p: P[(Y, C), (Z, C)] = tp.runTambara[C]
        fp(p)
      }
    }
  }

//  def ProfunctorFunctorTambara[X, Y] = {
//    new ProfunctorFunctor2[Tambara[?[_,_], X, Y]] {
//
//    def promap[P[_, _], Q[_, _]](dt: DinaturalTransformation[P, Q])(implicit PP: Profunctor[P]): DinaturalTransformation[({
//      type A
//      type B
//      type PP[A, B] = P[A, B]
//      type TP[A, B] = Tambara[PP, X, Y]
//    })#TP,
//      ({
//        type A
//        type B
//        type QQ[A, B] = Q[A, B]
//        type TQ[A, B] = Tambara[QQ, X, Y]
//      })#TQ] = {
//      ???
//    }
//
//    def promap1[P[_, _], Q[_, _]](f: DinaturalTransformation[P, Q])(implicit PP: Profunctor[P]): DinaturalTransformation[Tambara[P, ?, ?], Tambara[Q, ?, ?]] = {
//      new DinaturalTransformation[Lambda[(A, B) => Tambara[P, A, B]], Lambda[(A, B) => Tambara[Q, A, B]]] {
//        def dinat[X, Y](ppp: Tambara[P, X, Y]): Tambara[Q, X, Y] = new Tambara[Q, X, Y] {
//          def runTambara[C]: Q[(X, C), (Y, C)] = {
//            val p: P[(X, C), (Y, C)] = ppp.runTambara
//            f.dinat[(X, C), (Y, C)](ppp.runTambara)
//          }
//        }
//      }
//    }

    def promap2[P[_, _], Q[_, _]](f: DinaturalTransformation[P,Q]): DinaturalTransformation[Lambda[(A,B) => Tambara[P,A,B]], Lambda[(A,B) => Tambara[Q,A,B]]] = {
      new DinaturalTransformation[Lambda[(A,B) => Tambara[P,A,B]], Lambda[(A,B) => Tambara[Q,A,B]]] {
        def dinat[X, Y](ppp: Tambara[P,X,Y]): Tambara[Q,X,Y] = new Tambara[Q,X,Y] {
          def runTambara[C]: Q[(X,C), (Y,C)] = {
            val p: P[(X,C), (Y,C)] = ppp.runTambara
            f.dinat[(X,C), (Y,C)](p)
          }
        }
      }
//    }
//  }
}

  def strongTambara[P[_,_]](implicit PPro: Profunctor[P]): Strong[Tambara[P,*,*]] = new Strong[Tambara[P,*,*]] with ProfunctorTambara[P] {
    def PP: Profunctor[P] = PPro

    // instance Profunctor p => Strong (Tambara p) where
    //  first' = runTambara . produplicate
    def first[X, Y, Z](pab: Tambara[P, X, Y]): Tambara[P, (X, Z), (Y, Z)] = ??? // TODO need Profunctor Monad
  }
}
