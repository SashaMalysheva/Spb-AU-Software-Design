package spbau.malysheva.roguelike.controller

import spbau.malysheva.roguelike.model.Direction
import java.nio.file.Paths

/**
 * command: move player up
 */
object MoveUp : Command {
    override val id = "u"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.TOP }
        world.update()
    }
}

/**
 * command: move player down
 */
object MoveDown : Command {
    override val id = "d"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.BOTTOM }
        world.update()
    }
}

/**
 * command: move player left
 */
object MoveLeft : Command {
    override val id = "l"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.LEFT }
        world.update()
    }
}

/**
 * command: move player right
 */
object MoveRight : Command {
    override val id = "r"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.RIGHT }
        world.update()
    }
}

/**
 * creates new game
 */
object NewGame : Command {
    override val id = ":new"

    override fun execute() {
        Environment.newGame()
    }
}

/**
 * loads game from '.world' file
 */
object LoadGame : Command {
    override val id = ":load"

    override fun execute() {
        val stream = Paths.get(".world").toFile().inputStream()
        Environment.loadGame(stream)
        stream.close()
    }
}

/**
 * saves current game to '.world' file
 */
object SaveGame : Command {
    override val id = ":save"

    override fun execute() {
        val stream = Paths.get(".world").toFile().outputStream()
        Environment.saveGame(stream)
        stream.close()
    }
}