package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.ProfunctorLaws
import educational.category_theory.two.profunctor.ProfunctorInstance.Function1Profunctor

// class Profunctor p => Chocolate p where
//    mirala :: p a b -> p (b -> x) (a -> x)
//
trait Chocolate[=:>[_,_]] extends Profunctor[=:>] {
  def mirala[A,B,C](pab: A =:> B): (B => C) =:> (A => C)
}

object MiralaInstances {
  val Function1Chocolate: Chocolate[Function1] = new Chocolate[Function1] with Function1Profunctor {
    override def mirala[A,B,C](f: A => B): (B => C) => A => C = _ compose f
  }
}

trait ChocolateLaws[=:>[_,_]] extends Chocolate[=:>] with ProfunctorLaws[=:>] {
  // lmap (. f) . mirala ≡ rmap (. f) . mirala
  // mirala . mirala ≡ dimap uncurry curry . mirala
  // dimap const ($()) . mirala ≡ id
}
