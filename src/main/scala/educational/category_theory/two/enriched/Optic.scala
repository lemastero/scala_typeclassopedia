package educational.category_theory.two.enriched

/**
  TODO in here type constraints are implemented using type aliases? rest impls uses functions as members of type - rethink
  Haskell:
  data Optic objc c objd d objm m o i f g a b s t where
    Optic :: ( MonoidalAction objm m o i objc c f
           , MonoidalAction objm m o i objd d g
           , objc a, objc s , objd b, objd t , objm x )
        => c s (f x a) -> d (g x b) t
        -> Optic objc c objd d objm m o i f g a b s t

  */
trait Optic[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJM[_],M[_,_],O[_,_],I,F[_,_],G[_,_],A,B,S,T] {
   def maf: StrongMonoidalVAction[OBJM,M,O,I,OBJC,C,F]
   def mag: StrongMonoidalVAction[OBJM,M,O,I,OBJD,D,G]
   def oa: OBJC[A]
   def os: OBJC[S]
   def ob: OBJD[B]
   def ot: OBJD[T]
   type X
   def l: C[S,F[X,A]]
   def r: D[G[X,B],T]
}

object Optic {

  def apply[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJM[_],M[_,_],O[_,_],I,F[_,_],G[_,_],A,B,S,T,XX](
    amaf: StrongMonoidalVAction[OBJM,M,O,I,OBJC,C,F],
    amag: StrongMonoidalVAction[OBJM,M,O,I,OBJD,D,G],
    aoa: OBJC[A],
    aos: OBJC[S],
    aob: OBJD[B],
    aot: OBJD[T],
    al: C[S,F[XX,A]],
    ar: D[G[XX,B],T],
    ox: OBJM[XX]
  ): Optic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] = {
    new Optic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] {
      def maf: StrongMonoidalVAction[OBJM,M,O,I,OBJC,C,F] = amaf
      def mag: StrongMonoidalVAction[OBJM,M,O,I,OBJD,D,G] = amag
      def oa: OBJC[A] = aoa
      def os: OBJC[S] = aos
      def ob: OBJD[B] = aob
      def ot: OBJD[T] = aot
      type X = XX
      def l: C[S, F[XX, A]] = al
      def r: D[G[XX, B], T] = ar
    }
  }
}