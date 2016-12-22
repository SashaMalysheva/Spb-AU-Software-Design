package spbau.sashamalysheva.chat.api

/**
 * Simple ptp-connection interface.
 *
 * @see spbau.sashamalysheva.chat.core.base.ConnectionBase
 * @see spbau.sashamalysheva.chat.core.impl.ConnectionImpl
 */
interface Connection {

    /**
     * Owner of the connection.
     */
    val owner: User

    /**
     * remote-side owner of the connection.
     */
    val remoteUser: User

    /**
     * Sends message to remote owner.
     *
     * @param text message that will be sent to remote owner.
     */
    fun sendMessage(text: String)

    /**
     * Sends [owner] presentation to remote owner.
     */
    fun sendUserInfo()

    /**
     * Closes the connection.
     */
    fun closeConnection()

    /**
     * Presents that connection is closed.
     */
    val closed: Boolean

    /**
     * Adds [listener] to the connection
     *
     * @see [ConnectionListener]
     */
    fun addConnectionListener(listener: ConnectionListener)

    /**
     * Removes [listener] to the connection
     *
     * @see [ConnectionListener]
     */
    fun removeConnectionListener(listener: ConnectionListener)
}