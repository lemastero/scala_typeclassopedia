package educational.data

import educational.collections.HeadNel

object Validated {
  sealed trait Validated[+A, +E]
  case class SuccessV[A](result: A) extends Validated[A, Nothing]
  case class ErrorV[E](error: E) extends Validated[Nothing, E]
}

object ValidatedNel {
  sealed trait ValidatedNel[+A, +E]
  case class SuccessVN[A](result: A) extends ValidatedNel[A, Nothing]
  case class ErrorVN[E](error: E) extends ValidatedNel[Nothing, E]
  case class ErrorsVN[E](errors: HeadNel[E]) extends ValidatedNel[Nothing, E]
}

object ValidatedUnified {
  sealed trait Validated[+A, +E]
  sealed trait ValidatedNel[+A, +E]
  case class SuccessV[A](result: A) extends ValidatedNel[A, Nothing] with Validated[A, Nothing]
  case class ErrorV[E](error: E) extends ValidatedNel[Nothing, E] with Validated[Nothing, E]
  case class ErrorsV[E](error: E, errors: HeadNel[E]) extends ValidatedNel[Nothing, E]
}
