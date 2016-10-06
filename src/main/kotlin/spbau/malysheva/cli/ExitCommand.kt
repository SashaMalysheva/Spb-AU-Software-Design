package spbau.malysheva.cli

import java.io.InputStream

/**
 * 'exit' command.
 * This command is handled in special way in [Application.run].
 *
 * In fact, [Application] will be stopped on handling it.
 *
 * @see Application.run
 */
class ExitCommand() : Command {

    /**
     * Do nothing.
     */
    override fun execute(stream: InputStream): InputStream {
        return "".byteInputStream()
    }
}