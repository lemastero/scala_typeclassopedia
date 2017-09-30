package functor.contravariant

import cats.functor.Contravariant

/* Translated into Scala explanation about:
 - polarity, negative and positive position of type
 - covariance, contravariance
 - contravariant functor

 from talk:
George Wilson - The Extended Functor Family: https://www.youtube.com/watch?v=JUVMiRRq6wU


Type in type signature can be in positive position or negative position.

In defintion of constant is in positive position:

val x: Int = 42
val snacks: List[Banana] = ???

In function definition result type is in positive position (Int, Rome)
and input type is in negative position (List[A], Romulus, Remus):

def length(xs: List[A]): Int = ???
def buildRome(lhs: Romulus, rhs: Remus): Rome = ???

Type for Functor must be covariant (Functor == Covariant Functor).
In Option, List, Vector, type param is in positive position.

Haskell - Contravariant Functor type class
class Contravariant f where
  contramap :: (b -> a) -> f a -> f b

Laws:
contramap id = id
contramap f . contramap g = contramap (g . g)

(Covariant) Functor is full of A's
Contravarian (Functor) needs A's

Divisible is contravariant for Applicative:
 https://hackage.haskell.org/package/contravariant-1.4/docs/Data-Functor-Contravariant-Divisible.html
 https://stackoverflow.com/questions/32059754/are-there-useful-applications-for-the-divisible-type-class
Decidable is contravariant for Alternative:
 https://hackage.haskell.org/package/contravariant-1.4/docs/Data-Functor-Contravariant-Divisible.html#t:Decidable
 https://www.reddit.com/r/haskell/comments/38o0f7/a_mixture_of_applicative_and_divisible/
*/

object InstancesForContravariantFunctor {

  /* Haskell - Predicate and Contravariant instance for it

   newtype Predicate a =
     Predicate { runPredicate :: a -> Bool }

   In here a is on negative position, we can define Contravariant instance.

   instance Contravariant Predicate where
     contramap :: (b -> a) -> Predicate a -> Predicate b
     contramap f (Predicate p) = Predicate (p . f)
 */
  final case class Predicate[A](fun: A => Boolean)

  val predicateContravariantFunctor: Contravariant[Predicate] = new Contravariant[Predicate] {
    override def contramap[A, B](pred: Predicate[A])(fba: B => A): Predicate[B] = Predicate[B](fba andThen pred.fun)
  }

  /* Haskell - Ordering and Comparison and Contravariant instance for Comparison

  data Ordering = LT | EQ | GT

  newtype Comparison a =
    Comparison { runComparison :: a -> a -> Ordering }

  instance Contravariant Comparison where
    contramap :: (b -> a) -> Comparison a -> Comparison b
    contramap f (Comparison c) = Comparison (\a b -> c (f a) (f b))
  */

  sealed trait Ordering
  case object LT extends Ordering
  case object EQ extends Ordering
  case object GT extends Ordering

  case class Comparison[A](
    runComparison: (A, A) => Ordering
  )

  val comparisonContravariantFunctor: Contravariant[Comparison] = new Contravariant[Comparison] {
    override def contramap[A, B](fa: Comparison[A])(f: B => A): Comparison[B] = {
      Comparison[B](
        (lhs, rhs) => fa.runComparison(f(lhs), f(rhs))
      )
    }
  }
}
