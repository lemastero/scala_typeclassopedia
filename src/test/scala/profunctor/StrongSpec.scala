package profunctor

import profunctor.strong.Strong
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

case class DoubleFun[X,Y](fun: (X,X) => (Y,Y))

trait DoubleFunPro extends Profunctor[DoubleFun] {
  def dimap[X, Y, Z, W](f: X => Y, g: Z => W): DoubleFun[Y, Z] => DoubleFun[X, W] = (fa: DoubleFun[Y, Z]) =>
    DoubleFun[X, W] { (c1, c2) => fa.fun(f(c1), f(c2)) match {case (z1, z2) => (g(z1), g(z2)) } }
}

class StrongSpec
  extends AnyFunSpec
  with Matchers {

  it("Functions from A to B are Strong Profunctors") {
    trait Function1Profunctor extends Profunctor[Function1] {
      def dimap[X, Y, Z, W](ab: X => Y, cd: Z => W): (Y => Z) => X => W = fyz => ab andThen fyz andThen cd
    }

    val functionPro: Profunctor[Function1] = new Function1Profunctor {}

    val functionStrong: Strong[Function1] = new Strong[Function1] with Function1Profunctor {
      def first[X, Y, Z](pab: X => Y): Function1[(X, Z), (Y, Z)] = (xz: (X, Z)) => (pab(xz._1), xz._2)
    }

    def len: String => Int = _.length

    functionStrong.first(len)(("foo", 42)) mustBe (3,42)
  }

  it("You can define multiple Strong Profunctors for P[_,_]") {
    val doubleFunProStrength: Strong[DoubleFun] with DoubleFunPro = new Strong[DoubleFun] with DoubleFunPro {
      def first[A, B, C](fa: DoubleFun[A, B]): DoubleFun[(A, C), (B, C)] = DoubleFun {
        (a1: (A, C), a2: (A, C)) =>
          val bb = fa.fun(a1._1, a2._1)
          ((bb._1, a1._2), (bb._2, a2._2))
      }
    }

    val doubleFunProStrength2: Strong[DoubleFun] with DoubleFunPro = new Strong[DoubleFun] with DoubleFunPro {
      def first[A, B, C](fa: DoubleFun[A, B]): DoubleFun[(A, C), (B, C)] = DoubleFun {
        (a1: (A, C), a2: (A, C)) =>
          val bb = fa.fun(a1._1, a2._1)
          ((bb._1, a1._2), (bb._1, a2._2))
      }
    }

    def bothLength(a: String, b: String): (Int, Int) = (a.length, b.length)

    doubleFunProStrength.first(DoubleFun(bothLength)) must not be doubleFunProStrength2.first(DoubleFun(bothLength))
  }
}
