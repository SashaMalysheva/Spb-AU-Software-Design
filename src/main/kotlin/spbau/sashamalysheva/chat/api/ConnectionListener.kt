package spbau.sashamalysheva.chat.api

/**
 * Listener of [Connection] events, triggered by remote-side owner.
 */
interface ConnectionListener {

    /**
     * Invoked when remote-side [connection] owner sent [text].
     *
     * @param text message that sent by remote.
     */
    fun onMessageReceived(connection: Connection, text: String)

    /**
     * Invoked when remote-side [connection] owner info changed.
     */
    fun onRemoteUserChanged(connection: Connection)

    /**
     * Invoked when remote-side [connection] owner closed the connection.
     */
    fun onConnectionClosed(connection: Connection)
}