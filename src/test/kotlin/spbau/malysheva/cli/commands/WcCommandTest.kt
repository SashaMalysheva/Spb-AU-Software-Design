package spbau.malysheva.cli.commands

import org.junit.Test
import kotlin.test.assertEquals

class WcCommandTest {

    val TEST_DATA= """hello world
    word
word
    two words
two words
    """

    val TEST_RESULT="2 1 1 2 2 0\n"

    @Test
    fun execute() {
        assertEquals(TEST_RESULT, WcCommand().execute(TEST_DATA.byteInputStream()).reader().readText())
    }
}