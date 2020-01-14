package examples

import cats.arrow.Arrow
import cats.implicits.catsStdInstancesForFunction1

/**
  * Translated from post by geophf:
  * http://logicaltypes.blogspot.com/2014/02/arrow-is-spelt-fizz-buzz.html
import Control.Arrow

predfb :: String -> Int -> Int -> Either String Int
predfb str modulo x
  | x `mod` modulo == 0 = Left str
  | otherwise =           Right x

fizz = predfb "fizz" 3
buzz = predfb "buzz" 5

fbprinter :: (Either String Int, Either String Int) -> String
fbprinter (Left x, Left y) = x ++ y
fbprinter (Left x, _) = x
fbprinter (_, Left y) = y
fbprinter (Right num, _) = show num

fizzbuzz = [1..100] >>= return . (fizz &&& buzz >>> fbprinter)
  */
object ArrowFizBuzz
  extends App { // TODO change into unit tests

  def predfb(str: String, modulo: Int)(x: Int): Either[String, Int] =
    if(x % modulo == 0) Left(str) else Right(x)

  val fizz: Int => Either[String, Int] = predfb("fizz", 3)
  val buzz: Int => Either[String, Int] = predfb("buzz", 5)

  def fbprinter(pair: (Either[String, Int], Either[String, Int])): String =
    pair match {
      case (Left(x), Left(y)) => x + y
      case (Left(x), _) => x
      case (_, Left(y)) => y
      case (Right(n), _) => s"$n"
    }

  // there seem to be no &&& alternative in cats
  // there is in Haskell and
  // Scalaz: https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Arrow.scala
  //
  (1 until 100)
    .map( v => (v,v))
    .map( Arrow[Function1].split(fizz, buzz) )
    .map( fbprinter )
    .foreach( println )
}
