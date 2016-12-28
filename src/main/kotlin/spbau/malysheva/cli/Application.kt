package spbau.malysheva.cli

import org.antlr.v4.runtime.ANTLRInputStream
import org.antlr.v4.runtime.CommonTokenStream
import org.apache.commons.io.IOUtils
import spbau.malysheva.cli.commands.*
import java.io.InputStream

/**
 * CLI Application.
 *
 * Workflow:
 * 1) Reads command line from stdin
 * 2) Provides it to parser
 * 3) Executes parsed commands
 * 4) Writes output from last command on the CLI
 * 5) Repeats 1-4 until 'exit' command
 *
 * @author Sasha Malysheva
 */
class Application() {

    /**
     * Initializes & registers all command factories that available in CLI
     */
    init {
        CommandManager.registerCommandFactory("exit") { ExitCommand() }
        CommandManager.registerCommandFactory("cat") { CatCommand(it.getOrNull(1)) }
        CommandManager.registerCommandFactory("echo") { EchoCommand(it.subList(1, it.size)) }
        CommandManager.registerCommandFactory("wc") { WcCommand() }
        CommandManager.registerCommandFactory("pwd") { PwdCommand() }
        CommandManager.registerCommandFactory("grep"){
            GrepCommand(getGrepArguments(it.subList(1, it.size).toTypedArray()))
        }
    }

    /**
     * Runs CLI: executes input commands until 'exit' command.
     */
    fun run() {
        var parsedCommands: List<Command>
        do {
            parsedCommands = parse(readLine() ?: return)
            execute(parsedCommands)
        } while (!parsedCommands.isExitCommand())
    }

    private fun parse(string: String): List<Command> {
        val commandLexer = CommandLexer(ANTLRInputStream(string))
        return CommandParser(CommonTokenStream(commandLexer)).command_line().commands
    }

    private fun execute(commands: List<Command>) {
        var input: InputStream = "".byteInputStream()
        for (command in commands) {
            input = command.execute(input)
        }
        IOUtils.copy(input, System.out)
    }

    private fun List<Command>.isExitCommand(): Boolean {
        return size == 1 && this[0] is ExitCommand
    }
}
