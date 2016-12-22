package spbau.sashamalysheva.chat.app.gui

import spbau.sashamalysheva.chat.api.Server
import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.api.UserListener
import spbau.sashamalysheva.chat.core.base.UserBase
import java.awt.Dimension
import java.awt.GridBagConstraints
import java.awt.GridBagLayout
import java.awt.event.ComponentAdapter
import java.awt.event.ComponentEvent
import java.awt.event.MouseAdapter
import java.awt.event.MouseEvent
import javax.swing.*

/**
 * Chat start up GUI
 */
class StartUpFrame : JFrame() {

    val user = UserBase()
    var isServer = true
    var server : Server? = null

    val nameLabel = JLabel(user.name)
    val nameInputTextField = JTextField()
    val renameButton = JButton("Rename")

    val hostInputTextField = JTextField("localhost")
    val portInputTextField = JTextField("3338")
    val connectButton = JButton("Start")

    init {
        title = "Chat : Starting up"

        contentPane.layout = GridBagLayout()
        val c = GridBagConstraints()

        val nameAlias = JLabel("User Name:")
        c.setup(0, 0)
        contentPane.add(nameAlias, c)

        //nameLabel
        c.setup(1, 0, weightx = 1.0)
        contentPane.add(nameLabel, c)

        val renameAlias = JLabel("Choose New Name:")
        c.setup(0, 1)
        contentPane.add(renameAlias, c)

        //nameInputTextField
        c.setup(1, 1, weightx = 1.0)
        contentPane.add(nameInputTextField, c)

        //renameButton
        c.setup(2, 1)
        contentPane.add(renameButton, c)

        val separator = JSeparator()
        c.setup(0, 2, gridwidth = 3, weighty = 1.0, anchor = GridBagConstraints.PAGE_END)
        contentPane.add(separator, c)

        val serverCheckbox = JRadioButton("Create New Chat (server)", null, true)
        c.setup(0, 3, gridwidth = 3)
        contentPane.add(serverCheckbox, c)

        val clientCheckbox = JRadioButton("Connect to Existing Chat (client)")
        c.setup(0, 4, gridwidth = 3)
        contentPane.add(clientCheckbox, c)

        val hostAlias = JLabel("Host:")
        c.setup(0, 5)
        contentPane.add(hostAlias, c)

        //hostInputTextField
        c.setup(1, 5, gridwidth =  2, weightx = 1.0)
        contentPane.add(hostInputTextField, c)

        val portAlias = JLabel("Port:")
        c.setup(0, 6)
        contentPane.add(portAlias, c)

        //portInputTextField
        c.setup(1, 6, gridwidth =  2, weightx = 1.0)
        contentPane.add(portInputTextField, c)

        //connectButton
        c.setup(0, 7, gridwidth = 3, anchor = GridBagConstraints.LAST_LINE_END)
        contentPane.add(connectButton, c)

        //listeners

        val bg = ButtonGroup()
        bg.add(clientCheckbox)
        bg.add(serverCheckbox)

        user.addUserListener(object : UserListener {
            override fun onUserRenamed(user: User, oldName: String?) {
                nameLabel.text = user.name
            }
        })

        renameButton.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                if (nameInputTextField.text.isNullOrBlank()) {
                    return
                }
                user.name = nameInputTextField.text.trim()
            }
        })

        serverCheckbox.addChangeListener {
            if (it.source !is JRadioButton) {
                return@addChangeListener
            }
            isServer = (it.source as JRadioButton).isSelected
            hostInputTextField.isEnabled = !isServer
        }

        connectButton.addMouseListener(object : MouseAdapter() {
            override fun mouseClicked(e: MouseEvent?) {
                val currentServer = server
                if (currentServer != null) {
                    currentServer.terminate()
                    server = null
                    connectButton.text = "Start"
                    return
                }
                if (isServer) {
                    server = doServerStart(user, portInputTextField.text.toInt())
                    connectButton.text = "Stop running server"
                } else {
                    doConnect(user, hostInputTextField.text, portInputTextField.text.toInt())
                }
            }
        })



        addComponentListener(object : ComponentAdapter() {
            override fun componentHidden(e: ComponentEvent?) {
                server?.terminate()
            }
        })

        minimumSize = Dimension(320, 160)
        size = Dimension(320, 240)
        defaultCloseOperation = EXIT_ON_CLOSE
    }
}

