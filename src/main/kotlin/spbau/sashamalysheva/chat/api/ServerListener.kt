package spbau.sashamalysheva.chat.api

/**
 * Listener of [Server] events. Triggered by incoming connections.
 */
interface ServerListener {

    /**
     * Invoked when incoming [connection] is accepted by [server].
     */
    fun onConnectionAccepted(server: Server, connection: Connection)
}