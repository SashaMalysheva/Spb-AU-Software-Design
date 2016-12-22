package spbau.sashamalysheva.chat.app.console

import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.Server
import spbau.sashamalysheva.chat.api.ServerListener
import spbau.sashamalysheva.chat.core.base.UserBase
import spbau.sashamalysheva.chat.core.impl.ClientImpl
import spbau.sashamalysheva.chat.core.impl.ServerImpl
import kotlin.concurrent.thread


/**
 * Created by sasha on 21.12.16.
 */


const val USAGE = """You are chatting.
    Type '@rename <name>' to rename.
    Type '@exit' to leave chat.
"""

fun chat(user : UserBase, connection: Connection) {
    connection.addConnectionListener(SimpleConnectionListener())
    connection.remoteUser.addUserListener(SimpleUserListener())
    print(USAGE)

    var input: List<String>
    try {
        while (true) {
            input = (readLine() ?: "@exit").split(' ')
            when(input[0]) {
                "@exit" -> return
                "@rename" -> user.name = input[1]
                else -> connection.sendMessage(input[0])
            }
        }
    } finally {
        connection.closeConnection()
    }
}


fun main(args: Array<String>) {
    val isServer = args.isEmpty() || args[0].toBoolean()
    val port = if (args.size <= 1) 3338 else args[1].toInt()

    val user = UserBase()

    if (isServer) {
        val server = ServerImpl(port, user)
        server.addServerListener(object : ServerListener {
            override fun onConnectionAccepted(server: Server, connection: Connection) {
                thread {
                    chat(user, connection)
                }
            }
        })
        server.start()
        while (true) {
        }
    } else {
        val host = if (args.size <= 3) "localhost" else args[3]
        val connection = ClientImpl(user).connect(host, port)
        chat(user, connection)
    }
}