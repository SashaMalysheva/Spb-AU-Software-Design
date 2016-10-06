package spbau.malysheva.cli

import org.apache.commons.io.IOUtils
import java.io.IOException
import java.io.InputStream

/**
 * External Command. Runs an external process.
 *
 * @see CommandManager.createCommand
 */
class ExternalCommand(val args: List<String>) : Command {

    /**
     * Run an external process.
     *
     * @param stream input of external process
     * @return output of external process
     */
    override fun execute(stream: InputStream): InputStream {
        val builder = ProcessBuilder(args)
        val process = try {
            builder.start()
        } catch (ex: IOException) {
            ex.printStackTrace()
            return "".byteInputStream()
        }
        IOUtils.copy(stream, process.outputStream)
        process.waitFor()
        return process.inputStream
    }
}
