package ct_other_impls

// TODO fit into this pattern situations when there are 3 different effects under F: Selective, etc
trait FPAbs3[F[_]] {
  type Morphism1[_, _, _]
  type Morphism2[_, _, _]
  type Morphism3[_, _, _]

  def run[A, B, C](f: Morphism1[A, B, C]): Morphism2[A, B, C] => Morphism3[A, B, C]
}

// TODO what about Selective
