package spbau.malysheva.roguelike.controller

import spbau.malysheva.roguelike.model.World


interface Command {

    val id : String

    fun execute()
}