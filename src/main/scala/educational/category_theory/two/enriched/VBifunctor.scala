package educational.category_theory.two.enriched

/*
class ( Category objc c, Category objd d, Category obje e
      , forall x y . (objc x , objd y) => obje (f x y) )
      => Bifunctor objc c objd d obje e f where
  bimap :: ( objc x1, objc x2, objd y1, objd y2 )
       => c x1 x2 -> d y1 y2 -> e (f x1 y1) (f x2 y2)
 */
trait VBifunctor[OBJC[_],C[_,_],OBJD[_],D[_,_],OBJE[_],E[_,_],F[_,_]] {
  def cc: VCategory[OBJC,C]
  def cd: VCategory[OBJD,D]
  def ce: VCategory[OBJE,E]
  def xy[X,Y](implicit ox: OBJC[X], oy: OBJD[Y]): OBJE[F[X,Y]]

  def bimap[X1,X2,Y1,Y2](implicit ox1: OBJC[X1],
       ox2: OBJC[X2],
       oy1: OBJD[Y1],
       oy2: OBJD[Y2]): C[X1,X2] => D[Y1,Y2] => E[F[X1,X2],F[X2,Y2]]
}
