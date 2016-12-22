package spbau.sashamalysheva.chat.core.base

import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.api.UserListener
import java.util.*

/**
 * Represents base implementation of [User].
 *
 * Provides user renaming, listener managing.
 */
open class UserBase(initialName : String? = null) : User {

    companion object {
        val NAME_UNDEFINED = "???"
    }

    val listeners = HashSet<UserListener>()

    override var name : String = initialName ?: NAME_UNDEFINED
        set(value) {
            val old = field
            if (old != value) {
                field = value
                for (listener in listeners) {
                    listener.onUserRenamed(this, old)
                }
            }
        }

    override fun addUserListener(listener: UserListener) {
        listeners.add(listener)
    }

    override fun removeUserListener(listener: UserListener) {
        listeners.remove(listener)
    }
}