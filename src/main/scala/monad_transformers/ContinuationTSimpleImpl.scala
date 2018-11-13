package monad_transformers

object ContinuationTSimpleImpl {

  /*
  Continutation monad transformer

  newtype ContT r m a = ContT
    { runContT :: (a -> m r) -> m r }
   */
  trait ContT[R, M[_], A] {
    def runContT(amr: A => M[R]): M[R]
  }
}
