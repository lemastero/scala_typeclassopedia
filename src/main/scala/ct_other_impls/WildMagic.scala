package ct_other_impls

/*

       Morph[F,A,B]
  A ----------------> B
  |                   |
F |                   | F
  |                   |
  \/                  \/
  F[A] -------------> F[B]
      run(Morph[F,A,B])

def id[A]: Morph[A, A]
def compose[A, B, C]: Morph[A, B] => Morph[B, C] => Morph[A, C]

Signatures:

|-------------------|--------------------------------|--------------- |
| Morphism          | id                             | abstraction    |
|-------------------|--------------------------------|----------------|
| A    => B         | Morph[F[_], A, B] = A => B     | Functor        |
| B    => A         | Morph[F[_], A, B] = B => A     | Contravariant  |
| A    => F[B]      | Morph[F[_], A, B] = A => F[B]  | FlatMap        |
| F[A] => B         | Morph[F[_], A, B] = F[A] => B  | CoflatMap      |
| F[A  => B]        | Morph[F[_], A, B] = F[A => B]  | Apply          |
| A    => Option[B] | Morph[F[_], A, B] = F[A => B]  | Filter         |

Laws:

//        id[A]
// F[A] ========> F[A]

//        Morph[A,B] * Morph[C,C]
// F[A] ========================> F[C]

|-----------------|----------------|--------------|-------------------|
|  abstraction    | Morph          |   identity   | composition       |
|-----------------|----------------|--------------|-------------------|
| Functor         | A => B         | identity     | andThen           |
| FlatMap         | A => F[B]      | return       | kleisli comp.     |
| CoflatMap       | F[A] => B      | extract      | cokleisli comp.   |
| Apply           | F[A => B]      | F[identity]  | OK                |
| Contravariant   | B => A         | identity     | compose           |
| MapFilter       | A => Maybe[B]  | Just         | OK                |
| ContraMapFilter | Maybe[B] => A  | OK           | OK                |
|-----------------|----------------|--------------|-------------------|

 */

object FPMorphism {
  type Function[A, B]            = A => B
  type Kleisli[F[_],A,B]         = A => F[B]
  type Ap[F[_], A, B]            = F[A => B]
  type CoKleisli[F[_],A,B]       = F[A] => B
  type MapFilter[F[_],A,B]       = A => Option[B]
  type Op[A, B]                  = B => A
  type ContraMapFilter[F[_],A,B] = B => Option[A]
}

trait EndoFPAbs[F[_]] {
  type Morph[_, _] // specify some transformation on types A, B possibly using F and G e.g. A => F[B]
  def run[A, B](f: Morph[A, B]): F[A] => F[B]
}

trait EndoFPAbsLaw[F[_]] extends EndoFPAbs[F] {
  // define category structure on a Morphism
  def id[A]: Morph[A, A]
  def compose[A, B, C]: Morph[A, B] => Morph[B, C] => Morph[A, C]

  //        id[A]
  // F[A] ========> F[A]
  def abstractionIdentityLaw[A](fa: F[A]): Boolean = {
    val lhs: F[A] = run[A, A](id[A])(fa)
    lhs == fa
  }

  //        Mor[A,B]            Mor[B,C]
  // F[A] ============> F[B] ===========> F[C]
  //
  //        Mor[A,B] * Mor[C,C]
  // F[A] =======================> F[C]
  def abstractionCompositionLaw[A,B,C](fa: F[A], f: Morph[A,B], g: Morph[B,C]): Boolean = {
    val fb: F[B] = run[A, B](f)(fa)
    val lhs: F[C] = run[B, C](g)(fb)
    val rhs: F[C] = run[A, C](compose(f)(g))(fa)
    lhs == rhs
  }
}

////// Functor  //////////

trait Functor[F[_]] extends EndoFPAbs[F] {
  override def run[A, B](f: A => B): F[A] => F[B]
  type Morph[AA, BB] = FPMorphism.Function[AA,BB]
  def map[A, B](fa: F[A])(f: A => B): F[B] = run[A, B](f)(fa)
}

// fa.map(id) == fa
// fa.map(f).map(g) == fa.map(f andThen g)
trait FunctorLaws[F[_]] extends EndoFPAbsLaw[F] with Functor[F] {

   def abstractionIdentityLaw1[A](fa: F[A]): Boolean = {
     val lhs: F[A] = run[A,A](id[A])(fa)
     val rhs: F[A] = fa
     lhs == rhs
   }

  //        id
  // F[A] =======> F[A]
  def abstractionIdentityLaw2[A](fa: F[A]): Boolean = {
    val lhs: F[A] = map[A,A](fa)(identity[A])
    val rhs: F[A] = fa
    lhs == rhs
  }

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: A => B, g: B => C): Boolean = {
    val lhs1: F[C] = run[B,C](g)( run[A,B](f)(fa) )
    val rhs1: F[C] = run[A,C]( compose(f)(g) )(fa)
    lhs1 == rhs1
  }

  //        f          g                     f andThen g
  // F[A] ====> F[B] =====> F[C]  ==  F[A] ==============> F[B]
  def abstractionCompositionLaw2[A,B,C](fa: F[A], f: A => B, g: B => C): Boolean = {
    val fb: F[B] = map[A, B](fa)(f)
    val rhs1: F[C] = map[B,C](fb)(g)
    val lhs1: F[C] = map[A,C](fa)(f andThen g)
    lhs1 == rhs1
  }

  def id[A]: A => A = identity[A]
  def compose[A, B, C]: (A => B) => (B => C) => (A => C) = f => f andThen _
}

////// FlatMap  //////////

trait FlatMap[F[_]] extends EndoFPAbs[F] {
  type Morph[AA, BB] = FPMorphism.Kleisli[F,AA,BB]
  def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = run[A, B](f)(fa)
}

trait Pure[F[_]] {
  def pure[A](value: A): F[A]
}

// needs Pure/Return so it is Monad

// flatMap(pure) == id
// fa.flatMap(f).flatMap(g) == fa.flatMap(a => f(a).flatMap(g) )
abstract class FlatMapLaws[F[_]: Pure] extends EndoFPAbsLaw[F] with FlatMap[F] {
  def pure[A](a:A): F[A] = implicitly[Pure[F]].pure(a)

  // FlatMap and Pointed are connected, give raise to Monad
def id[A]: A => F[A] = pure[A]

def compose[A,B,C]: (A => F[B]) => (B => F[C]) => (A => F[C]) =
  f => g => a => run[B,C](g)(f(a))

  def abstractionIdentityLaw1[A](fa: F[A]): Boolean = {
    val lhs: F[A] = run[A, A](id[A])(fa)
    lhs == fa
  }

  def abstractionIdentityLaw2[A](fa: F[A]): Boolean = {
    val pa: A => F[A] = pure[A]
    val lhs: F[A] = flatMap[A, A](fa)(pa)
    lhs == fa
  }

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: A => F[B], g: B => F[C]): Boolean = {
    val fb: F[B] = run[A, B](f)(fa)
    val lhs: F[C] = run[B, C](g)(fb)

    val fg: A => F[C] = compose(f)(g)
    val rhs: F[C] = run[A, C](fg)(fa)
    lhs == rhs
  }

  //        flatMap(f)          flatMap(g)
  // F[A] -------------> F[B] -------------> F[C]

  //        flatMap(a => flatMap(f(a))(g))
  // F[A] ----------------------------------> F[C]
  def abstractionCompositionLaw2[A,B,C](fa: F[A], f: A => F[B], g: B => F[C]): Boolean = {
    val fb: F[B] = flatMap[A, B](fa)(f)
    val lhs: F[C] = flatMap[B, C](fb)(g)

    def fg: A => F[C] = a => flatMap[B,C](f(a))(g)
    val rhs: F[C] = flatMap[A, C](fa)(fg)
    lhs == rhs
  }
}

trait Apply[F[_]] extends EndoFPAbs[F] {
  type Morph[AA, BB] = FPMorphism.Ap[F,AA,BB]
  def ap[A, B](fa: F[A])(f: F[A => B]): F[B] = run[A, B](f)(fa)
}

// fa.pure(identity) == fa
// fa.ap(f).ap(g) == fa.ap( compose(f,g) ) // compose uses pure
abstract class ApplyLaws[F[_] : Pure] extends EndoFPAbsLaw[F] with Apply[F] {
  def pure[A]: A => F[A] = implicitly[Pure[F]].pure

  def id[A]: F[A => A] = pure(identity[A])
  def compose[A, B, C]: F[A => B] => F[B => C] => F[A => C] = fab => {
    val fabbcac: F[(A => B) => ((B => C) => (A => C))] = pure(ab => bc => ab andThen bc)
    val fbcac: F[(B => C) => (A => C)] = run[A => B, (B => C) => (A => C)](fabbcac)(fab)
    run[B => C,A => C](fbcac)
  }

  def abstractionIdentityLaw1[A](fa: F[A]): Boolean = {
    val lhs: F[A] = run[A, A](id[A])(fa)
    lhs == fa
  }

  def abstractionIdentityLaw2[A](fa: F[A]): Boolean = {
    val lhs: F[A] = ap[A, A](fa)(pure(identity[A]))
    lhs == fa
  }

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: Morph[A,B], g: Morph[B,C]): Boolean = {
    val lhs: F[C] = run[B, C](g)(run[A, B](f)(fa))
    val rhs: F[C] = run[A, C](compose(f)(g))(fa)
    lhs == rhs
  }

  def abstractionCompositionLaw2[A,B,C](fa: F[A], fab: F[A => B], fbc: F[B => C]): Boolean = {
    val fb: F[B] = ap[A, B](fa)(fab)
    val lhs: F[C] = ap[B, C](fb)(fbc)
    val fac: F[A => C] = {
      val ff1: F[(A => B) => ((B => C) => (A => C))] = pure(ab => bc => ab andThen bc)
      val ff2: F[(B => C) => (A => C)] = run[A => B, (B => C) => (A => C)](ff1)(fab)
      ap[B => C,A => C](fbc)(ff2)
    }
    val rhs: F[C] = ap[A, C](fa)(fac)
    lhs == rhs
  }
}

trait CoFlatMap[F[_]] extends EndoFPAbs[F] {
  type Morph[AA, BB] = FPMorphism.CoKleisli[F,AA,BB]
  def extend[A, B](fa: F[A])(f: F[A] => B): F[B] = run[A, B](f)(fa)
  def duplicate[A](fa: F[A]): F[F[A]] = extend(fa)(identity)
}

trait Extract[F[_]] {
  def extract[A](w: F[A]): A
}

// fa.extend(extract) == fa
// fa.extend(f).extend(g) == ffa.extend( compose(f,g) )
abstract class CoFlatMapLaws[F[_]:Extract] extends EndoFPAbsLaw[F] with CoFlatMap[F] {
  def extract[A]: F[A] => A = implicitly[Extract[F]].extract _

  def id[A]: F[A] => A = extract
  def compose[A, B, C]: (F[A] => B) => (F[B] => C) => F[A] => C = fab => fbc => fa =>
    extract(run(fbc)(run(fab)(fa)))

  def abstractionIdentityLaw1[A](fa: F[A]): Boolean =
    run[A,A](id[A])(fa) == fa

  // fa.extend(extract) == fa
  def abstractionIdentityLaw2[A](fa: F[A]): Boolean = {
    val lhs: F[A] = extend[A, A](fa)(extract[A])
    lhs == fa
  }

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: F[A] => B, g: F[B] => C): Boolean =
    run[B,C](g)( run[A,B](f)(fa) ) == run[A,C]( compose(f)(g) )(fa)

  // fa.extend(f).extend(g) == ffa.extend( compose(f,g) )
  def abstractionCompositionLaw2[A,B,C](fa: F[A], f: F[A] => B, g: F[B] => C): Boolean = {
    val fb: F[B] = extend[A, B](fa)(f)
    val lhs: F[C] = extend[B, C](fb)(g)
    val fg: F[A] => C = fa => {
      val ff1: F[B] = extend(fa)(f)
      val ff2: F[C] = extend(ff1)(g)
      extract(ff2)
    }
    val rhs: F[C] = extend[A, C](fa)(fg)
    lhs == rhs
  }
}

trait Contravariant[F[_]] extends EndoFPAbs[F] {
  type Morph[AA, BB] = FPMorphism.Op[AA,BB]

  def contramap[A, B](fa: F[A])(f: B => A): F[B] = run[A, B](f)(fa)
}

// fa.contramap(identity) == fa
// fa.contramap(f).contramap(g) == fa.contramap(f compose g)
trait ContravariantLaws[F[_]] extends EndoFPAbsLaw[F] with Contravariant[F] {
  def id[A]: A => A = identity[A]
  def compose[A, B, C]: (B => A) => (C => B) => (C => A) = f => g => f compose g

  def abstractionIdentityLaw1[A](fa: F[A]): Boolean =
    run[A,A](id[A])(fa) == fa

  // fa.contramap(identity) == fa
  def abstractionIdentityLaw2[A](fa: F[A]): Boolean =
    contramap[A,A](fa)(identity[A]) == fa

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: Morph[A,B], g: Morph[B,C]): Boolean =
    run[B,C](g)( run[A,B](f)(fa) ) == run[A,C]( compose(f)(g) )(fa)

  // fa.contramap(f).contramap(g) == fa.contramap(f compose g)
  def abstractionCompositionLaw2[A,B,C](fa: F[A], f: B => A, g: C => B): Boolean = {
    val fb: F[B] = contramap[A, B](fa)(f)
    val lhs: F[C] = contramap[B, C](fb)(g)
    val fg: C => A = f compose g
    val rhs: F[C] = contramap[A, C](fa)(fg)
    lhs == rhs
  }
}

trait MapFilter[F[_]] extends EndoFPAbs[F] {
  override def run[A, B](f: A => Option[B]): F[A] => F[B]
  type Morph[AA, BB] = FPMorphism.MapFilter[F,AA,BB]
  def mapFilter[A, B](fa: F[A])(f: A => Option[B]): F[B] = run[A, B](f)(fa)
}

trait MapFilterLaws[F[_]] extends EndoFPAbsLaw[F] with MapFilter[F] {

  def abstractionIdentityLaw1[A](fa: F[A]): Boolean = {
    val lhs = run[A,A](id[A])(fa)
    val rhs: F[A] = fa
    lhs == rhs
  }

  //        id
  // F[A] =======> F[A]
  def abstractionIdentityLaw2[A](fa: F[A]): Boolean = {
    val lhs: F[A] = mapFilter[A,A](fa)(Some(_))
    val rhs: F[A] = fa
    lhs == rhs
  }

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: A => Option[B], g: B => Option[C]): Boolean = {
    val lhs1: F[C] = run[B,C](g)( run[A,B](f)(fa) )
    val rhs1: F[C] = run[A,C]( compose(f)(g) )(fa)
    lhs1 == rhs1
  }

  //        f          g                     compose(f,g)
  // F[A] ====> F[B] =====> F[C]  ==  F[A] ==============> F[B]
  def abstractionCompositionLaw2[A,B,C](fa: F[A], f: A => Option[B], g: B => Option[C]): Boolean = {
    val fb: F[B] = mapFilter[A, B](fa)(f)
    val rhs1: F[C] = mapFilter[B,C](fb)(g)
    val lhs1: F[C] = mapFilter[A,C](fa)(compose(f)(g))
    lhs1 == rhs1
  }

  def id[A]: A => Option[A] = Some(_)
  def compose[A, B, C]: (A => Option[B]) => (B => Option[C]) => (A => Option[C]) = f => g => a =>
    for {
      b <- f(a)
      c <- g(b)
    } yield c
}

trait ContraMapFilter[F[_]] extends EndoFPAbs[F] {
  override def run[A, B](f: B => Option[A]): F[A] => F[B]
  type Morph[AA, BB] = FPMorphism.ContraMapFilter[F,AA,BB]
  def contramapFilter[A, B](fa: F[A])(f: B => Option[A]): F[B] = run[A, B](f)(fa)
}


trait ContraMapFilterLaws[F[_]] extends EndoFPAbsLaw[F] with ContraMapFilter[F] {

  def abstractionIdentityLaw1[A](fa: F[A]): Boolean = {
    val lhs = run[A,A](id[A])(fa)
    val rhs: F[A] = fa
    lhs == rhs
  }

  //        id
  // F[A] =======> F[A]
  def abstractionIdentityLaw2[A](fa: F[A]): Boolean = {
    val lhs: F[A] = contramapFilter[A,A](fa)(Some(_))
    val rhs: F[A] = fa
    lhs == rhs
  }

  def abstractionCompositionLaw1[A,B,C](fa: F[A], f: B => Option[A], g: C => Option[B]): Boolean = {
    val lhs1: F[C] = run[B,C](g)( run[A,B](f)(fa) )
    val rhs1: F[C] = run[A,C]( compose(f)(g) )(fa)
    lhs1 == rhs1
  }

  //        f          g                     compose(f,g)
  // F[A] ====> F[B] =====> F[C]  ==  F[A] ==============> F[B]
  def abstractionCompositionLaw2[A,B,C](fa: F[A], f: B => Option[A], g: C => Option[B]): Boolean = {
    val fb: F[B] = contramapFilter[A, B](fa)(f)
    val rhs1: F[C] = contramapFilter[B,C](fb)(g)
    val lhs1: F[C] = contramapFilter[A,C](fa)(compose(f)(g))
    lhs1 == rhs1
  }

  def id[A]: A => Option[A] = Some(_)
  def compose[A, B, C]: (B => Option[A]) => (C => Option[B]) => (C => Option[A]) = f => g => c =>
    g(c).flatMap(f)
}


// OUTLAWS !!!

trait Alt[F[_]] extends EndoFPAbs[F] {
  type Alt[F[_], A, B] = F[Either[A, B]]
  type Morph[AA, BB] = Alt[F,AA,BB]
  def either2[A, B](fa: F[A])(fab: F[Either[A, B]]): F[B] = run[A, B](fab)(fa)
}

trait Divide[F[_]] extends EndoFPAbs[F] {
  type OpAp[F[_], A, B] = F[B => A]
  type Morph[AA, BB] = OpAp[F,AA,BB]
  def contraAp[A, B](fa: F[A])(fba: F[B => A]): F[B] = run[A, B](fba)(fa)
}
