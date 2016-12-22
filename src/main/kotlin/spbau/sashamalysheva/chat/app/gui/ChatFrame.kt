package spbau.sashamalysheva.chat.app.gui

import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.ConnectionListener
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

/**
 * Chat GUI by provided [connection]
 */
class ChatFrame(val connection: Connection) : JFrame() {

    val historyTextArea = JTextArea()
    val messageTextField = JTextField()
    val sendMessageButton = JButton("Send")

    val titleText : String
        get() = "Chat with ${connection.remoteUser.name}"

    init {
        title = titleText

        contentPane.layout = GridBagLayout()
        val c = GridBagConstraints()

        //historyTextArea
        c.setup(0, 0, weighty = 1.0, weightx = 1.0, gridwidth = 2, fill = GridBagConstraints.BOTH)
        contentPane.add(historyTextArea, c)

        //messageTextField
        c.setup(0, 1, weightx = 1.0)
        contentPane.add(messageTextField, c)

        //sendMessageButton
        c.setup(1, 1)
        contentPane.add(sendMessageButton, c)

        // listeners

        connection.addConnectionListener(object : ConnectionListener {
            override fun onMessageReceived(connection: Connection, text: String) {
                historyTextArea.append(
                        "-> ${connection.remoteUser.name} :: $text\n"
                )
            }

            override fun onRemoteUserChanged(connection: Connection) {
                title = titleText
            }

            override fun onConnectionClosed(connection: Connection) {
                isVisible = false
                dispose()
            }
        })

        sendMessageButton.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (messageTextField.text.isNullOrBlank()) {
                    return
                }
                val text = messageTextField.text.trim()
                connection.sendMessage(text)
                historyTextArea.append("<- ${connection.owner.name} :: $text\n")
                messageTextField.text = ""
            }
        })

        addComponentListener(object : ComponentAdapter() {
            override fun componentHidden(e: ComponentEvent?) {
                connection.closeConnection()
            }
        })

        size = Dimension(320, 240)
        defaultCloseOperation = WindowConstants.HIDE_ON_CLOSE
    }
}