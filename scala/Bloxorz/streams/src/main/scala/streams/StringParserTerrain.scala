package streams

trait StringParserTerrain extends GameDef {

  val level: String

  def terrainFunction(levelVector: Vector[Vector[Char]]): Pos => Boolean =
    pos =>
      pos.isPositive &&
        levelVector.length > pos.row &&
        levelVector(pos.row).length > pos.col &&
        levelVector(pos.row)(pos.col) != '-'

  def findChar(c: Char, levelVector: Vector[Vector[Char]]): Pos = {
    val poses =
      for (row <- levelVector; cell <- row if cell == c)
        yield Pos(levelVector.indexOf(row), row.indexOf(cell))

    poses.head
  }

  private lazy val vector: Vector[Vector[Char]] =
    Vector(level.split("\n").map(str => Vector(str: _*)): _*)

  lazy val terrain: Terrain = terrainFunction(vector)
  lazy val startPos: Pos = findChar('S', vector)
  lazy val goal: Pos = findChar('T', vector)

}
