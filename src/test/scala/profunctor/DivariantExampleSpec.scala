package educational.category_theory.two.profunctor

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import zio.prelude._

class DivariantExampleSpec extends AnyFunSpec with Matchers {

  describe("Divariant") {
    it("Divariant for Function1 is Covariant + Contravariant") {
      case class Person(name: String, age: Int)

      val len: String => Int = _.length
      val getPersonName: Person => String = _.name
      val above5: Int => Boolean = _ > 5

      val personNameAbove5 = len.dimap(getPersonName, above5)
      personNameAbove5(Person("Foo", 100)) mustBe false
    }
  }
}
