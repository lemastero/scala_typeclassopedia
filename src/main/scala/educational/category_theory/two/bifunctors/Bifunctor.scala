package educational.category_theory.two.bifunctors

/**
  * Type constructor with two parameters
  */
trait Bifunctor[P[_,_]]
  extends Clown[P]
    with Joker[P] {

  def bimap[A,AA,B,BB](fa: P[A,B])(f: A => AA, g: B => BB): P[AA,BB]

  override def map[A,B,BB](fa: P[A,B])(g: B => BB): P[A,BB] = bimap(fa)(identity, g)
  override def mapLeft[A,AA,B](fa: P[A,B])(g: A => AA): P[AA,B] = bimap(fa)(g, identity[B])
}

// map (f compose g) ≡ map f compose map g - inherited from Joker
trait BifunctorLaws[P[_,_]]
  extends Bifunctor[P]
    with ClownLaws[P]
    with JokerLaws[P] {

  def bimapDistributeLaw[A1,A2,A3,B1,B2,B3](fa: P[A1,B1], f: A2 => A3, g: A1 => A2, h: B2 => B3, i: B1 => B2): Boolean = {
    val v1: P[A3, B3] = bimap(fa)(f compose g, h compose i)
    val w1: P[A2, B2] = bimap(fa)(g,i)
    val w2: P[A3, B3] = bimap(w1)(f,h)
    v1 == w2
  }

  def bimapIsMapAndLeftMap[A,AA,B,BB](fa: P[A,B], f: A => AA, g: B => BB): Boolean = {
    val v1: P[AA, BB] = bimap(fa)(f, g)
    val w1: P[AA, B] = mapLeft(fa)(f)
    val w2: P[AA, BB] = map(w1)(g)
    v1 == w2
  }
}

object BifunctorInstances {

  implicit val tupleBifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {
    def bimap[A,AA,B,BB](fa: (A,B))(f: A => AA, g: B => BB): (AA,BB) =
      fa match { case (a,c) => (f(a), g(c)) }
  }

  implicit val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
    def bimap[A,B,C,D](fa: Either[A,C])(f: A => B, g: C => D): Either[B,D] = fa match {
      case Left(a) => Left(f(a))
      case Right(c) => Right(g(c))
    }
  }

  implicit def tuple3BifunctorXab[X]: Bifunctor[(X,*,*)] = new Bifunctor[(X,*,*)] {
    def bimap[A,AA,B,BB](fa: (X,A,B))(f: A => AA, g: B => BB): (X,AA,BB) =
      { fa match { case (x,a,b) => (x, f(a), g(b)) } }
  }

  implicit def tuple3BifunctoraXb[X]: Bifunctor[(*,X,*)] = new Bifunctor[(*,X,*)] {
    def bimap[A,AA,B,BB](fa: (A,X,B))(f: A => AA, g: B => BB): (AA,X,BB) =
      { fa match { case (a,x,b) => (f(a), x, g(b)) } }
  }

  implicit def tuple3BifunctorabX[X]: Bifunctor[(*,*,X)] = new Bifunctor[(*,*,X)] {
    def bimap[A,AA,B,BB](fa: (A,B,X))(f: A => AA, g: B => BB): (AA,BB,X) =
      { fa match { case (a,b,x) => (f(a), g(b), x) } }
  }

  implicit def tuple4Bifunctor[X1,X2]: Bifunctor[(X1,X2,*,*)] = new Bifunctor[(X1,X2,*,*)] {
    def bimap[A,B,C,D](fa: (X1,X2,A,C))(f: A => B, g: C => D): (X1,X2,B,D) =
      fa match { case (a,b,c,d) => (a, b, f(c), g(d)) }
  }

  implicit def tuple4BifunctorXaXb[X1,X2]: Bifunctor[(X1,*,X2,*)] = ??? // TODO
  implicit def tuple4BifunctorXabX[X1,X2]: Bifunctor[(X1,*,*,X2)] = ??? // TODO
  implicit def tuple4BifunctoraXXb[X1,X2]: Bifunctor[(*,X1,X2,*)] = ??? // TODO
  implicit def tuple4BifunctoraXbX[X1,X2]: Bifunctor[(*,X1,*,X2)] = ??? // TODO
  implicit def tuple4BifunctorabXX[X1,X2]: Bifunctor[(*,*,X1,X2)] = ??? // TODO

  // probability of this abstraction being usefull is very small
  // in the same time amount of code needed to write them increase
}
