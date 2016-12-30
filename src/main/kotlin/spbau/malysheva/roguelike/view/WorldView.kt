package spbau.malysheva.roguelike.view

import spbau.malysheva.roguelike.model.*
import java.io.Writer

/**
 * Draws the view of the [world].
 * Show information like player health, current world time and world map.
 *
 * Basically, it writes [world] presentation to the [uiProvider] anytime method [redraw] called.
 *
 * @param [world] wotld that will be presented
 * @param [uiProvider] output stream where the [world] will be presented.
 */
class WorldView(val world : World, val uiProvider: Writer) {

    /**
     * Redraw the [world] presentation.
     * Should be called after any changes in [world]
     */
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

    /**
     * Show the error.
     * Should be called if some error occurred.
     */
    fun showError(message: String) {
        uiProvider.write("Error occurred: $message")
        uiProvider.flush()
    }
}
