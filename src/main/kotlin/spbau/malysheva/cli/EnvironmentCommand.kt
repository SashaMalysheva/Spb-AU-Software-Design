package spbau.malysheva.cli

import java.io.InputStream

/**
 * Environment command.
 *
 * @param key marco name
 * @param value macro value
 */
class EnvironmentCommand(private val key: String,
                         private val value: String)
: Command {

    /**
     * Saves macro in [Environment].
     *
     * @param stream skipped
     * @return empty stream
     */
    override fun execute(stream: InputStream): InputStream {
        Environment[key] = value
        return "".byteInputStream()
    }
}