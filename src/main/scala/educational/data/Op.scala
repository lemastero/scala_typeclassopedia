package educational.data

case class Op[K[_,_],A,B](unOp: K[B,A])