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
 * @param fileName name of file
 */
class CatCommand(val fileName: String) : Command {

    /**
     * Returns file content as [InputStream].
     *
     * @param stream skipped
     * @return a stream that containing result
     */
    override fun execute(stream: InputStream): InputStream {
        val reader = Files.newBufferedReader(Paths.get(fileName))
        return ReaderInputStream(reader)
    }
}
