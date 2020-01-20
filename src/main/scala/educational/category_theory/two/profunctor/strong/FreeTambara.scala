package educational.category_theory.two.profunctor.strong

// translation from @emilypi Haskell snippet
// https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb

trait FreeTambara[P[_,_],A,B] {
  type U
  type V
  type C
  type D
  def _x1: A => (List[U], C)
  def _x2: P[U,V]
  def _x3: B => (List[V], D)
}

object FreeTambara {
  def apply[A, B, CC, DD, UU, VV, P[_, _]](
    x1: A => (List[UU], CC),
    x2: P[UU,VV],
    x3: B => (List[VV], DD)): FreeTambara[P, A, B] =
    new FreeTambara[P, A, B] {
      type U = UU
      type V = VV
      type C = CC
      type D = DD
      def _x1: A => (List[UU], CC) = x1
      def _x2: P[UU,VV] = x2
      def _x3: B => (List[VV], DD) = x3
    }
}
