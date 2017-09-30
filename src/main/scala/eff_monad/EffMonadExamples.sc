// Examples of Eff Monad from: http://rea.tech/a-journey-into-extensible-effects-in-scala/

case class Error(message: String)

case class PropertyId(value: Long)

case class Property(name: String, id: PropertyId)

case class UserId(value: Long)

case class User(name: String, id: UserId, propertyId: PropertyId)

object SameMonad {

  def getUser(id: UserId): Either[Error, User] =
    if (id.value > 1000) Right(User("Bob", id, PropertyId(123)))
    else Left(Error(s"Id ${id.value} in invalid range"))

  def getProperty(id: PropertyId): Either[Error, Property] =
    if (id.value > 1000) Right(Property("Big house!", id) )
    else Left(Error("Wrong URL!"))

  def getPropertyForUserId(id: UserId): Either[Error, Property] =
    for {
      user <- getUser(id)
      property <- getProperty(user.propertyId)
    } yield property
}

case class PropertyApiUrl(value: String)

object DifferentMonads {
  import cats.data.Reader

  def getUser(id: UserId): Either[Error, User] =
    if (id.value > 1000) Right(User("Bob", id, PropertyId(123)))
    else Left(Error(s"Id ${id.value} in invalid range"))

  def getProperty(id: PropertyId): Reader[PropertyApiUrl, Either[Error, Property]] =
    Reader {
      propertyApiUrl =>
        if (propertyApiUrl.value == "https://production.property-api.com")
          Right(Property("Big house!", id))
        else Left(Error("Wrong URL!"))
    }

  def getPropertyForUserId(id: UserId): Either[Error, Property] = {
    val errorOrUser = getUser(id)
    errorOrUser flatMap { user =>
      val readProperty: Reader[PropertyApiUrl, Either[Error, Property]] = getProperty(user.propertyId)
      readProperty.run(PropertyApiUrl("https://production.property-api.com"))
    }
  }
}

object EffExample {
  import cats.data.Reader
  import org.atnos.eff.Fx
  import org.atnos.eff.Eff
  import org.atnos.eff.MemberIn
  import org.atnos.eff.EitherCreation.{left, right}
  import org.atnos.eff.ReaderCreation.ask
  import org.atnos.eff.syntax.all._

  type _either[R] = MemberIn[Either[Error, ?], R]

  def getUser[R: _either](id: UserId): Eff[R, User] =
    if (id.value > 1000) right(User("Bob", id, PropertyId(123)))
    else left(Error(s"Id ${id.value} in invalid range"))

  type _readerUrl[R] = MemberIn[Reader[PropertyApiUrl, ?], R]

  def getProperty[R: _either : _readerUrl](id: PropertyId): Eff[R, Property] =
    for {
      propertyApiUrl <- ask[R, PropertyApiUrl]
      property <-
      if (propertyApiUrl.value == "https://production.property-api.com")
        right(Property("Big house!", id))
      else left(Error("Wrong URL!"))
    } yield property

  type AppStack = Fx.fx2[Either[Error, ?], Reader[PropertyApiUrl, ?]]

  def getPropertyForUserId2(id: UserId): Either[Error, Property] = {
    val program: Eff[AppStack, Property] = for {
      user <- getUser[AppStack](id)
      property <- getProperty[AppStack](user.propertyId)
    } yield property

    program
      .runReader(PropertyApiUrl("https://production.property-api.com"))
      .runEither[Error]
      .run
  }
}

EffExample.getPropertyForUserId2(UserId(1234))
