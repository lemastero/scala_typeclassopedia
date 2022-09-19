# Optics
* Putting Lenses to Work - John Wiegley [(video)](https://www.youtube.com/watch?v=QZy4Yml3LTY) [slides](https://github.com/jwiegley/putting-lenses-to-work) [(blog post)](http://newartisans.com/2017/04/putting-lenses-to-work/)
* Taking Updates Seriously - Danel Ahman, Tarmo Uustalu [(paper)](http://ceur-ws.org/Vol-1827/paper11.pdf)
* (Haskell) Optics in Haskell - Functional Tricity #13 - Paweł Sączawa [(video)](https://www.youtube.com/watch?v=X-OxRPT_u0A)

## Iso
* Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA)

## Prism
* Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA&t=960)

## Lens
* Resources:
  * Beyond Scala Lenses - Julien Truffaut: [(video)](https://www.youtube.com/watch?v=6nyGVgGEKdA&t=1694)
  * scalaz [Lens src](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/Lens.scala) [(PLens src)](https://github.com/scalaz/scalaz/blob/series/7.3.x/core/src/main/scala/scalaz/PLens.scala)

## Van Laarhoven Optics

* [Monocle Learning Resources](https://julien-truffaut.github.io/Monocle/learning_resources.html)
* [Twan van Laarhoven: CPS based functional references](https://www.twanvl.nl/blog/haskell/cps-functional-references)
* [Functor Optics](http://oleg.fi/gists/posts/2017-12-23-functor-optics.html)

## Profunctor Optics

Optics (Iso, Lens, Prism, etc) can be implemented using different Profunctors.
If we have following Profunctor hierarchy:

```scala
trait Strong[P[_, _]] extends Profunctor[P] {
  def first[A,B,C](pab: P[A, B]): P[(A, C), (B, C)]
}

trait Choice[P[_, _]] extends Profunctor[P] {
  def left[A,B,C](pab: P[A, B]):  P[Either[A, C], Either[B, C]]
  def right[A,B,C](pab: P[A, B]): P[Either[C, A], Either[C, B]] = dimap(_.swap, _.swap)(left(pab))
}

trait Step[P[_,_]] extends Choice[P] with Strong[P] {
  def step[A,B,C,D](pab: P[A,B]): P[Either[D, (A,C)], Either[D, (B,C)]] = right(first(pab))
}

trait Walk[P[_,_]] extends Step[P] {
  def walk[A,B,F[_]](pab: P[A,B])(implicit FT: Traverse[F]): P[F[A], F[B]]
}

trait Settable[P[_,_]] extends Walk[P] {
  def mapping[A,B,F[_]](pab: P[A,B])(implicit FT: Functor[F]): P[A,B] => P[F[A], F[B]]
}

trait Closed[P[_,_]] extends Profunctor[P] {
  def closed[A,B,X](pab: P[A,B]): P[X=>A, X=>B]
}
```

Then optics can be expressed in following way:

```scala
trait Iso[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit P: Profunctor[P]): P[S, T]
}

trait Lens[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit S: Strong[P]): P[S, T]
}

trait Prism[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit C: Choice[P]): P[S, T]
}

trait AffineTraversal[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit S: Step[P]): P[S, T]
}

trait Traversal[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit W: Walk[P]): P[S, T]
}

trait SEC[S, T, A, B] {
  def run[P[_, _]](pab: P[A, B])(implicit S: Settable[P]): P[S, T]
}

trait Grates[S, T, A, B] { // https://r6research.livejournal.com/28050.html
  def run[P[_,_]](pab: P[A, B])(implicit C: Closed[P]): P[S, T]
}
```

* Resources:
  * Bartosz Milewski - Profunctor Optics: The Categorical Approach [(video)](https://www.youtube.com/watch?v=l1FCXUi6Vlw)
  * Mainline Profunctor Heirarchy for Optics [(blog post)](https://r6research.livejournal.com/27476.html)
  * Jeremy Gibbons - Profunctor Optics Modular Data Accessors [(video)](https://www.youtube.com/watch?v=sfWzUMViP0M)
  * Grate: A new kind of Optic: [(blog post)](https://r6research.livejournal.com/28050.html)
  * Tambara Modules - Brendan Fong [(video)](https://www.youtube.com/watch?v=67hJW6J4Mic)
  * Profunctor optics, a categorical update - Mario Román, Bryce Clarke, Fosco Loregian, Emily Pillmore, Derek Elkins, Bartosz Milewski [(video)](https://www.youtube.com/watch?v=ceCwD7L0t3w), [(slides)](http://events.cs.bham.ac.uk/syco/strings3-syco5/slides/roman.pdf), [(gist)](https://gist.github.com/emilypi/407838d9c321d5b21ebc1828ad2bedcb)