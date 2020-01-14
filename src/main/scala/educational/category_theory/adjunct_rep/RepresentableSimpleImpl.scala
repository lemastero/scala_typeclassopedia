package educational.category_theory.adjunct_rep

import educational.data.Reader

/*
Haskell implementation:
based on:
 Bartosz Milewski - Category Theory II 4.1: Representable Functors - https://www.youtube.com/watch?v=KaBz45nZEZw

 {-# LANGUAGE TypeFamilies #-}

class Representable f where
  type Rep f :: *
  tabulate :: (Rep f -> x) -> f x
  index :: f x -> Rep f -> x

data Stream a = Cons a (Stream a)

instance Representable Stream where
 type Rep Stream = Integer
 tabulate f = Cons (f 0) (tabulate (f . (+ 1)))
 index (Cons x xs) n =
    if n == 0 then x
    else index xs (n - 1)

 and: https://github.com/typelevel/CT_from_Programmers.scala/blob/master/src/main/tut/2.4-representable-functors.md
*/
final case class Stream[T](
                            value: () => T,
                            tail: () => Stream[T]) {

  def get(ind: Int): T = {
    if(ind <= 0) value()
    else tail().get(ind - 1)
  }
}

final case class Pair[A](fst: A, snd: A)

object Representable {

  def repReader[In]: Representable[Reader[In, ?], In] = new Representable[Reader[In, ?], In] {
    def tabulate[X](f: In => X): Reader[In, X] = Reader(f)
    def index[X](fa: Reader[In, X]): In => X = fa.run
  }

  val repFunction0: Representable[Function0, Unit] = new Representable[Function0, Unit] {
    def tabulate[X](f: Unit => X): Function0[X] = () => f(())
    def index[X](fa: Function0[X]): Unit => X = _ => fa()
  }

  // TODO use Scala Stream
  val streamRep: Representable[Stream, Int] = new Representable[Stream, Int] {
    def tabulate[X](f: Int => X): Stream[X] =
      Stream(
        value = () => f(0),
        tail = () => tabulate(f compose (_ + 1)))

    def index[X](fx: Stream[X]): Int => X = fx.get
  }

  val pair2Rep: Representable[Pair, Boolean] = new Representable[Pair, Boolean] {
    def tabulate[X](f: Boolean => X): Pair[X] = Pair(f(false), f(true))
    def index[X](fa: Pair[X]): Boolean => X = b => if(b) fa.snd else fa.fst
  }

  type UniformPair[P] = Tuple2[P, P]
  val uniformPairRep: Representable[UniformPair, Boolean] = new Representable[UniformPair, Boolean] {
    def tabulate[X](f: Boolean => X): UniformPair[X] = (f(false), f(true))
    def index[X](fa: UniformPair[X]): Boolean => X = b => if(b) fa._2 else fa._1
  }

  type UniformTriple[P] = Tuple3[P, P, P]
  val uniformTripleRep: Representable[UniformTriple, Option[Boolean]] = new Representable[UniformTriple, Option[Boolean]] {
    def tabulate[X](f: Option[Boolean] => X): UniformTriple[X] = (f(None), f(Some(false)), f(Some(true)))
    def index[X](fa: UniformTriple[X]): Option[Boolean] => X = b => b.map{ rawB => if(rawB) fa._3 else fa._2 }.getOrElse(fa._1)
  }
}

/**
  * Representable Functors.
  *
  * Representable endofunctors over category of Scala types are isomorphic to the Reader Monad.
  * So they inherit a very large number of properties for free
  *
  * class Distributive f => Representable f where
  *   type Rep f :: *
  *   tabulate :: (Rep f -> a) -> f a
  *   index    :: f a -> Rep f -> a
  *
  * tabulate . index  ≡ id
  * index . tabulate  ≡ id
  * tabulate . return ≡ return
  *
  * fmap f . tabulate ≡ tabulate . fmap f
  */
trait Representable[F[_], RepF] { self =>
  def tabulate[X](f: RepF => X): F[X] // memoization - convert function into Container
  def index[X](fa: F[X]): RepF => X
}


object RepresentableSimpleImpl extends App {

  val streamRep = Representable.streamRep

  def fooN(i: Int) =  s"foo $i"
  val tabulatedFooNs: Stream[String] = streamRep.tabulate(fooN)

  def second[A](s: Stream[A]): A = s.tail().value()

  println(second(tabulatedFooNs))
  println(streamRep.index(tabulatedFooNs)(2))
}
