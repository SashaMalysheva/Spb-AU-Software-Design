package spbau.malysheva.roguelike.model

import java.io.*

/**
 * Game world model
 *
 * @author Sasha Malysheva
 */
class World constructor(
        val height: Int,
        val width: Int,
        mapInitializer: (Int) -> Entity) {


    constructor(height: Int, width: Int, worldGenerator: WorldGenerator)
            : this(height, width, { worldGenerator.generate(it % width, it / width) }) {
    }

    internal val map = Array(height * width) {
        val x = getXOn(it)
        val y = getYOn(it)
        val entity = mapInitializer(it)
        val field = Field(x, y, this)
        entity.attach(field)
        field
    }

    val player = findPlayer()

    var time = 0
        private set

    operator fun get(x: Int, y: Int) = map[getPositionOn(x, y)]

    fun update() {
        map.map { it.entity }.filterNotNull().forEach {
            val field = it.field ?: return@forEach
            val direction = it.getNextMoveDirection()
            if (direction == Direction.NOPE) {
                return@forEach
            }
            val nextField = field + direction
            it.interact(nextField.entity ?: return)
        }
        time++
    }
}


internal fun World.findPlayer() : Player {
    var player : Player? = null
    for (field in map) {
        val entity = field.entity
        if (entity is Player) {
            if (player == null) {
                player = entity
            } else {
                throw IllegalStateException("two players in world")
            }
        }
    }
    return player ?: throw IllegalStateException("no players in world")
}

internal fun World.getXOn(position: Int) = position % width
internal fun World.getYOn(position: Int) = position / width
internal fun World.getPositionOn(x: Int, y: Int) = x + y * width

fun load(stream: InputStream, factories: Map<Char, EntityFactory>): World {
    val lines = stream.bufferedReader().readLines()
    val width = lines[0].toInt()
    val height = lines[1].toInt()

    val entities = lines[2].map { factories[it]?.create() ?: throw IllegalAccessException("bad char in stream") }

    return World(width, height, { entities[it] })
}

fun World.save(stream: OutputStream, serializationProvider: EntityVisitor<Char>) {
    val writer = stream.bufferedWriter()

    writer.appendln(width.toString())
    writer.appendln(height.toString())

    for (field in map) {
        val entity = field.entity ?: throw IllegalStateException("bad map")
        val charToSerialize = entity.accept(serializationProvider)
        writer.append(charToSerialize)
    }
    writer.flush()
}