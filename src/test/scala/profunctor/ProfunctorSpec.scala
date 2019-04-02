package profunctor

import org.scalatest.{FunSpec, MustMatchers}
import scalaz._
import Scalaz._

class ProfunctorSpec
  extends FunSpec
    with MustMatchers {

  describe("Profunctor") {
    it("Profunctor for Function1 is Functor + Contravariant") {
      case class Person(name: String, age: Int)

      val len: String => Int = _.length // String => Int
      val getPersonName: Person => String = _.name // Person => String
      val above5: Int => Boolean = _ > 5 // Int => Boolean

      val personNameAbove5 = Profunctor[Function1].dimap(len)(getPersonName)(above5) // AA => BB
      personNameAbove5(Person("Foo", 100)) mustBe false
    }
  }
}
