package spbau.malysheva.roguelike.controller

import spbau.malysheva.roguelike.model.Direction
import java.nio.file.Paths


object MoveUp : Command {
    override val id = "u"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.TOP }
        world.update()
    }
}

object MoveDown : Command {
    override val id = "d"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.BOTTOM }
        world.update()
    }
}

object MoveLeft : Command {
    override val id = "l"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.LEFT }
        world.update()
    }
}

object MoveRight : Command {
    override val id = "r"

    override fun execute() {
        val world = Environment.world ?: return
        world.player.moveStrategy = { Direction.RIGHT }
        world.update()
    }
}

object NewGame : Command {
    override val id = ":new"

    override fun execute() {
        Environment.newGame()
    }
}

object LoadGame : Command {
    override val id = ":load"

    override fun execute() {
        val stream = Paths.get(".world").toFile().inputStream()
        Environment.loadGame(stream)
        stream.close()
    }
}

object SaveGame : Command {
    override val id = ":save"

    override fun execute() {
        val stream = Paths.get(".world").toFile().outputStream()
        Environment.saveGame(stream)
        stream.close()
    }
}