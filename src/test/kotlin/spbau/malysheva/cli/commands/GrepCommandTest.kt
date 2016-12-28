package spbau.malysheva.cli.commands

import org.junit.Test
import kotlin.test.assertEquals

val TEST_DATA = """bye
hello
Hello
helloworld
HelloWorld
bye bye hello bye
double hello hello
hell o
"""

val TEST_RESULT_1 = """hello
Hello
helloworld
HelloWorld
bye bye hello bye
double hello hello
"""

val TEST_RESULT_2 = """hello
helloworld
bye bye hello bye
double hello hello
"""

val TEST_RESULT_3 = """hello
Hello
bye bye hello bye
double hello hello
"""

class GrepCommandTest {

    fun getGrepResultOn(insensivity: Boolean, forceWordMathing: Boolean)
            = GrepCommand(GrepArguments(insensivity, 1, forceWordMathing, "hello"))
            .execute(TEST_DATA.byteInputStream())
            .reader()
            .readText()

    @Test
    fun grep() {
        assertEquals(TEST_RESULT_1, getGrepResultOn(true, false))
        assertEquals(TEST_RESULT_2, getGrepResultOn(false, false))
        assertEquals(TEST_RESULT_3, getGrepResultOn(true, true))
    }

}