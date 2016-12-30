package spbau.malysheva.roguelike.controller

import spbau.malysheva.roguelike.model.*
import spbau.malysheva.roguelike.view.WorldView
import java.io.InputStream
import java.io.OutputStream
import java.util.*

const val HEIGHT = 15
const val WIDTH = 15

object Environment {

    val commandMap = arrayOf(
            MoveUp,
            MoveDown,
            MoveLeft,
            MoveRight,
            NewGame,
            LoadGame,
            SaveGame
    ).map { Pair(it.id, it) }.toMap()

    val CONTROLLER_RANDOM = Random()

    var world: World? = null
    var view: WorldView? = null

    fun loadGame(stream: InputStream) {
        init(load(stream, MY_FACTORIES))
    }

    fun saveGame(stream: OutputStream) {
        val currentWorld = world
        currentWorld ?: throw IllegalStateException("no world to save")
        currentWorld.save(stream, MY_SERIALIZATION_PROVIDER)
    }

    fun newGame() {
        init(World(HEIGHT, WIDTH, SimpleProportionGenerator(
                spaceProportion = 10,
                worldHeight = HEIGHT,
                worldWidth = WIDTH,
                mobFactory = MY_MOB_FACTORY,
                playerFactory = MY_PLAYER_FACTORY
        )))
    }

    fun init(newWorld: World) {
        world = newWorld
        view = WorldView(newWorld, System.out.bufferedWriter())
    }

    fun execute(commandPresentation: String) {
        val command = commandMap[commandPresentation]
        if (command == null) {
            view?.showError("Unexpected command '$commandPresentation'")
        } else {
            command.execute()
            view?.redraw()
        }
    }

    private val MY_PLAYER_FACTORY = PlayerFactory.withParams(12, 4, 4)

    private val MY_MOB_FACTORY = CreatureFactory.withStrategy {
        when (CONTROLLER_RANDOM.nextInt(5)) {
            0 -> Direction.NOPE
            1 -> Direction.LEFT
            2 -> Direction.TOP
            3 -> Direction.RIGHT
            4 -> Direction.BOTTOM
            else -> throw IllegalStateException("bad random function")
        }
    }

    private val MY_FACTORIES = mapOf(
            'p'.to(MY_PLAYER_FACTORY),
            'm'.to(MY_MOB_FACTORY),
            ' '.to(SpaceFactory),
            '#'.to(BlockFactory)
    )

    private val MY_SERIALIZATION_PROVIDER = object : EntityVisitor<Char>() {
        override fun visitPlayer(player: Player) = 'p'

        override fun visitCreature(creature: Creature) = 'm'

        override fun visitSpace(space: Space) = ' '

        override fun visitBlock(block: Block) = '#'

        override fun visitDefault(entity: Entity) = throw UnsupportedOperationException("unexpected entity provided")
    }
}