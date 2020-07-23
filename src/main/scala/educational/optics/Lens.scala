package educational.optics

case class Lens[+A,-B,-S,+T](
  view: S => A,
  update: (B,S) => T
)

object LensExamples {

  def tupleFoucsFirst[A,B,C]: Lens[A, B, (A,C), (B,C)] = {
    def getFst: ((A,C)) => A = { case (a, _) => a }
    def overrideFst: (B,(A,C)) => (B,C) = { case (b, (_,c)) => (b,c) }
    Lens[A, B, (A,C), (B,C)](getFst, overrideFst)
  }

  def tupleFoucsSecond[A,B,C]: Lens[A, B, (A,C), (B,C)] = {
    def getFst: ((A,C)) => A = { case (a, _) => a }
    def overrideFst: (B, (A,C)) => (B,C) = { case (b, (_, c)) => (b, c) }
    Lens[A, B, (A,C), (B,C)](getFst, overrideFst)
  }

  // monomorphic - types have to be the same for A,B and S,T
  val signNonNegative: Lens[Boolean, Boolean, Int, Int] = {
    def isNonNegative: Int => Boolean = _ >= 0
    def asNonNegative: (Boolean,Int) => Int = { case(b,n) =>
      if(b) Math.abs(n)
      else -Math.abs(n)
    }

    Lens[Boolean, Boolean, Int, Int](isNonNegative, asNonNegative)
  }

  // Nat and isOdd would work
  // Nat and isPrime would be tricky (enforcing property could be hard)

  // compsing lens
//  def tupleInnerFirst[A,B,C,D]: Lens[A, B, ((A,C),D), ((B,C),D)] = {
//    def overrideFst: (B,(A,C)) => (B,C) = { case (b, (_,c)) => (b,c) }
//    Lens[A, B, ((A,C),D), ((B,C),D)](tupleFoucsFirst.view andThen tupleFoucsFirst.view, overrideFst)
//  }

  // TODO finish + check if we use Option instead of Either if that helps?
}