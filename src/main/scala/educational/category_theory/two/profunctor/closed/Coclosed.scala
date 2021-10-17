package educational.category_theory.two.profunctor.closed

import educational.category_theory.two.profunctor.Profunctor
import educational.category_theory.two.profunctor.ProfunctorLaws
import educational.category_theory.two.profunctor.ProfunctorInstance.Function1Profunctor
import scala.Function.uncurried
import scala.Function.untupled

// class Profunctor p => Coclosed p where
//    open :: p a b -> p (b -> x) (a -> x)
//
trait Coclosed[=:>[_,_]] extends Profunctor[=:>] {
  def open[A,B,C](pab: A =:> B): (B => C) =:> (A => C)
}

object CoclosedInstances {
  val Function1Chocolate: Coclosed[Function1] = new Coclosed[Function1] with Function1Profunctor {
    override def open[A,B,C](f: A => B): (B => C) => A => C = _ compose f
  }
}

trait CoclosedLaws[=:>[_,_]] extends Coclosed[=:>] with ProfunctorLaws[=:>] {

  // lmap (. f) . open ≡ rmap (. f) . open
  def lmapClosedEqRmapClosed[A, B, C, D](p: A =:> B, f: (B => D) => (B => C), g: (A => D) => (A => C)): Boolean = {
    val l1: (B => C) =:> (A => C) = open(p) // (C => A) =:> (C => B)
    val l2: (B => D) =:> (A => C) = lmap(l1)(f)

    val r1: (B => D) =:> (A => D)  = open(p)
    val r2: (B => D) =:> (A => C) = rmap(r1)(g)
    l2 == r2 // TODO wh haskell uses 1 method? C == D and it is polymorphic? wired?
  }

  // TODO WIP
//  // open . open ≡ dimap uncurry curry . open
//  def closedClosedEqDimapClosed[A, B](p: A =:> B): Boolean = {
//    val l1: (B => B) =:> (A => B) = open(p)
//    val l2: (A => B => A) =:> (A => B => B) = open(l1)
//    val r1: (((A, B)) => A) =:> (((A, B)) => B) = open[A, B, (A, B)](p)
//    val r2: (A => B => A) =:> (A => B => B) = dimap(r1)(uncurry[A, B, A], curry[A, B, B])
//    l2 == r2
//  }

  // TODO WIP
  // dimap const ($()) . open ≡ id
//  def dimapCloseIsIdentity2[A,B](p: A =:> B): Boolean = {
//    val l1: (B => Unit) =:> (A => Unit) = open(p)
//    def foo[YY]: (Unit => YY) => YY = f => f(())
//    val l2: A =:> B = dimap(l1)(foo, const)
//    l2 == p
//  }

  def uncurry[X, Y, Z]: (X => Y => Z) => Tuple2[X, Y] => Z = uncurried(_).tupled
  def curry[X, Y, Z]: (Tuple2[X, Y] => Z) => X => Y => Z = untupled(_).curried
}
