package ct_other_impls

object DataCategory {
  type Identity[Morphism[_, _], A] = Morphism[A, A]
  /* object is represented as identity morphism */
  type TheObject[Morphism[_, _], A] = Identity[Morphism,A]

  trait Category[Morphism[_,_]] {
    def source[A,B](f: Morphism[A,B]): TheObject[Morphism,A]
    def target[A,B](f: Morphism[A,B]): TheObject[Morphism,B]
    def compose[A,B,C](f: Morphism[B,C], g: Morphism[A,B]): Morphism[A,C]
  }

  /** Category of types and one argument pure functions between them */
  val TypesAndPureOneArgumentFunctionsCategory: Category[Function1] = new Category[Function1] {
    def source[A, B](f: A => B): TheObject[Function, A] = identity[A]
    def target[A, B](f: A => B): TheObject[Function, B] = identity[B]
    def compose[A, B, C](f: B => C, g: A => B): A => C = f compose g
  }

  /** Opposite category */
  case class Op[K[_,_],A,B](unOp: K[B,A])

  def oppositeCategory[K[_,_]](implicit CC: Category[K]): Category[Op[K, *, *]] = new Category[Op[K, *, *]] {
    def source[A, B](f: Op[K, A, B]): TheObject[Op[K, *, *], A] = Op(CC.target(f.unOp))
    def target[A, B](f: Op[K, A, B]): TheObject[Op[K, *, *], B] = Op(CC.source(f.unOp))
    def compose[A, B, C](f: Op[K, B, C], g: Op[K, A, B]): Op[K, A, C] = Op(CC.compose(g.unOp, f.unOp))
  }

  /** 1 category */
  object TheOne
  type One[A,B] = TheOne.type

  val oneCategory: Category[One] = new Category[One] {
    def source[A, B](f: One[A, B]): TheObject[One, A] = TheOne
    def target[A, B](f: One[A, B]): TheObject[One, B] = TheOne
    def compose[A, B, C](f: One[B, C], g: One[A, B]): One[A, C] = TheOne
  }

  /** In Haskell Boolean, Void categories are encoded using GADTs :( */
}
