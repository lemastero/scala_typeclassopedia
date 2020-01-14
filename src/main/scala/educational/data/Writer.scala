package educational.data

final case class Writer[R, A](runWriter: A => (R, A))
