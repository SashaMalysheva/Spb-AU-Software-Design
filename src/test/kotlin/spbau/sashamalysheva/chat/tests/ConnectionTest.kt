package spbau.sashamalysheva.chat.tests

import org.junit.Test
import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.ConnectionListener
import spbau.sashamalysheva.chat.api.Server
import spbau.sashamalysheva.chat.api.ServerListener
import spbau.sashamalysheva.chat.core.base.UserBase
import spbau.sashamalysheva.chat.core.impl.ClientImpl
import spbau.sashamalysheva.chat.core.impl.ServerImpl
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class ConnectionTest {

    companion object{
        const val TEST_PORT = 3338
        const val TEST_MESSAGE = "test-message"
        @Volatile var lastServerConnection: Connection? = null
        @Volatile var lastMessageReceived: String? = null
    }

    class TestConnectionListener() : ConnectionListener {
        override fun onMessageReceived(connection: Connection, text: String) {
            lastMessageReceived = text
        }

        override fun onRemoteUserChanged(connection: Connection) {
        }

        override fun onConnectionClosed(connection: Connection) {
        }
    }

    internal fun testCoConnections(left : Connection, right : Connection) {
        val testListener = TestConnectionListener()
        right.addConnectionListener(testListener)

        left.sendMessage(TEST_MESSAGE)
        Thread.sleep(500)
        assertEquals(TEST_MESSAGE, lastMessageReceived, "sent and received should be equal")

        left.sendUserInfo()
        Thread.sleep(500)
        assertEquals(left.owner.name, right.remoteUser.name, "user name should be same")

        right.removeConnectionListener(testListener)
    }

    @Test
    fun test(){
        val clientUser = UserBase("client-user")
        val serverUser = UserBase("server-user")

        val server = ServerImpl(TEST_PORT, serverUser)
        server.addServerListener(object : ServerListener {
            override fun onConnectionAccepted(server: Server, connection: Connection) {
                lastServerConnection = connection
            }
        })
        server.start()

        val client = ClientImpl(clientUser)
        val clientConnection = client.connect("localhost", TEST_PORT)

        // waiting for connection establishing
        Thread.sleep(500)
        val serverConnection = assertNotNull(lastServerConnection, "server doesn't accept incoming connection")

        testCoConnections(clientConnection, serverConnection)
        testCoConnections(serverConnection, clientConnection)

        //test closing
        serverConnection.closeConnection()
        Thread.sleep(500)
        assertTrue(clientConnection.closed, "server has closed, but client hasn't")

        server.terminate()
    }

}
