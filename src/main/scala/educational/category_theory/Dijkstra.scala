package educational.category_theory

import educational.data.State

trait Dijkstra[S,A] {
  def runWp[R](f: (S,A) => R): S => R
}

object Dijkstra {

  // State[S,A] => Dijkstra[S,A]
  def fromState[S,A](h: State[S,A]): Dijkstra[S,A] =
    new Dijkstra[S,A] {
      def runWp[R](f: (S, A) => R): S => R =
        s => {
          val (a,s2) = h.runState(s)
          f(s2,a)
        }
    }

  // Dijkstra[S,A] => State[S,A]
  def fromDijkstra[S,A](k: Dijkstra[S,A]): State[S,A] = State(
    s => k.runWp{ case(s2,a) => (a,s2) }(s)
  )

  // Dijkstra is Monad over second argument
  implicit def dijakstraMonad[S]: Monad[Dijkstra[S, ?]] = new Monad[Dijkstra[S, ?]] {
    def pure[A](a: A): Dijkstra[S,A] = new Dijkstra[S,A] {
      def runWp[R](f: (S, A) => R): S => R =
        s => f(s,a)
    }

    def flatMap[A,B](ma: Dijkstra[S,A])(f: A => Dijkstra[S,B]): Dijkstra[S,B] =
      new Dijkstra[S,B] {
        def runWp[R](g: (S, B) => R): S => R =
          ma.runWp{ case (s,a) => f(a).runWp(g)(s) }
      }
  }
}