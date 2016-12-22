package spbau.sashamalysheva.chat.app.console

import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.ConnectionListener
import kotlin.system.exitProcess

/**
 * Created by sasha on 21.12.16.
 */
class SimpleConnectionListener : ConnectionListener {
    override fun onMessageReceived(connection: Connection, text: String) {
        println("${connection.remoteUser.name} : $text")
    }

    override fun onRemoteUserChanged(connection: Connection) {
    }

    override fun onConnectionClosed(connection: Connection) {
        println("user exit")
        exitProcess(0)
    }
}