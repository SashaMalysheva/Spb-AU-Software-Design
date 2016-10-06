package spbau.malysheva.cli

import java.io.InputStream

/**
 * Command Interface
 *
 * @see CommandFactory
 * @see CommandManager
 */
interface Command {
    fun execute(stream: InputStream = "".byteInputStream()): InputStream
}