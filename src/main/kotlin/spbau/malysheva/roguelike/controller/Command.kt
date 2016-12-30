package spbau.malysheva.roguelike.controller

import spbau.malysheva.roguelike.model.World


/**
 * A command that can be executed by user from ui.
 * User should type its 'id' to invoke it.
 */
interface Command {

    /**
     * presentation of the command on the screen.
     */
    val id : String

    /**
     * executes a command
     */
    fun execute()
}