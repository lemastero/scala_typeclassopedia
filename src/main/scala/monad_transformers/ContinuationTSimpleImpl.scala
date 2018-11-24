package monad_transformers

import scala.language.higherKinds

object ContinuationTSimpleImpl {

  /** Continutation monad transformer */
  trait ContT[R, M[_], A] {
    def runContT(amr: A => M[R]): M[R]
  }

  object ContT {
    def apply[A,R](f: A => R) = ???
  }
}
