package streams


trait GameDef {

  case class Pos(row: Int, col: Int) {

    val isPositive: Boolean = row >= 0 && col >= 0

    def deltaRow(d: Int): Pos = copy(row = row + d)

    def deltaCol(d: Int): Pos = copy(col = col + d)
  }

  val startPos: Pos
  val goal: Pos


  type Terrain = Pos => Boolean
  val terrain: Terrain


  sealed abstract class Move

  case object Left extends Move

  case object Right extends Move

  case object Up extends Move

  case object Down extends Move


  def startBlock: Block = Block(startPos, startPos)


  case class Block(b1: Pos, b2: Pos) {

    require(b1.row <= b2.row && b1.col <= b2.col, "Invalid block position: b1=" + b1 + ", b2=" + b2)

    def deltaRow(d1: Int, d2: Int) = Block(b1.deltaRow(d1), b2.deltaRow(d2))

    def deltaCol(d1: Int, d2: Int) = Block(b1.deltaCol(d1), b2.deltaCol(d2))

    def left = if (isStanding) deltaCol(-2, -1)
    else if (b1.row == b2.row) deltaCol(-1, -2)
    else deltaCol(-1, -1)

    def right = if (isStanding) deltaCol(1, 2)
    else if (b1.row == b2.row) deltaCol(2, 1)
    else deltaCol(1, 1)

    def up = if (isStanding) deltaRow(-2, -1)
    else if (b1.row == b2.row) deltaRow(-1, -1)
    else deltaRow(-1, -2)

    def down = if (isStanding) deltaRow(1, 2)
    else if (b1.row == b2.row) deltaRow(1, 1)
    else deltaRow(2, 1)

    def neighbors: List[(Block, Move)] =
      List((up, Up), (down, Down), (left, Left), (right, Right))

    def legalNeighbors: List[(Block, Move)] = neighbors.filter {
      case (block, _) => block.isLegal
    }

    def isStanding: Boolean = b1 == b2

    def isLegal: Boolean = terrain(b1) && terrain(b2)

    def isStandingAt(pos: Pos): Boolean =
      b1 == pos && b2 == pos
  }

}
