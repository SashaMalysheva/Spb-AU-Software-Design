package spbau.malysheva.cli

import java.util.*

/**
 * Macro container.
 */
object Environment {
    private val macros = HashMap<String, String>()

    /**
     * Returns value of macro
     *
     * @param name macro name
     * @return macro value
     */
    operator fun get(name: String) = macros[name]

    /**
     * Sets value of macro
     *
     * @param name macro name
     * @param value macro value
     */
    operator fun set(name: String, value: String) {
        macros[name] = value
    }
}