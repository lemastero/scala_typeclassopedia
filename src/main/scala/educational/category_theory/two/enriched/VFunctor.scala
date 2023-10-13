package educational.category_theory.two.enriched

/*
V-enriched functor

nLab: https://ncatlab.org/nlab/show/enriched+functor

Haskell
class ( Category objc c, Category objd d
      , forall x . objc x => objd (f x)
      ) => VFunctor objc c objd d f where
  map :: (objc x, objc y) => c x y -> d (f x) (f y)
*/
trait VFunctor[OBJC[_],C[_,_],OBJD[_],D[_,_],F[_]] {
  def c1: VCategory[OBJC,C]
  def c2: VCategory[OBJD,D]
  def f[X]: OBJC[X] => OBJD[F[X]]

  def map[X,Y](implicit x: OBJC[X], y: OBJD[Y]): C[X,Y] => D[F[X],F[Y]]
}
