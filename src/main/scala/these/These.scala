package these

/**
 * Non exclusive two values
 *
  * (A + B + AB)
*/

sealed trait These[A,B]
case class This[A, B](a: A) extends These[A,B]
case class That[A,B](b: B) extends These[A,B]
case class Those[A,B](a: A, b: B) extends These[A,B]
