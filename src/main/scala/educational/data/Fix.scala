package educational.data

import educational.category_theory.Functor

// Matryoshka: https://github.com/precog/matryoshka/blob/master/core/shared/src/main/scala/matryoshka/data/Fix.scala
// Y combinator on type level
case class Fix[F[_]](unFix: F[Fix[F]])
