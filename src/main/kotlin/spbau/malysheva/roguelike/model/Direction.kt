package spbau.malysheva.roguelike.model

/**
 * Presents directions on the map
 */
enum class Direction(val dx : Int, val dy: Int) {
    TOP(0, -1), LEFT(-1, 0), RIGHT(1, 0), BOTTOM(0, 1), NOPE(0, 0);
}

/**
 * utility for 'iterating' over fields on the map
 */
operator fun Field.plus(direction: Direction) = world[x + direction.dx, y + direction.dy]