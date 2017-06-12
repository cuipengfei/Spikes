package streams


trait InfiniteTerrain extends GameDef {
  val terrain: Terrain = (pos: Pos) => true
}
