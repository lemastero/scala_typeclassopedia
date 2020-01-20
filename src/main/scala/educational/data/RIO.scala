package educational.data

import educational.abstract_algebra.Monoid
import educational.category_theory.contra.{Contravariant, Divide}
import educational.category_theory.{Functor, Monad}
import educational.category_theory.two.profunctor.Profunctor

/**
  * Profunctor IO (Reader + IO) models input and output
  */
case class RIO[R, A](run: R => A)

object RIOInstances {
  def rioFunctor[R,E]: Functor[RIO[R,*]] = new Functor[RIO[R,*]] {
    def map[A, B](fa: RIO[R,A])(f: A => B): RIO[R,B] = RIO(fa.run andThen f)
  }

  trait RIOMonad[R,E] extends Monad[RIO[R,*]] {
    def pure[A](a: A): RIO[R,A] = RIO(_ => a)
    def flatMap[A,B](fa: RIO[R,A])(f: A => RIO[R,B]): RIO[R,B] =
      RIO(r => {
        val rrb: RIO[R, RIO[R,B]] = map(fa)(f)
        rrb.run(r).run(r)
      })
  }

  def rioMonad[R,E]: Monad[RIO[R,*]] = new RIOMonad[R,E] {}

  def rioContravariant[A]: Contravariant[RIO[*, A]] = new Contravariant[RIO[*,A]] {
    def contramap[R,RR](fa: RIO[R,A])(f: RR => R): RIO[RR,A] =
      RIO{ rr => fa.run(f(rr)) }
  }

  def rioDivide[F[_],A](implicit MA: Monoid[A]): Divide[RIO[*,A]] = new Divide[RIO[*,A]] {
    def divide[RR, B, C](f: RR => (B,C), fb: RIO[B,A], fc: RIO[C,A]): RIO[RR,A] = {
      RIO[RR,A]( (rr: RR) => {
        val (b,c) = f(rr)
        MA.combine(fb.run(b),fc.run(c))
      } )
    }

    override def contramap[R, RR](fa: RIO[R, A])(f: RR => R): RIO[RR, A] =
      RIO{ rr => fa.run(f(rr)) }
  }

  def rioProfunctor[E]: Profunctor[RIO[*,*]] = new Profunctor[RIO[*,*]] {
    override def dimap[S,T,A,B](pab: RIO[A,B])(ab: S => A, cd: B => T): RIO[S,T] = RIO((ab andThen pab.run) andThen cd)
  }
}
