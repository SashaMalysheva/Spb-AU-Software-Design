package spbau.sashamalysheva.chat.core.base

import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.ConnectionListener
import spbau.sashamalysheva.chat.api.User
import java.util.*

/**
 * Base implementation of [Connection]
 *
 * Provides listener managing
 */
abstract class ConnectionBase(override val owner: User) : Connection {

    val listeners = HashSet<ConnectionListener>()

    override fun addConnectionListener(listener: ConnectionListener) {
        listeners.add(listener)
    }

    override fun removeConnectionListener(listener: ConnectionListener) {
        listeners.remove(listener)
    }

    fun fireMessageReceived(text : String) {
        for (listener in listeners) {
            listener.onMessageReceived(this, text)
        }
    }

    fun fireRemoteUserChanged() {
        for (listener in listeners) {
            listener.onRemoteUserChanged(this)
        }
    }

    fun fireConnectionClosed() {
        for (listener in listeners) {
            listener.onConnectionClosed(this)
        }
    }
}