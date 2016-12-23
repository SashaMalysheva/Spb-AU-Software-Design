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
    /**
     * Create a command.
     * By convention, first argument  is a name of command.
     *
     * @param [arguments] command arguments.
     */
    fun create(arguments: List<String>): Command
}
