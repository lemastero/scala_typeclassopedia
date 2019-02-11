package educational

class ReverseEngineerMachines {

  trait Machine01[A] {
    def runMachine[B](f: A => B): B
  }

  def newAlienDevice[A]: Machine01[A] = ???

  // can we by trial and error figure out what
  // alienDevice.run do?
}
