package functor

import functor.Tree.treeFunctor.map
import org.scalatest.{MustMatchers, WordSpec}

class InstanceForCustomTreeSpec
  extends WordSpec
  with MustMatchers {

  "map over Tree" when {
    "called with Leaf" must {
        "execute function for value stored inside Left" in {
          map(Leaf("foo"))(stringLength) mustBe Leaf(3)
          map(Leaf("foo"))(toUpperCase) mustBe Leaf("FOO")
      }
    }

    "called with Branch" must {
      "execute function for every subtree" in {
        map(Branch(Leaf("foo"), Leaf("bar")))(stringLength) mustBe Branch(Leaf(3), Leaf(3))
        map(Branch(Leaf("foo"), Branch(Leaf("quux"), Leaf("foobar"))))(toUpperCase) mustBe Branch(Leaf("FOO"), Branch(Leaf("QUUX"), Leaf("FOOBAR")))
      }
    }
  }

  private def stringLength(s: String) = s.length
  private def toUpperCase(s: String) = s.toUpperCase
}
