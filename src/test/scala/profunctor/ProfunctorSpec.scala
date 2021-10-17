package profunctor

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import scalaz.Scalaz._
import scalaz._

class ProfunctorSpec
  extends AnyFunSpec
    with Matchers {

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
