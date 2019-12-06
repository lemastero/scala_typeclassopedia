package comonad

import comonad.ComonadSimpleImpl.Comonad
import educational.Functor

import scala.language.higherKinds
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class CofreeCustomImplSpec
  extends AnyFunSpec
  with Matchers {

  case class Cofree[A, F[_]](extract: A, sub: F[Cofree[A, F]])(implicit functor: Functor[F]) {
    def map[B](f: A => B): Cofree[B, F] = Cofree(f(extract), functor.map(sub)(_.map(f)))
    def duplicate: Cofree[Cofree[A, F], F] = Cofree(this, functor.map(sub)(_.duplicate))
    def extend[B](f: Cofree[A, F] => B): Cofree[B, F] = duplicate.map(f) // coKleisi composition
  }

  case class CofreeList[A](extract: A, sub: List[CofreeList[A]])

  def cofreeListComonad: Comonad[CofreeList] = new Comonad[CofreeList] {
    def map[A, B](ca: CofreeList[A])(f: A => B): CofreeList[B] = CofreeList(f(ca.extract), ca.sub.map(i => map(i)(f)))
    def extract[A](ca: CofreeList[A]): A = ca.extract
    def duplicate[A](ca: CofreeList[A]): CofreeList[CofreeList[A]] = CofreeList(ca, ca.sub.map(duplicate))
  }

/*
Free monads vs Cofree comonads

sealed trait Free[F[_], A]
case class Return[F[_], A](a: A) extends Free[F, A]
case class Suspend[F[_], A](s: F[Free[F,A]])

Both:
- trees that holds value of type A
- branches (Option, List, other functor) are defined by Functor F

Free monad -> leaf tree,     either value or recursive step
Cofree comonad -> rose tree, both value and recursive step

 */
}
