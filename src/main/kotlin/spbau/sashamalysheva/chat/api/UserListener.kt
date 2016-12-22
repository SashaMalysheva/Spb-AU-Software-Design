package spbau.sashamalysheva.chat.api

/**
 * Listener of [User] events.
 *
 * @see spbau.sashamalysheva.chat.core.base.UserBase
 */
interface UserListener {

    /**
     * Invoked when [user] name is changed.
     *
     * @param [oldName] the old name of user; new name can be get from *user.name*
     */
    fun onUserRenamed(user: User, oldName : String?)
}