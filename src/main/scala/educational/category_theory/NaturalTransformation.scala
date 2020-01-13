package educational.category_theory

trait ~>[F[_], G[_]] {
  def apply[A](fa: F[A]): G[A]
}
