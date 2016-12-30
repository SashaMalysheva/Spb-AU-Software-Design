package spbau.malysheva.roguelike.tests

import org.junit.Before
import org.junit.Test
import spbau.malysheva.roguelike.model.*
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull


abstract class InteractionTests {

    lateinit var world: World
    lateinit var player: Player

    fun Field.coords() = x to y

    @Before
    open fun init() {
        world = initWorld()
        player = world.player
    }

    fun test(expected: Pair<Int, Int>, direction: Direction) {
        player.moveStrategy = { direction }
        world.update()

        val playerField = assertNotNull(player.field)
        val actual = playerField.coords()

        assertEquals(expected, actual)
    }

    abstract fun initWorld(): World
}

class SpaceInteractionTests : InteractionTests() {

    ///////
    //   //
    // p //
    //   //
    ///////
    override fun initWorld() = World(3, 3) {
        when (it) {
            4 -> PlayerFactory.create()
            else -> SpaceFactory.create()
        }
    }

    @Test
    fun testPosition() {
        val playerField = assertNotNull(player.field)
        assertEquals(playerField.coords(), 1 to 1)
    }

    @Test
    fun testNope() {
        test(1 to 1, Direction.NOPE)
    }

    @Test
    fun testLeft() {
        test(0 to 1, Direction.LEFT)
    }

    @Test
    fun testRight() {
        test(2 to 1, Direction.RIGHT)
    }

    @Test
    fun testTop() {
        test(1 to 0, Direction.TOP)
    }

    @Test
    fun testBottom() {
        test(1 to 2, Direction.BOTTOM)
    }
}

class BlockInteractionTests : InteractionTests() {

    ///////
    //   //
    // p#//
    //   //
    ///////
    override fun initWorld() = World(3, 3) {
        when (it) {
            4 -> PlayerFactory.create()
            5 -> BlockFactory.create()
            else -> SpaceFactory.create()
        }
    }

    @Test
    fun testBlockInteraction() {
        test(1 to 1, Direction.RIGHT)
    }
}

class CreatureInteractionTests : InteractionTests() {

    ///////
    //   //
    // pm//
    //   //
    ///////
    override fun initWorld() = World(3, 3) {
        when (it) {
            4 -> PlayerFactory.create()
            5 -> CreatureFactory.create()
            else -> SpaceFactory.create()
        }
    }

    @Test
    fun testCreatureInteraction() {
        test(1 to 1, Direction.RIGHT)
    }
}