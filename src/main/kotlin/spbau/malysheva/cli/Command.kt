package spbau.malysheva.cli

import java.io.InputStream

/**
 * Command Interface
 *
 * @see CommandFactory
 * @see CommandManager
 */
interface Command {

    /**
     * Executes the command.
     *
     * @param [stream] command input.
     * @return the result of command.
     */
    fun execute(stream: InputStream = "".byteInputStream()): InputStream
}