package spbau.malysheva.cli.commands

import org.junit.Test
import kotlin.test.assertEquals

class EchoCommandTest {

    val TEST_DATA = listOf(
            "Hello",
            "World",
            "          string with     spaces   "
    )

    val TEST_RESULT = "Hello World           string with     spaces   \n"

    @Test
    fun execute() {
        assertEquals(TEST_RESULT, EchoCommand(TEST_DATA).execute().reader().readText())
    }

}