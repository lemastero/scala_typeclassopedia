package educational.category_theory.two.profunctor.strong

// translation from @emilypi Haskell snippet
// https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb

trait CofreeTambara[P,A,B] {
  type C
  def x1: (List[A],C)
  def x2: (List[B],C)
}

object CofreeTambara {
  def apply[P,A,AA,B,BB, C](t1: (List[AA], C), t2: (List[BB], C)): CofreeTambara[P,A,B] = ???
}
