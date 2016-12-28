package spbau.malysheva.cli.commands

import org.junit.Test
import kotlin.test.assertEquals

import java.nio.file.Files
import java.nio.file.Paths

class CatCommandTest {

    val TEST_FILE = "test.txt"
    val TEST_CONTENT = """
    test file content
    string1
    string2

    string3
"""

    @Test
    fun execute() {
        val test = Paths.get(TEST_FILE)
        Files.createFile(test)
        Files.write(test, TEST_CONTENT.toByteArray())
        assertEquals(TEST_CONTENT, CatCommand(TEST_FILE).execute().reader().readText())
        Files.delete(test)
    }

    @Test
    fun executeOnNull() {
        assertEquals(TEST_CONTENT, CatCommand().execute(TEST_CONTENT.byteInputStream()).reader().readText())
    }

}