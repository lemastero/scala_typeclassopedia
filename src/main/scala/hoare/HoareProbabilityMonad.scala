package hoare

import educational.category_theory.Monad

/**
  * Hoare Probablility Monad
  *
  * Laws:
  *
  * identity laws:
  *   choice(0, mx, my) == my
  *   choice(1, mx, my) == mx
  *
  * skewed commutativity law:
  *   choice(p, mx, my) == choice(1-p, my, mx)
  *
  * idempotence:
  *   choice(p, mx, mx) == mx
  *
  * quasi-associativity:
  *   if p == rs and (1-s)=(1-p)(1-q) then
  *   choice(p, mx, choice(q, my, mz) == choice(s, choice(r, mx, my), mz)
  *   mx - p
  *   my - (1-p)q
  *   mz - (1-p)(1-q)
  *
  *   mx - sr
  *   my - s(1-r)
  *   mz - 1-s
  *
  * @tparam M effect
  * @tparam Prob type of progability e.g. double from [0,1]
  */
trait HoareProbabilityMonad[M[_], Prob] extends Monad[M] {

  /** bahaves like mx with probability of p and like my with probability of my **/
  def choice[A](p: Prob, mx: M[A], my: M[A]): M[A]
}
