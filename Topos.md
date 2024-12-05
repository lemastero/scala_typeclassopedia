## Topos

Topos is a category with some properties such that it behaves like sets.
So we can do mathematics in such category.

Definitions:
* `Elementatry topos` is more general, used in logic
* `Grothendieck topos (sheaf topos)` more specialized for application in geometry - it has natural number object. Adding natural number object to definition of elementary topos gives `W-topos`

Elementary topos is a category that:
* has finite limits
* is cartesian closed
* has subobject classifier

Resources:
  * vpatryshev/Categories [Topos](https://github.com/vpatryshev/Categories/blob/master/scala2/src/main/scala/math/cat/topos/Topos.scala) [Grothendieck Topos, Subobject Classifier](https://github.com/vpatryshev/Categories/blob/master/scala2/src/main/scala/math/cat/topos/GrothendieckTopos.scala), [Topology](https://github.com/vpatryshev/Categories/blob/master/scala2/src/main/scala/math/cat/topos/Topology.scala), [Lawvere-Tierney topology](https://github.com/vpatryshev/Categories/blob/master/scala2/src/main/scala/math/cat/topos/LawvereTopology.scala)
  * (Agda) agda/agda-categories [Topos](https://github.com/agda/agda-categories/blob/master/src/Categories/Category/Topos.agda) [Subobject Classifier](https://github.com/agda/agda-categories/blob/master/src/Categories/Diagram/SubobjectClassifier.agda)
  * (Coq) UniMath/UniMath [Grothendieck Topos](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/GrothendieckToposes/Toposes.v) [Subobject Classifier](https://github.com/UniMath/UniMath/blob/master/UniMath/CategoryTheory/SubobjectClassifier/SubobjectClassifier.v)
  * (Haskell) [brunjlar/protop](https://github.com/brunjlar/protop)
  * Category Theory for Computing Science - Michael Barr Charles Wells [(pdf)](http://www.math.mcgill.ca/triples/Barr-Wells-ctcs.pdf) Chapter 15 Toposes
  * 7Sketches Chapter 7 Logic of behavior: Sheaves, toposes, and internal languages [(arxiv)](https://arxiv.org/abs/1803.05316), [(video 1)](https://www.youtube.com/watch?v=Cf3tsAeGhBg), [(video 2)](https://www.youtube.com/watch?v=wF-khda2i4c)
  * (Category Theory, Haskell) Topoi - Bartosz Milewski [(blog post)](https://bartoszmilewski.com/2017/07/22/topoi/)
  * Category Theory: Toposes - MathProofsable [(video playlist)](https://www.youtube.com/watch?v=gKYpvyQPhZo&list=PL4FD0wu2mjWM3ZSxXBj4LRNsNKWZYaT7k)
  * Computational Quadrinitarianism (Curious Correspondences go Cubical) - Gershom Bazerman [(blog post)](http://comonad.com/reader/2018/computational-quadrinitarianism-curious-correspondences-go-cubical/)
  * [fdilke/bewl](https://github.com/fdilke/bewl) (A DSL for the internal language of a topos)
  * [resources about topos theory](./ComputationalTrinitarianism.md#topos-theory)
  * nLab [Topos](https://ncatlab.org/nlab/show/topos), [Subobject Classifier](https://ncatlab.org/nlab/show/subobject+classifier), [Lawvere-Tierney topology](https://ncatlab.org/nlab/show/Lawvere-Tierney+topology)