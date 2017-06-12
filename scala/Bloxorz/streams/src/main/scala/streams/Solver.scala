package streams


trait Solver extends GameDef {

  def done(b: Block): Boolean = b.isStandingAt(goal)

  def neighborsWithHistory(b: Block, history: List[Move]): Stream[(Block, List[Move])] =
    b.legalNeighbors.toStream.map { case (block, move) => (block, move :: history) }

  def newNeighborsOnly(neighbors: Stream[(Block, List[Move])],
                       explored: Set[Block]): Stream[(Block, List[Move])] =
    neighbors.filter { case (b, m) => !explored.contains(b) }

  def from(initial: Stream[(Block, List[Move])],
           explored: Set[Block]): Stream[(Block, List[Move])] =
    initial match {
      case (block, moves) #:: tail =>
        val newExplored = explored + block
        val newNeighbors = newNeighborsOnly(neighborsWithHistory(block, moves), newExplored)
        (block, moves) #:: from(initial.tail ++ newNeighbors, newExplored) //广度优先

      case _ => Stream.empty
    }

  lazy val pathsFromStart: Stream[(Block, List[Move])] =
    from(Stream((startBlock, Nil)), Set.empty)

  lazy val pathsToGoal: Stream[(Block, List[Move])] =
    pathsFromStart.filter { case (b, _) => done(b) }

  lazy val solution: List[Move] = pathsToGoal.headOption match {
    case Some((b, ms)) => ms.reverse
    case _ => Nil
  }
}
