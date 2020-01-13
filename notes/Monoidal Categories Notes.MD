# Notes on Monoidal Categories

Category theory models: composition
Monoidal category theory add idea of parallelism: composition + other concept (product, sum, parallelism)

# [Monoidal category](https://ncatlab.org/nlab/show/monoidal+category)

`Monoidal category` `(C,I,α,λ,ρ)` is a category `C` with:
* a bifunctor (`tensor product`) `⊗: (C,C) -> C`
* object (`unit object` or `tensor unit`)  `I: C`
* natural isomorphisms  
  * (`associator`) `α: (A⊗B)⊗C ~ A⊗(B⊗C)`
  * (`left unitor`) `λ: I ⊗ A ~ A`
  * (`right unitor`) `ρ: A ⊗ I ~ A`
* that satisfy `triangle equations`

![](../img/mon_cat_triangle.svg)

* and `pentagon equations`

![](../img/monoidal_pentagon.svg)

Following implementation in Scala 2 allows to reason about categories

```scala
trait MonoidalCategory[M[_, _], I] {
  val mcBif: Bifunctor[M]
  val mcId: I

  def rho[A]    (mai: M[A,I]): A
  def rho_inv[A](a:   A):      M[A, I]

  def lambda[A]      (mia: M[I,A]): A
  def lambda_inv[A,B](a: A):        M[I, A]

  def alpha[A,B,C](    mabc: M[M[A,B], C]): M[A, M[B,C]]
  def alpha_inv[A,B,C](mabc: M[A, M[B,C]]): M[M[A,B], C]
}

trait MonoidalCategoryLaws[M[_, _], I] extends MonoidalCategory[M,I] {

  def triangleEquations[A, B](mai: M[M[A, I], B]): Boolean = {
    //               ro[A] * id[B]
    // (A * I) * C ----------------> A * B
    val v1: M[A, B] = mcBif.bimap(rho[A], identity[B])(mai)

    //              alpha[A,I,B]                  id[A] * lambda[B]
    // (A * I) * C -------------->  A * (I * C) --------------------> A * B
    val w1: M[A, M[I, B]] = alpha[A,I,B](mai)
    val w2: M[A, B] = mcBif.bimap[A,A, M[I,B],B](identity[A],lambda[B])(w1)

    v1 == w2
  }

  def pentagonEquations[A,B,C,D](fa: M[M[M[A,B],C],D]): Boolean = {
    //                    alpha[A,B,C] * 1D                      alpha[A,B*C,D]
    // ((A * B) * C) * D -------------------> (A * (B * C)) * D ------------------> A * ((B * C) * D)
    //                    1A * alpha[B,C,D]
    // A * ((B * C) * D) ------------------> A * (B * (C * D))
    val v1: M[M[A, M[B, C]], D] = mcBif.bimap(alpha[A,B,C],identity[D])(fa)
    val v2: M[A, M[M[B,C], D]] = alpha[A,M[B,C],D](v1)
    val v3: M[A, M[B, M[C,D]]] = mcBif.bimap(identity[A],alpha[B,C,D])(v2)

    //                     alpha[A*B,C,D]                       alpha[A,B,C*D]
    // ((A * B) * C) * D -----------------> (A * B) * (C * D) -----------------> A * (B * (C * D))
    val w1: M[M[A,B], M[C,D]] = alpha[M[A,B],C,D](fa)
    val w2: M[A,M[B,M[C,D]]] = alpha[A,B,M[C,D]](w1)

    v3 == w2
  }
}
```

# [Cartesian monoidal category](https://ncatlab.org/nlab/show/cartesian+monoidal+category)

A cartesian monoidal category is a monoidal category whose monoidal product is a cartesian product and unit is a terminal object.

Example  
category:  
  objects: Scala types  
  morphisms: pure functions A => B  
tensor product: Tuple2  
tensor unit: ()  

Implementation:

```scala
val tupleBifunctor: Bifunctor[Tuple2] = new Bifunctor[Tuple2] {
  override def bimap[A, B, C, D](f: A => B, g: C => D): Tuple2[A, C] => Tuple2[B, D] = { case (a,c) => (f(a), g(c)) }
}

val productMonoidalCategory: MonoidalCategory[Tuple2, Unit] = new MonoidalCategory[Tuple2, Unit] {
  val mcBif: Bifunctor[Tuple2] = tupleBifunctor
  val mcId: Unit = ()
  def rho[A](pair: (A, Unit)): A = pair._1
  def rho_inv[A](a: A): (A, Unit) = (a, mcId)
  def lambda[A](pair: (Unit, A)): A = pair._2
  def lambda_inv[A, B](a: A): (Unit, A) = (mcId, a)
  def alpha[A, B, C](pair: ((A, B), C)): (A, (B, C)) = pair match {case ((a,b),c) => (a, (b, c)) }
  def alpha_inv[A, B, C](pair: (A, (B, C))): ((A, B), C) = pair match {case (a,(b,c)) => ((a, b), c) }
}
```

# [Cocartesian monoidal category]((https://ncatlab.org/nlab/show/cocartesian+monoidal+category))

A `cocartesian monoidal category` is a monoidal category whose monoidal product is a coproduct and unit is an initial object.
It is dual to cartesian monoidal category.

Examples:

1) Scala types, pure functions, Either

category:  
  objects: Scala types  
  morphisms: pure functions A => B  
tensor product: Either  
tensor unit: Nothing  

```scala
type Void <: Nothing

val eitherBifunctor: Bifunctor[Either] = new Bifunctor[Either] {
  override def bimap[A, B, C, D](f: A => B, g: C => D): Either[A, C] => Either[B, D] = {
    case Left(a) => Left(f(a))
    case Right(c) => Right(g(c))
  }
}

val coproductMonoidalCategory: MonoidalCategory[Either, Void] = new MonoidalCategory[Either, Void] {
  val mcBif: Bifunctor[Either] = eitherBifunctor
  val mcId: Void = throw new RuntimeException("This exception was not thrown!")
  def rho[A](mai: Either[A, Void]): A = mai match { case Left(a) => a }
  def rho_inv[A](a: A): Either[A, Void] = Left(a)
  def lambda[A](mia: Either[Void, A]): A = mia match { case Right(a) => a }
  def lambda_inv[A, B](a: A): Either[Void, A] = Right(a)
  def alpha[A, B, C](mabc: Either[Either[A, B], C]): Either[A, Either[B, C]] =
    mabc match {
      case Left(Left(a)) => Left(a)
      case Left(Right(b)) => Right(Left(b))
      case Right(c) => Right(Right(c))
    }
  def alpha_inv[A, B, C](mabc: Either[A, Either[B, C]]): Either[Either[A, B], C] = mabc match {
    case Left(a) => Left(Left(a))
    case Right(Left(b)) => Left(Right(b))
    case Right(Right(c)) => Right(c)
  }
}
```

2) natural numbers, +, min

category:  
  objects: natural numbers (Nat)  
  morphisms: +  
tensor product: min  
tensor unit: 0  

3) natural numbers, *, min

category:
  objects: natural numbers (Nat)  
  morphisms: *  
tensor product: max  
tensor unit: 0  

Here monoidal category is defined on value level so we cannot use our definition of MonoidalCategory to express this.


3)
category:  
  objects: functors: List, Vector, Option, Eiter[Throwable, *], Function1[A, *], monix.Task, ZIO[A,E,*], ZIO[A,*,R]  
  morphisms: natural transformation =>  
    list.headOption, list.lastOption,  
    list.reverse,  
    option.toList  
tensor product: functor composition  
tensor unit: Identity functor  

3) TODO does it work ?  
category:  
  objects: ZIO[A,R,E]  
  morphisms: ZIO[A,R,E] => ZIO[B,R,E]  
tensor product: andThen  
tensor unit: TODO ZIO[A,A,Nothing](identity)  

4) TODO Monix and Parallel composition

5) you can construct monoidal category from category C that have products, sums, terminal, initial objects by:  
(C, sum, initial)  
(C, product, terminal)  

TODO do we get interesting monoidal category if we use one of common Kleisli category  
Id[*]  
Option[*]  
Either[*,E]  
Thunk[*]  
Task[*]  
ZIO[A,E,*]  
ZIO[A,*,R]  
Reader[*]  
Const[*]  
Cont[*]  
List[*]  
?

# Strict monoidal category

A monoidal category is strict if associator, left unitor and right unitors are all identity morphisms.

# Interchange law

Any morphisms f: A -> B, g: B -> C, h: D -> E, j: E -> F  
in a monoidal category, satisfy:  

`(g compose f) ⊗ (j compose h) = (g ⊗ j) compose (f ⊗ h)`  


# Properties of Monoidal categories

## [Coherence for Monoidal categories](https://ncatlab.org/nlab/show/coherence+theorem+for+monoidal+categories)

## [Closure for Monoidal categories](https://ncatlab.org/nlab/show/monoidal+category#closure)

## Additional structure generated by objects of monoidal category

Every small monoidal category C embeds as a full subcategory C -> D into a closed monoidal category,
where the embedding functor is a strong monoidal functor.

# Braided monoidal category

A `braided monoidal category` is a monoidal category C equipped with a natural isomorphism
`x ⊗ y ~ y ⊗ x` called `braiding`, such that following diagrams (called `hexagon identitties`) commute

TODO hexagon identities 

# [Symmetric monoidal category](https://ncatlab.org/nlab/show/symmetric+monoidal+category)

A symmetric monoidal category is a braided monoidal category for which the braiding  
`B(x,y): x ⊗ y -> y ⊗ x`   
satisfies condition  
`B(y,x) . B(x,y) = 1`  

# References
  * Some Definitions Everyone Should Know - John C. Baez [(pdf)](http://math.ucr.edu/home/baez/qg-fall2004/definitions.pdf)
  * Monoidal Categories, Higher Categories - Jamie Vicary [(pdf)](http://events.cs.bham.ac.uk/mgs2019/vicary.pdf)
  * Notions of Computation as Monoids - Exequiel Rivas, Mauro Jaskelioff [(arxiv.org/abs/1406.4823)](https://arxiv.org/pdf/1406.4823.pdf)
  * Discrimination is Wrong: Improving Productivity - Edward Kmett [(video from YOW! Conferences)](https://www.youtube.com/watch?v=eXDJ5Jcbgk8), [(video from ZuriHac 2015)](https://www.youtube.com/watch?v=cB8DapKQz-I), [(slides)](http://yowconference.com.au/slides/yowlambdajam2015/Kmett-DiscriminationIsWrong.pdf)
  * Braided Monoidal Categories - Andre Joyal, Ross Street [(pdf)](http://maths.mq.edu.au/~street/JS1.pdf)
  * A practical type theory for symmetric monoidal categories - Michael Shulman [(arxiv.org/abs/1911.00818)](https://arxiv.org/pdf/1911.00818.pdf)
  * Supplying bells and whistles in symmetric monoidal categories - Brendan Fong, David I Spivak [arxiv.org/abs/1908.02633](https://arxiv.org/pdf/1908.02633.pdf), [(video part 1)](https://www.youtube.com/watch?v=5p_tB_MXFrk), [(video part 2)](https://www.youtube.com/watch?v=ecfl4DCXFPQ)
  * A survey of graphical languages for monoidal categories - Peter Selinger [arxiv.org/abs/0908.3347](https://arxiv.org/pdf/0908.3347.pdf)
  * nLab:
    * [nLab monoidal category](https://ncatlab.org/nlab/show/monoidal+category)
    * [nLab Closure for Monoidal categories](https://ncatlab.org/nlab/show/monoidal+category#closure)
    * [nLab Coherence for Monoidal categories](https://ncatlab.org/nlab/show/coherence+theorem+for+monoidal+categories)
    * [nLab cartesian monoidal category](https://ncatlab.org/nlab/show/cartesian+monoidal+category)
    * [nLab cocartesian monoidal category](https://ncatlab.org/nlab/show/cocartesian+monoidal+category)
    * [nLab symmetric monoidal category](https://ncatlab.org/nlab/show/symmetric+monoidal+category)
