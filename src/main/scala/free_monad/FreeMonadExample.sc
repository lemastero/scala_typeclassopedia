// Free
// we want to interact with external world
//   => have side effects
//   => so we don't work with pure functions

// to handle effects with pure functions we can
// build description of a program with effects instead of

// TODO effects to play with
// reading from / writing to console
// reading from / writing to file
// send request to REST service / receive response
// receive response after some delay
// read from / write to DB

// receive input from user (mouse click etc)

object Free01 {

  // algebra
  // each action represent side effect and continuation (next of the program)
  sealed trait ConsoleIO
  final case class WriteLine(line: String, next: ConsoleIO)
    extends ConsoleIO
  final case class ReadLine(process: String => ConsoleIO)
    extends ConsoleIO
  final case object End extends ConsoleIO

  // describe program (without side effects)
  WriteLine(
    line = "Hello stranger, what is your name?",
    next = ReadLine {
      name =>
        WriteLine(
          line = s"Oh, hello $name! I did not recognize you!",
          next = End)
    }
  )

  // TODO you probably need interpreter to run it
}

// Problem 1: we cannot compose those, End does not return value
// so we cannot pass one program to another one

object Free02 {
  import scala.util.Try

  // algebra
  sealed trait ConsoleIO[A]
  final case class WriteLine[A](line: String, next: ConsoleIO[A])
    extends ConsoleIO[A]
  final case class ReadLine[A](process: String => ConsoleIO[A])
    extends ConsoleIO[A]
  final case class End[A](value: A) extends ConsoleIO[A]

  // describe program (without side effects)
  val greeting = WriteLine(
    line = "Hello stranger, what is your name?",
    next = ReadLine { name =>
      WriteLine(
        line = s"Oh, hello $name! I did not recognize you!",
        next = End(name)
      )
    }
  )

  val guess = WriteLine(
    line = "Can you guess the value between 1 and 10?",
    next = ReadLine { input =>
      val response = Try(input.toInt)
        .map{ n =>
          if(n == 7) "GMTA m8!" else s"$n is a good choice"}
        .getOrElse(s"I dont think $input is a number :)")
      WriteLine(response, End(input))
    }
  )

  // TODO how to pass value from greeting to guess ?
  // TODO you probably need interpreter to run it
}

// Problems

// Going beyond Free
