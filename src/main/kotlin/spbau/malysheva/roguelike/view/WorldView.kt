package spbau.malysheva.roguelike.view

import spbau.malysheva.roguelike.model.*
import java.io.Writer

class WorldView(val world : World, val uiProvider: Writer) {

    fun redraw() {
        uiProvider.appendln("Heath: ${world.player.health}, \tTime: ${world.time}")
        for (y in 0 until world.width) {
            for (x in 0 until world.height) {
                val entity = world[x, y].entity ?: throw IllegalStateException("bad map")
                val presentation = entity.accept(EntityPresenationProvider)
                uiProvider.append(presentation)
            }
            uiProvider.appendln()
        }
        uiProvider.flush()
    }

    fun showError(message: String) {
        uiProvider.write("Error occurred: $message")
        uiProvider.flush()
    }
}
