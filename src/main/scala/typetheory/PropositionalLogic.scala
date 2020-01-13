package typetheory

class PropositionalLogic {
  object PropositionLogic {
    type /\[P,Q] = (P,Q)
    type \/[P,Q]  = Either[P,Q]
    // => implication is function
    type <=>[P,Q] = (P => Q, P => Q) // Definition of biconditional
  }

  object Tautology {
    /** P ^ (Q V R) => (P ^ Q) ∨ (P ^ R) */
    def andDistributeOr[P,Q,R](b: (P, Either[Q,R])): Either[(P,Q), (P,R)] = b._2 match {
      case Right(r) => Right((b._1,r))
      case Left(q) => Left((b._1,q))
    }

    /** (P ^ Q) ∨ (P ^ R) => P ^ (Q V R)  */
    def andDistributeOrInv[P,Q,R](b: Either[(P,Q), (P,R)]): (P, Either[Q,R]) = b match {
      case Right(pr) => (pr._1, Right(pr._2))
      case Left(pr) => (pr._1, Left(pr._2))
    }

    /** ((P => Q) ^ (Q => R)) => P => R is function composition */
    def `Law of syllogism`[P,Q,R](f: P => Q, g: Q => R): P => R =
      f andThen g
  }
}
