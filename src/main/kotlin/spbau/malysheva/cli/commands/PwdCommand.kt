package spbau.malysheva.cli.commands

import spbau.malysheva.cli.Command
import java.io.InputStream
import java.nio.file.Paths

/**
 * PwdCommand.
 *
 * On executing, it returns current working directory.
 */
class PwdCommand() : Command {

    /**
     * Returns current working directory as [InputStream].
     *
     * @param stream skipped
     * @return a stream that containing result
     */
    override fun execute(stream: InputStream): InputStream =
            Paths.get("").toAbsolutePath().toString().plus("\n").byteInputStream()

}