package spbau.sashamalysheva.chat.app.console

import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.api.UserListener

/**
 * Created by radimir on 21.12.16.
 */
class SimpleUserListener : UserListener {
    override fun onUserRenamed(user: User, oldName: String?) {
        println("user $oldName renamed to ${user.name}")
    }
}