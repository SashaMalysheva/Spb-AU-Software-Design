package spbau.malysheva.roguelike.model

import java.util.*

/**
 * Utility interface for [World] map generation
 */
interface WorldGenerator {

    /**
     * @return a newly generated entity for ([x], [y]) field on the map
     */
    fun generate(x: Int, y: Int): Entity
}

/**
 * A simple [WorldGenerator] implementation, working on java [Random] facility
 *
 * @param spaceProportion proportion of [Space] entities on the map
 * @param blockProportion proportion of [Block] entities on the map
 * @param creatureProportion proportion of [Creature] entities on the map
 *
 * @param mobFactory [Creature] supplier that's ruled by pc
 * @param playerFactory [Player] supplier
 *
 * @param worldHeight world height - needs to create border around the world
 * @param worldWidth world width - same
 */
class SimpleProportionGenerator(
        val random: Random = Random(),
        spaceProportion: Int = 1,
        blockProportion: Int = 1,
        creatureProportion: Int = 1,
        val worldWidth: Int,
        val worldHeight: Int,
        val mobFactory: EntityFactory = CreatureFactory,
        val playerFactory: EntityFactory = PlayerFactory
) : WorldGenerator {

    val isSpace = spaceProportion
    val isBlock = blockProportion + isSpace
    val isCreature = creatureProportion + isBlock

    val proportions = spaceProportion + blockProportion + creatureProportion

    val playerWidth = random.nextInt(worldWidth - 2) + 1
    val playerHeight = random.nextInt(worldHeight - 2) + 1

    override fun generate(x: Int, y: Int): Entity {
        if (x == 0 || x == worldWidth - 1 || y == 0 || y == worldHeight - 1) {
            return Block()
        }
        if (x == playerWidth && y == playerHeight) {
            return playerFactory.create()
        }
        if (x >= playerWidth - 1 && x <= playerWidth + 1 && y >= playerHeight - 1 && y <= playerHeight + 1) {
            return Space()
        }
        val randomness = random.nextInt(proportions)
        if (randomness < isSpace) {
            return Space()
        }
        if (randomness < isBlock) {
            return Block()
        }
        if (randomness < isCreature) {
            return mobFactory.create()
        }
        throw IllegalStateException("bad random function")
    }
}