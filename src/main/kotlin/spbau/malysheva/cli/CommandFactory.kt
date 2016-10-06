package spbau.malysheva.cli

/**
 * Command Factory Interface.
 *
 * All instances should be registered in [CommandManager].
 *
 * @see Command
 * @see CommandManager
 */
interface CommandFactory {
    fun create(arguments: List<String>): Command
}
