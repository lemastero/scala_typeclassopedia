package educational.category_theory.two.enriched

/*
 class ( MonoidalAction objm m o i objc c f
      , MonoidalAction objm m o i objd d g
      , Profunctor objc c objd d p )
      => Tambara objc c objd d objm m o i f g p where
  tambara :: (objc x, objd y, objm w)
          => p x y -> p (f w x) (g w y)
 */
trait Tambara[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJM[_],M[_,_],O[_,_],I,F[_,_],G[_,_],P[_,_]] {
  def maf: StrongMonoidalVAction[OBJM,M,O,I,OBJC,C,F]
  def mag: StrongMonoidalVAction[OBJM,M,O,I,OBJD,D,G]
  def pp: VProfunctor[OBJC,C,OBJD,D,P]

  def tambara[X,Y,W](implicit ox: OBJC[X], o: OBJD[Y], ow: OBJM[W]): P[X,Y] => P[F[W,X],G[W,Y]]
}
