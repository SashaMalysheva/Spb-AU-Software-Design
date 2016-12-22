package spbau.sashamalysheva.chat.app.gui

import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.Server
import spbau.sashamalysheva.chat.api.ServerListener
import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.core.impl.ClientImpl
import spbau.sashamalysheva.chat.core.impl.ServerImpl
import java.awt.GridBagConstraints


//utility

internal fun GridBagConstraints.setup(gridx: Int,
                             gridy: Int,
                             weightx: Double = 0.0,
                             weighty: Double = 0.0,
                             gridwidth: Int = 1,
                             gridheight: Int = 1,
                             fill : Int = GridBagConstraints.HORIZONTAL,
                             anchor : Int = GridBagConstraints.PAGE_START) {
    this.gridx = gridx
    this.gridy = gridy
    this.weightx = weightx
    this.weighty = weighty
    this.gridwidth = gridwidth
    this.gridheight = gridheight
    this.fill = fill
    this.anchor = anchor
}

//frame init

fun doServerStart(user: User, port: Int) : Server {
    val server = ServerImpl(port, user)
    server.addServerListener(object : ServerListener {
        override fun onConnectionAccepted(server: Server, connection: Connection) {
            val frame = ChatFrame(connection)
            connection.sendUserInfo()
            frame.isVisible = true
        }
    })
    println("starting server by ${user.name} on $port port")
    server.start()
    return server
}

fun doConnect(user: User, host: String, port: Int) {
    val client = ClientImpl(user)
    println("connecting by ${user.name} to $host:$port")
    val connection = client.connect(host, port)
    val frame = ChatFrame(connection)
    connection.sendUserInfo()
    frame.isVisible = true
}

fun doStartUp() {
    val frame = StartUpFrame()
    frame.isVisible = true
}

fun main(args: Array<String>) {
    doStartUp()
}