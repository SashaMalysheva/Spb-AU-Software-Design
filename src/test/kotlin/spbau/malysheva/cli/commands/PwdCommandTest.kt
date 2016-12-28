package spbau.malysheva.cli.commands

import org.junit.Test
import kotlin.test.assertEquals

import java.nio.file.Paths

class PwdCommandTest {
    @Test
    fun execute() {
        val TEST_RESULT = Paths.get("").toAbsolutePath().toString() + '\n'
        assertEquals(TEST_RESULT, PwdCommand().execute().reader().readText())
    }

}