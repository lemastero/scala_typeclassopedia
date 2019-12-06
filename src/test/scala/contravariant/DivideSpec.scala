package contravariant

import scalaz.Divide
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class DivideSpec
  extends AnyFunSpec
  with Matchers {

  describe("Divide") {

    case class Serializer[A](run: A => Array[Byte])
    val strSerial = Serializer[String](_.getBytes)
    val intSerial = Serializer[Int](_.toString.getBytes)

    case class Fragment(name: String, size: Int)

    it("custom implementation of Fragment serializer") {

      val fragmentSerial = Serializer[Fragment] { frag =>
        val a1 = strSerial.run(frag.name)
        val a2 = intSerial.run(frag.size)
        a1 ++ a2
      }

      val serialized = fragmentSerial.run(Fragment("Area", 52))
      new String(serialized ) mustBe "Area52"
    }

    it("Fragment serializer using Divide") {

      implicit val fragmentDivide: Divide[Serializer] = new Divide[Serializer] {
        def divide2[A1, A2, Z](s1: => Serializer[A1], s2: => Serializer[A2])(f: Z => (A1, A2)):
            Serializer[Z] = Serializer{ frag =>
          val (a1,a2) = f(frag)
          val serialized1 = s1.run(a1)
          val serializedB = s2.run(a2)
          serialized1 ++ serializedB
        }

        def contramap[A, B](r: Serializer[A])(f: B => A): Serializer[B] = Serializer(f andThen r.run)
      }

      val fragAsTuple: Fragment => (String, Int) = frag => (frag.name, frag.size)
      val fragmentSerial: Serializer[Fragment] = Divide[Serializer].divide(
        strSerial, intSerial)(fragAsTuple)

      val serialized = fragmentSerial.run(Fragment("Area", 52))
      new String(serialized ) mustBe "Area52"
    }
  }
}
