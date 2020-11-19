package zivariant

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers
import zio.ZIO
import zio.prelude._

class ZivariantSpec extends AnyFunSpec with Matchers with ZivariantSyntax {

  case class EmployeeId(v: Int)
  case class Employee(id: EmployeeId, name: String)

  val db: Map[Int, Employee] = {
    Map(
      0 -> "Delenn",
      1 -> "Dr. Stephen Frankli",
      2 -> "Vir Cotto",
      3 -> "G'Kar",
      4 -> "Londo Mollari",
      5 -> "Michael Garibaldi"
    ).map { case (k, v) => k -> Employee(EmployeeId(k), v) }
  }

  def loadFromDb: Int => Either[Throwable, Employee] = id =>
    db.get(id) match {
      case Some(r) => Right(r)
      case None => Left(new RuntimeException(s"Employee with id [$id] not found"))
    }

  sealed trait AppError
  case class NotFound(details: String) extends AppError

  def getDetails: Employee => String = _.name

  def asDomainError: Throwable => NotFound =
    err => NotFound(err.getMessage)

  def idFromEmployee: EmployeeId => Int = _.v

  it("bimap for function returning Either map result") {
    val employee: Either[Throwable, Employee] = loadFromDb(4)

    val result: Either[NotFound, String] = employee.bimap(asDomainError, getDetails)
    result mustBe Right("Londo Mollari")
  }

  it("bimap for function returning Either transform function") {
    val getEmployeeDetails: Int => Either[NotFound, String] =
      loadFromDb andThen { _.bimap(asDomainError, getDetails) }

    val result: Either[NotFound, String] = getEmployeeDetails(4)
    result mustBe Right("Londo Mollari")
  }

  it("zimap for function returning Either transform input, error channel and output") {
    import Zivariant.FunctionEitherZivariant.zimap

    val getEmployeeDetails: EmployeeId => Either[NotFound, String] =
      zimap(idFromEmployee, asDomainError, getDetails)(loadFromDb)

    val result: Either[NotFound, String] = getEmployeeDetails(EmployeeId(4))
    result mustBe Right("Londo Mollari")
  }

  it("zimap for ZIO transform input, error channel and output") {
    val loadEmployeeFromDb: ZIO[Int, Throwable, Employee] = ZIO.fromFunctionM{ id =>
      ZIO.fromEither(db.get(id) match {
        case Some(r) => Right(r)
        case None => Left(new RuntimeException(s"Employee with id [$id] not found"))
      })
    }

    val loadEmployee: ZIO[EmployeeId, NotFound, String] =
      loadEmployeeFromDb.zimap(idFromEmployee, asDomainError, getDetails)
  }
}
