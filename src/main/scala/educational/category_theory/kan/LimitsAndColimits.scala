package educational.category_theory.kan

/**
  * Limits and colimits are universal objects.
  * Identify things with universal properties (best, biggest, smallest, canonical, free),
  * means for every other things there is nice relationship.
  *
  * ------------------------------------------------------|
  * || Limits   || terminal object | product   | pullback |
  * || ---------||-----------------|----------------------|
  * || Colimits || initial object  | coproduct | pushout  |
  * ------------------------------------------------------|
  *
  * https://www.youtube.com/watch?v=yeQcmxM2e5I&list=PLE337D7DEA972E632&index=1
  *
  * All limits (colimits) of given type are unique up to unique isomorphism.
  */
object LimitsAndColimits {

  /**
    * Terminal object (final object)
    *
    * Given C be a Category. Terminal object in C is object T
    * such that (universal property) given any other object X
    * there is unique morphism from X to terminal object.
    *
    *  X -----> T
    *
    * Examples:
    * - Set: every set with 1 element e.g. {42}
    * - Grp: trivial group
    * - Top: one point space
    * - Poset: maximum element (if exists)
    *
    * Non examples:
    * - Natural numbers (no max)
    * - Vector spaces
    *
    * Terminal objects in C are unique up to unique isomorphism.
    *
    * Terminal object is limit over empty diagram.
    */
  trait TerminalObject {
    type T
    def universalProperty[X]: X => T // universal property
  }

  val typesTerminal: TerminalObject = new TerminalObject {
    type T = Unit
    def universalProperty[A]: A => Unit = _ => ()
  }

  trait InitialObject {
    type T
    def universalProperty[A]: T => A
  }

  val voidInitial: InitialObject = new InitialObject {
    type T = Nothing
    def universalProperty[A]: A => T = ???
  }

  /**
    * Product
    *
    * Product is limit with diagram containing 2 points and no morphisms.
    */
  trait Prod[A,B] {
    type AxB

    // projection maps
    def p: AxB => A
    def q: AxB => B

    def universalProperty[OAxB](op: OAxB => A, oq: OAxB => B): OAxB => AxB
  }

  def cartesianProduct[A,B]: Prod[A,B] = new Prod[A,B] {
    type AxB = (A,B)

    // insertion maps
    def p: AxB => A = { case(a,_) => a}
    def q: AxB => B = { case(_,b) => b}

    def universalProperty[Other](op: Other => A, oq: Other => B): Other => (A, B) =
      o => (op(o), oq(o))
  }

  // TODO for Abelian Group product == coproduct

  /**
    * Coproduct are unique up to unique isomorphism
    */
  trait CoProd[A,B] {
    type AxB
    def p: A => AxB
    def q: B => AxB

    def universalProperty[Other](op: A => Other, oq: B => Other): AxB => Other
  }

  def coProduct[A,B]: CoProd[A,B] = new CoProd[A,B] {
    type AxB = Either[A,B]
    def p: A => AxB = x => Left[A,B](x)
    def q: B => AxB = x => Right[A,B](x)

    def universalProperty[Other](op: A => Other, oq: B => Other): AxB => Other = {
      case Left(a) => op(a)
      case Right(b) => oq(b)
    }
  }

  /**
    * Given: objectC and morphisms: A => C and B => C
    *
    *           B
    *           |
    *           |   g
    *           \/
    * A ------> C
    *     f
    *
    * a pull back (or fiber product) is:
    * - object U
    * - morphisms U => B and U => A
    *
    *       p
    *  U ------> B
    *  |         |
    *  |  q      |   g
    *  \/        \/
    *  A ------> C
    *      f
    *
    * such that (universal property of pullback)
    * for all other V, s: V => B, t: V => A
    *
    *        t
    *   V -------------+
    *   |              |
    *   |        p    \/
    *   |    U ------> B
    * s |    |         |
    *   |  q |         |   g
    *   |    \/  f     \/
    *   +---> A ------> C
    *
    * exists unique morphism h: V => U such that
    * following diagrams commute:
    *
    *        t
    *   V -------------+
    *   | \            |
    *   |  \/     p    \/
    *   |    U ------> B
    * s |    |         |
    *   |  q |         |   g
    *   |    \/  f     \/
    *   +---> A ------> C
    *            f
    *
    * Pullback is limit where diagram with 3 objects A, B, C
    * and morphisms A => C and B => C
    *        B
    *        |
    *        \/
    * A ---> C
    */
  abstract class Pullback[A,B,C](f: A => C, g: B => C) {
    type U
    def p: U => B
    def q: U => A

    def universalProperty[V](s: V => A, t: V => B): V => U
    // laws
    // g(t(v)) == f(s(v))
    // p(h(v)) == t(v)
    // q(h(v)) == s(v)
  }

  // TODO
  case class RestrictedSet[A,B,C](f: A => C, g: B => C)


  def setPullback[A,B,C](f: A => C, g: B => C): Pullback[A,B,C] = new Pullback[A,B,C](f, g) {
    type U = (A,B)
    def p: U => B = {case (a,b) => b }
    def q: U => A = {case (a,b) => a }

    def universalProperty[V](s: V => A, t: V => B): V => U = v => (s(v), t(v))
   }

  /**
    * Equalizer is a limit where diagram with 2 objects A, B
    * and 2 morphisms A => B
    *
    *   --->
    * A ---> B
    */
  abstract class Equalizer[A,B](f: A => B, g: A => B) {
    type U
    def p: U => A
    def q: U => B

    def universalProperty[V](s: V => A, t: V => B): V => U
    // laws
    // g(t(v)) == f(s(v))
    // p(h(v)) == t(v)
    // q(h(v)) == s(v)
  }
}
