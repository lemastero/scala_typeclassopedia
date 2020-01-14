package typetheory

class SkiCombinators {

  def S[X,Y,Z]: (Z => Y => X) => (Z => Y) => Z => X = a => b => c => {
    val bc: Y = b(c)
    val ac: Y => X = a(c)
    ac(bc)
  }

  def K[X,Y,Z]: X => Y => X = a => _ => a

  def I[X]: X => X = identity[X]
}
