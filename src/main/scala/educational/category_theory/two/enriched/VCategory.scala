package educational.category_theory.two.enriched

/*
class Category objc c where
  unit :: (objc x) => c x x
  comp :: (objc x) => c y z -> c x y
 */
trait VCategory[OBJC[_],C[_,_]] {
  def unit[X]: OBJC[X] => C[X,X]
  def comp[X,Y,Z]: OBJC[X] => C[Y,Z] => C[X,Y]
}
