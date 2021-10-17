# Bird combinators

| bird            | syntax                                             | combinator     | interpretation       |
|-----------------|----------------------------------------------------|----------------|----------------------|
| idiot           | a -> a                                             | I combinator   | id                   |
| kestrel         | a -> b -> a                                        | K combinator   | const                |
| bluebird        | (b -> c) -> (a -> b) -> a -> c                     | B combinator   | compose              |
| cardinal        | (a -> b -> c) -> b -> a -> c                       | C combinator   | flip                 |
| applicator      | (a -> b) -> a -> b                                 | A combinator   | function application |
| psi             | (b -> b -> c) -> (a -> b) -> a -> a -> c           | Psi combinator | [on](http://hackage.haskell.org/package/base/docs/Data-Function.html#v:on) |
| becard          | (c -> d) -> (b -> c) -> (a -> b) -> a -> d         | B3 combinator  | |
| blackbird       | (c -> d) -> (a -> b -> c) -> a -> b -> d           | B1 combinator  | |
| bluebird prime  | (a -> c -> d) -> a -> (b -> c) -> b -> d           | B' combinator  | |
| bunting         | (d -> e) -> (a -> b -> c -> d) -> a -> b -> c -> e | B2 combinator  | |

* Implementations Haskell [data-aviary](http://hackage.haskell.org/package/data-aviary/docs/Data-Aviary-Birds.html), Purescript [Aviary.Birds](https://pursuit.purescript.org/packages/purescript-birds/docs/Aviary.Birds), JavaScript [fantasy-birds](https://github.com/fantasyland/fantasy-birds), Scala [folone/type-level-birds](https://github.com/folone/type-level-birds)

* Resources
  * [Combinator Birds - Chris Rathman](http://www.angelfire.com/tx4/cus/combinator/birds.html) Reference of Birds expressed as Combinatory Logic (SKI calculus)
  * [functions from Functor, Applicative, Monad, Comond as combinators](http://hackage.haskell.org/package/data-aviary/docs/Data-Aviary-Functional.html)
  * [Haskell Wiki Combinatory logic](https://wiki.haskell.org/Combinatory_logic)
  * [Combinatory Logic Tutorial - Chris Barker](http://www.nyu.edu/projects/barker/Lambda/ski.html)
  * The Thrush combinator in Scala - Debasish Ghosh [(blog post)](http://debasishg.blogspot.com/2009/09/thrush-combinator-in-scala.html)
  * (Haskell) Combinators in Haskell - geophf [(blog post)](http://logicaltypes.blogspot.com/2008/08/combinators-in-haskell.html)
  * (Haskell) Combinatory Birds as Types - geophf [(blog post)](http://logicaltypes.blogspot.com/2008/08/combinatory-birds-as-types.html)