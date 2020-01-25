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
   type maf = StrongMonoidalVAction[OBJM,M,O,I,OBJC,C,F]
   type mag = StrongMonoidalVAction[OBJM,M,O,I,OBJD,D,G]
   type oa = OBJC[A]
   type os = OBJC[S]
   type ob = OBJD[B]
   type ot = OBJD[T]
}

object Optic {
  def apply[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJM[_],M[_,_],O[_,_],I,F[_,_],G[_,_],A,B,S,T,X](
    cs: C[S,F[X,A]],
    d: D[G[X,B],T]):

   Optic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] = {
    type ox = OBJM[X]
    new Optic[OBJC,C,OBJD,D,OBJM,M,O,I,F,G,A,B,S,T] {}
  }
}