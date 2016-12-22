package spbau.sashamalysheva.chat.core.base

import spbau.sashamalysheva.chat.api.Client
import spbau.sashamalysheva.chat.api.User

/**
 * Base implementation of [Client].
 */
abstract class ClientBase(override val owner: User) : Client