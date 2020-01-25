package educational.category_theory.two.enriched

/*
V-profunctor

class ( Category objc c, Category objd d )
       => Profunctor objc c objd d p where
   dimap :: (objc x1, objc x2, objd y1, objd y2)
        => c x2 x1 -> d y1 y2 -> p x1 y1 -> p x2 y2
*/

trait VProfunctor[OBJC[_],C[_,_],OBJD[_],D[_,_],P[_,_]] {
  def cc: VCategory[OBJC,C]
  def cd: VCategory[OBJD,D]

  def dimap[X1,X2,Y1,Y2](implicit ox1: OBJC[X1], ox2: OBJC[X2], oy1: OBJD[Y1], oy2: OBJD[Y2]):
    C[X2,X1] => D[Y1,Y2] => P[X1,X2] => P[X2,Y2]
}
