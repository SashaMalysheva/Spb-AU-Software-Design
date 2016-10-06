package spbau.malysheva.cli

import spbau.malysheva.cli.CommandManager.createCommand
import spbau.malysheva.cli.CommandManager.registerCommandFactory
import java.util.*

/**
 * Command Manager.
 * All [command factories][CommandFactory] should be registered here.
 * This object can be used for creating command by its name.
 *
 * @see CommandFactory
 * @see registerCommandFactory
 * @see createCommand
 */
object CommandManager {
    val commandFactories = HashMap<String, CommandFactory>()

    val externalCommandFactory = object : CommandFactory {
        override fun create(arguments: List<String>): Command {
            return ExternalCommand(arguments)
        }
    }

    /**
     * Registers [commandFactory] and associates it with [commandName].
     */
    fun registerCommandFactory(commandName: String, commandFactory: CommandFactory) {
        commandFactories[commandName] = commandFactory
    }

    fun registerCommandFactory(commandName: String, creator: (List<String>) -> Command) {
        commandFactories[commandName] = object : CommandFactory {
            override fun create(arguments: List<String>) = creator(arguments)
        }
    }

    /**
     * Creates a command by provided [arguments].
     * First argument should be a name of command.
     *
     * In fact, it calls a [CommandFactory.create] method of a factory
     * that is associated with given command name.
     */
    fun createCommand(arguments: List<String>): Command? {
        if (arguments.isEmpty()) {
            return null
        }
        val commandFactory = commandFactories[arguments[0]]
        if (commandFactory == null) {
            return externalCommandFactory.create(arguments)
        } else {
            return commandFactory.create(arguments)
        }
    }
}
