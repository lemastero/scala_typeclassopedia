package category

trait Category[K[_,_]] {
  def id[A]: K[A,A]
  def compose[A,B,C](f: K[B,C])(g: K[A,B]): K[A,C]
}
