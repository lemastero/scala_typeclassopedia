package reader

case class Reader[-In, +R](run: In => R) {

  def apply(x: In): R = run(x)

  def map[R2](f: R => R2): Reader[In, R2] =
    Reader(run andThen f)

  def flatMap[R2, In2 <: In](f: R => Reader[In2, R2]): Reader[In2, R2] =
    Reader(x => map(f)(x)(x))
}
