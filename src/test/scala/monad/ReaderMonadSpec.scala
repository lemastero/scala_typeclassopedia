package monad

import scala.concurrent.duration.Duration
import scala.concurrent.duration._
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

case class Reader[A, B](run: A => B) { // B is changing and A is constant

  def map[C](f: B => C): Reader[A, C] = Reader(run andThen f)

  def flatMap[C](f: B => Reader[A, C]): Reader[A, C] = {
//    def attempt1: A => Reader[A, C] = a => f(run(a))
//    def attempt2: A => (A => C) = (a:A) => f(run(a)).run
    def attempt3: A => C = (a:A) => {
      val b: B = run(a)
      val reader: Reader[A, C] = f(b)
      reader.run(a)
    }
    Reader(attempt3)
  }

  def flatten(r: Reader[A, Reader[A, B]]): Reader[A, B] = { // pass the context A to outer computation and inner computation
    def newRun: A => B = a => {
      val firstRun: Reader[A, B] = r.run(a)
      firstRun.run(a)
    }
    Reader(newRun)
  }
}

object Reader {

  def ask[A]: Reader[A, A] = Reader(identity)
}

class ReaderMonadSpec
  extends AnyFunSpec
  with Matchers {

  describe("Reader") {
    case class DbConfig(maxTimeout: Duration, jdbc: String, user: String, pass: String)

    val baseConfig = DbConfig(60.seconds, "jdbc://localhost:5432/db", "admin", "1234")
    type FindById = Int => String

    def getAllCustomerIds: DbConfig => List[Int] = _ => List(1, 42) // it pass the tests so who cares
    def getDefaultCustomer: DbConfig => FindById = conf => id => s"Johnny Bravo $id"

    it("produces something from configuration") {
      val reader: Reader[DbConfig, (Int => String)] = Reader(getDefaultCustomer)
      reader.run(baseConfig)(1) mustBe "Johnny Bravo 1"
    }

    it("is like Functor") {
      val reader: Reader[DbConfig, List[Int]] = Reader(getAllCustomerIds)
      reader.map(_.mkString(", ")).run(baseConfig) mustBe "1, 42"
    }
  }
}
