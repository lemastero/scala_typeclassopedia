package examples

// example from documentation: http://typelevel.org/cats/datatypes/freemonad.html

import cats.free.Free
import cats.free.Free.liftF

// TODO change into unit tests

sealed trait KVStoreA[A]
final case class Put[T](key: String, value: T) extends KVStoreA[Unit]
final case class Get[T](key: String) extends KVStoreA[Option[T]]
final case class Delete(key: String) extends KVStoreA[Unit]

object KeyValueStoreFreeMonad extends App {

  // 1 free type
  type KVStore[A] = Free[KVStoreA, A]

  // 2 DSL
  def put[T](key: String, value: T): KVStore[Unit] = liftF[KVStoreA, Unit](Put[T](key, value))

  def get[T](key: String): KVStore[Option[T]] = liftF[KVStoreA, Option[T]](Get[T](key))

  def delete(key: String): KVStore[Unit] = liftF(Delete(key))

  def update[T](key: String, f: T => T): KVStore[Unit] =
    for {
      vMaybe <- get[T](key)
      _ <- vMaybe.map(v => put[T](key, f(v))).getOrElse(Free.pure(()))
    } yield ()

  // 3 program
  def program: KVStore[Option[Int]] =
    for {
      _ <- put("wild-cats", 2)
      _ <- update[Int]("wild-cats", (_ + 12))
      _ <- put("tame-cats", 5)
      n <- get[Int]("wild-cats")
      _ <- delete("tame-cats")
    } yield n

  // 4 compiler, using natural transformation (~> or FunctionK)
  // compiler could use Future, Option, Either etc instead of Id
//  import cats.arrow.FunctionK
//  import cats.{Id, ~>}
//  import scala.collection.mutable

  /* TODO it does not compile for me :(
  def impureCompiler: KVStoreA ~> Id =
    new (KVStoreA ~> Id) {
      val kvs = mutable.Map.empty[String, Any]

      def apply[A](fa: KVStoreA[A]): Id[A] = // Id[A] will not compile
        fa match {
          case Put(key, value) =>
            println(s"put($key, $value)")
            kvs(key) = value
          case Get(key) =>
            println(s"get($key)")
            kvs.get(key).map(_.asInstanceOf[A])
          case Delete(key) =>
            println(s"delete($key)")
            kvs.remove(key)
            ()
        }
    }

  // 5 run
  val result: Option[Int] = program.foldMap(impureCompiler)
  println(result)
  */
}
