package educational.category_theory.two.enriched

/**
 Strong monoidal V-action F

 Haskell:

class ( MonoidalCategory objm m o i
      , Bifunctor objm m objc c objc c f
      , Category objc c )
      => MonoidalAction objm m o i objc c f where
  unitor           :: (objc x) => c (f i x) x
  unitorinv        :: (objc x) => c x (f i x)
  multiplicator    :: (objc x, objm p, objm q)
                             => c (f p (f q x)) (f (p `o` q) x)
  multiplicatorinv :: (objc x, objm p, objm q)
                             => c (f (p `o` q) x) (f p (f q x))
 */
trait StrongMonoidalVAction[OBJM[_],M[_,_],O[_,_],I,OBJC[_],C[_,_],F[_,_]] {
  def mc: MonoidalVCategory[OBJM,M,O,I]
  def bif: VBifunctor[OBJM,M,OBJC,C,OBJC,C,F]
  def c: VCategory[OBJC,C]

  def unitor[X](implicit ox: OBJC[X]): C[F[I,X],X]
  def unitorinv[X](implicit ox: OBJC[X]): C[X,F[I,X]]
  def multiplicator[X,P,Q](implicit ox: OBJC[X], op: OBJC[P], oq: OBJC[Q]):
    C[F[P,F[Q,X]],F[O[P,Q],X]]
  def multiplicatorinv[X,P,Q](implicit ox: OBJC[X], op: OBJC[P], oq: OBJC[Q]):
    C[F[O[P,Q],X],F[P,F[Q,X]]]
}
