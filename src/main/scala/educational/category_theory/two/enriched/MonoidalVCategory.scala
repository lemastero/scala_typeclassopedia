package educational.category_theory.two.enriched

/**
  Monoidal V-category (M,⊗,I,α,ρ,λ) consists of:
   * V-category M
   * V-functor ⊗
     M ⊗ M => M
   * object I : M
   * V-natural isomorphismss
     * α: (A ⊗ B) ⊗ C ~ A ⊗ (B ⊗ C)
     * λ: X ⊗ I) :=> X

  Haskell:
  ```
  class ( Category obja a
      , Bifunctor obja a obja a obja a o
      , obja i )
     => MonoidalCategory obja a o i where
  alpha :: (obja x, obja y, obja z)
                       => a (x `o` (y `o` z)) ((x `o` y) `o` z)
  alphainv  :: (obja x, obja y, obja z)
                        => a ((x `o` y) `o` z) (x `o` (y `o` z))
  rho        :: (obja x) => a (x `o` i) x
  rhoinv     :: (obja x) => a x (x `o` i)
  lambda     :: (obja x) => a (i `o` x) x
  lambdainv  :: (obja x) => a x (i `o` x)
  ```
  */
trait MonoidalVCategory[OBJA[_],:=>[_,_],⊗[_,_],I] {
  def c: VCategory[OBJA,:=>]
  def tensor: VBifunctor[OBJA,:=>,OBJA,:=>,OBJA,:=>,⊗]
  def id: OBJA[I]
  def α[X,Y,Z](implicit ox: OBJA[X], oy: OBJA[Y], oz: OBJA[Z]):     (X ⊗ (Y ⊗ Z)) :=> ((X ⊗ Y) ⊗ Z)
  def α_inv[X,Y,Z](implicit ox: OBJA[X], oy: OBJA[Y], oz: OBJA[Z]): ((X ⊗ Y) ⊗ Z) :=> (X ⊗ (Y ⊗ Z))
  def λ[X](implicit ox: OBJA[X]):      (X⊗I) :=> X
  def λ_inv[X](implicit ox: OBJA[X]):  X :=> (X ⊗ I)
  def ρ[X](implicit ox: OBJA[X]):      (I⊗X) :=> X
  def ρ_inv[X](implicit ox: OBJA[X]):  X :=> (I ⊗ X)
}
