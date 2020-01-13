package educational.data

case class Compose[F[_],G[_],A](v: F[G[A]])
