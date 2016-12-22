package spbau.sashamalysheva.chat.api

/**
 * Application user identity.
 */
interface User {

    /**
     * Presents user name.
     * Note that name can be changed after while.
     *
     * @see [UserListener]
     */
    val name : String

    /**
     * Adds [listener] to the user.
     *
     * @see [UserListener]
     */
    fun addUserListener(listener: UserListener)

    /**
     * Removes [listener] from the user.
     *
     * @see [UserListener]
     */
    fun removeUserListener(listener: UserListener)
}