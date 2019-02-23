package educational

trait Dijkstra[S,A] {
  def wp[Omega](f: (S,A) => Omega): S => Omega
}

object Dijkstra {

  // State[S,A] => Dijkstra[S,A]
  def fromState[S,A](h: State[S,A]): Dijkstra[S,A] =
    new Dijkstra[S,A] {
      def wp[Omega](f: (S, A) => Omega): S => Omega =
        s => {
          val (a,s2) = h.runState(s)
          f(s2,a)
        }
    }

  // Dijkstra[S,A] => State[S,A]
  def fromDijkstra[S,A](k: Dijkstra[S,A]): State[S,A] = State(
    s => k.wp{ case(s2,a) => (a,s2) }(s)
  )

  // Dijkstra is Monad over second argument
  implicit def dijakstraMonad[S]: Monad[Dijkstra[S, ?]] = new Monad[Dijkstra[S, ?]] {
    def pure[A](a: A): Dijkstra[S,A] = new Dijkstra[S,A] {
      def wp[Omega](f: (S, A) => Omega): S => Omega =
        s => f(s,a)
    }

    def flatMap[A,B](ma: Dijkstra[S,A])(f: A => Dijkstra[S,B]): Dijkstra[S,B] =
      new Dijkstra[S,B] {
        def wp[Omega](g: (S, B) => Omega): S => Omega =
          ma.wp{ case (s,a) => f(a).wp(g)(s) }
      }
  }
}