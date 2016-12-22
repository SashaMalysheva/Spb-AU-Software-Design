package spbau.sashamalysheva.chat.api

/**
 * Simple server interface.
 *
 * @see spbau.sashamalysheva.chat.core.base.ServerBase
 * @see spbau.sashamalysheva.chat.core.impl.ServerImpl
 */
interface Server {

    /**
     * Owner of the server.
     */
    val owner : User

    /**
     * Bound port.
     */
    val port : Int

    /**
     * Starts the server.
     */
    fun start()

    /**
     * Terminates the server.
     * All started connection will be terminated.
     */
    fun terminate()

    /**
     * Adds [listener] to the server
     *
     * @see ServerListener
     */
    fun addServerListener(listener: ServerListener)

    /**
     * Removes [ServerListener] to the server
     *
     * @see ServerListener
     */
    fun removeServerListener(listener: ServerListener)
}