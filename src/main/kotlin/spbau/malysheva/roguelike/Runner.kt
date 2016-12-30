package spbau.malysheva.roguelike

import spbau.malysheva.roguelike.controller.Environment

fun main(args: Array<String>) {
    Environment.newGame()

    var command = readLine()
    while (command != null && command != ":quit") {
        Environment.execute(command)
        command = readLine()
    }
}