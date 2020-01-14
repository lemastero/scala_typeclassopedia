package examples

import cats.arrow.Arrow
import cats.implicits._

/**
  * Examples inspired by Eugene Yokota learning Scalaz
  * http://eed3si9n.com/learning-scalaz/Arrow.html
  */
object ArrowBasicOps
  extends App { // TODO change into unit tests
    val inc: Int => Int = _ + 1
    val inc2: Int => Int = _ * 2
    val len: String => Int = _.length
    val times10: Int => Int = _ * 10
    val comb: (Int, Int) => String = _.toString + _.toString

    val a1 = Arrow[Function1].lift(times10)
    val a2 = Arrow[Function1].lift(inc)

    val r1 = Arrow[Function1].andThen(len, inc)("foo") // <<<
    val r2 = Arrow[Function1].compose(inc, len)("foo") // >>>
    val r3 = a1.dimap(inc)(inc2)(10)
    val r4 = a1.split(inc)((50,60)) // ***
    val r5 = Arrow[Function1].split(times10, inc)((50,60))

    println(List( r1, r2, r3, r4, r5 ).mkString(", "))
    println( (a1 >>> a2)(5) )
    println( (a1 <<< a2)(5) )
    println( a1.second((1,2)) )
}
