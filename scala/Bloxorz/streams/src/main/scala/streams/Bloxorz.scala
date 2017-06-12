package streams


object Bloxorz extends App {

  object InfiniteLevel extends Solver with InfiniteTerrain {
    val startPos = Pos(1, 3)
    val goal = Pos(7, 8)
  }

  println(InfiniteLevel.solution)


  abstract class Level extends Solver with StringParserTerrain

  object Level0 extends Level {
    val level =
      """------
        |--ST--
        |--oo--
        |--oo--
        |------""".stripMargin
  }

  println(Level0.solution)


  object Level1 extends Level {
    val level =
      """ooo-------
        |oSoooo----
        |ooooooooo-
        |-ooooooooo
        |-----ooToo
        |------ooo-""".stripMargin
  }

  println(Level1.solution)
}
