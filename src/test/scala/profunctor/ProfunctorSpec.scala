package profunctor

import org.scalatest.{FunSpec, MustMatchers}
import scalaz._
import Scalaz._

class ProfunctorSpec
  extends FunSpec
    with MustMatchers {

  describe("Profunctor") {
    it("custoom contramap for Predicate on String and Int") {
      val f: String => Int = _.length

      case class Person(name: String, age: Int)
      val preF: Person => String = _.name

      val postF: Int => Boolean = _ > 5

      Profunctor[Function1].dimap(f)(preF)(postF)(Person("Foo", 100)) mustBe false
    }
  }
}
