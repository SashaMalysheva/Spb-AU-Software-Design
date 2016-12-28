package spbau.malysheva.cli.commands

import  org.apache.commons.io.input.ReaderInputStream

import spbau.malysheva.cli.Command
import java.io.InputStream
import java.nio.file.Files
import java.nio.file.Paths

/**
 * CatCommand.
 *
 * On executing, it returns file content as [InputStream].
 * @param fileName name of file, can be null
 */
class CatCommand(val fileName: String? = null) : Command {

    /**
     * Returns file content as [InputStream].
     *
     * @param [stream] incoming stream; skipped if [fileName] is not null
     * @return a stream that containing result if [fileName] is not null,
     *         or incoming [stream] otherwise.
     */
    override fun execute(stream: InputStream): InputStream {
        if (fileName == null) {
            return stream
        }
        val reader = Files.newBufferedReader(Paths.get(fileName))
        return ReaderInputStream(reader)
    }
}
