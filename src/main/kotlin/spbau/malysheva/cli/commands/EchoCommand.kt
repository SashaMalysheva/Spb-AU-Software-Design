package spbau.malysheva.cli.commands

import spbau.malysheva.cli.Command
import java.io.InputStream

/**
 * EchoCommand.
 *
 * On executing, it returns [args] joined in line .
 * @param args list of arguments
 */
class EchoCommand(val args: List<String>) : Command {

    /**
     * Returns [args] joined in line as [InputStream].
     *
     * @param stream skipped
     * @return a stream that containing result
     */
    override fun execute(stream: InputStream) =
            args.joinToString(separator = " ", postfix = "\n").byteInputStream()
}