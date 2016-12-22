package spbau.sashamalysheva.chat.api

/**
 * simple client interface
 */
interface Client {

    /**
     * owner of the client
     */
    val owner : User

    /**
     * connects to the [host]:[port]
     *
     * @return the resulting connection
     */
    fun connect(host : String, port : Int) : Connection
}