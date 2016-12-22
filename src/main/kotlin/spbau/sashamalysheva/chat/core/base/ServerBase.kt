package spbau.sashamalysheva.chat.core.base

import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.Server
import spbau.sashamalysheva.chat.api.ServerListener
import spbau.sashamalysheva.chat.api.User
import java.util.*

/**
 * Base implementation of [Server].
 *
 * Provides listener managing.
 */
abstract class ServerBase(override val owner: User) : Server {

    val listeners = HashSet<ServerListener>()

    override fun addServerListener(listener: ServerListener) {
        listeners.add(listener)
    }

    override fun removeServerListener(listener: ServerListener) {
        listeners.remove(listener)
    }

    fun fireConnectionAccepted(connection : Connection) {
        for (listener in listeners) {
            listener.onConnectionAccepted(this, connection)
        }
    }
}