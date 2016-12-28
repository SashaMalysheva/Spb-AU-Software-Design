package spbau.malysheva.cli.commands

import spbau.malysheva.cli.Command
import java.io.InputStream

/**
 * CatCommand.
 *
 * On executing, it returns words count on each line.
 */
class WcCommand() : Command {

    /**
     * Returns words count of each line in [stream].
     *
     * @param stream input stream
     * @return a stream that containing result
     */
    override fun execute(stream: InputStream): InputStream = stream
            .reader()
            .readLines()
            .joinToString(separator = " ", postfix = "\n") {
                it.split(" ").filter { it.isNotEmpty() }.size.toString()
            }.byteInputStream()
}