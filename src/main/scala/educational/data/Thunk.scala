package educational.data

case class Thunk[A](run: () => A)
