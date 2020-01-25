package educational.category_theory.two.enriched

/*
V-enriched category

nLab: https://ncatlab.org/nlab/show/enriched+category

Haskell:
class Category objc c where
  unit :: (objc x) => c x x
  comp :: (objc x) => c y z -> c x y -> c x z
 */
trait VCategory[OBJC[_],C[_,_]] {
  def unit[X](implicit o: OBJC[X]): C[X,X]
  def comp[X,Y,Z](implicit o: OBJC[X]): C[Y,Z] => C[X,Y] => C[X,Z]
}
