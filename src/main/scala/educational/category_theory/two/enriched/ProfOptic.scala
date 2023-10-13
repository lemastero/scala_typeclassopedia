package educational.category_theory.two.enriched

/**

Haskell
type ProfOptic   objc c objd d objm m o i f g a b s t = forall p .
     ( Tambara objc c objd d objm m o i f g p
     , MonoidalAction objm m o i objc c f
     , MonoidalAction objm m o i objd d g
     , objc a , objd b , objc s , objd t
     ) => p a b -> p s t
 */
trait ProfOptic[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJM[_],M[_,_],O[_,_],I,F[_,_],G[_,_],A,B,S,T] {
  def MAF: StrongMonoidalVAction[OBJM,M,O,I,OBJC,C,F]
  def MAG: StrongMonoidalVAction[OBJM,M,O,I,OBJD,D,G]
  def OA: OBJC[A]
  def OB: OBJD[B]
  def OS: OBJC[S]
  def OT: OBJD[T]

  def apply[P[_,_]](implicit
    TP: Tambara[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,P] // TODO this is trait constraint, but I can't express it, withotut froall P[_,_]
  ): P[A,B] => P[S,T]
}

// TODO
//object ExistentialAndProfunctorOptics {
//  def ex2prof[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJM[_],M[_,_],O[_,_],I,F[_,_],G[_,_],A,B,S,T]:
//        Optic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] =>
//    ProfOptic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] = p => {
//    new ProfOptic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] {
//      def MAF: StrongMonoidalVAction[OBJM, M, O, I, OBJC, C, F] = p.maf
//      def MAG: StrongMonoidalVAction[OBJM, M, O, I, OBJD, D, G] = p.mag
//      def OA: OBJC[A] = p.oa
//      def OB: OBJD[B] = p.ob
//      def OS: OBJC[S] = p.os
//      def OT: OBJD[T] = p.ot
//      def apply[P[_, _]](implicit TP: Tambara[OBJC, C, OBJD, D, OBJM, M, O, I, F, G, P]): P[A, B] => P[S, T] = {
//        TP.pp.dimap compose TP.tambara
//      }
//    }
//  }
//}
