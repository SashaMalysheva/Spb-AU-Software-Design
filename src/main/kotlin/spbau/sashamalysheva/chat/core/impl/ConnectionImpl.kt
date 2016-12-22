package spbau.sashamalysheva.chat.core.impl

import io.grpc.stub.StreamObserver
import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.api.UserListener
import spbau.sashamalysheva.chat.core.base.ConnectionBase
import spbau.sashamalysheva.chat.core.base.UserBase

/**
 * [Connection] implementation using gRPC framework
 */
internal class ConnectionImpl(owner: User) : ConnectionBase(owner) {

    var sentMessagesObserver: StreamObserver<PtpChat.Message> = MessagesObserverBase()

    init {
        owner.addUserListener(object : UserListener {
            override fun onUserRenamed(user: User, oldName: String?) {
                if (user == owner) {
                    sendUserInfo()
                }
            }
        })
    }

    companion object {
        const val TYPE_PLAIN_MESSAGE = 0
        const val TYPE_USER_INFO = 1
    }

    private var closeImpl = false

    override val remoteUser = UserBase()

    override val closed: Boolean
        get() = closeImpl

    override fun sendMessage(text: String) {
        assert(!closeImpl)
        sentMessagesObserver.onNext(PtpChat.Message.newBuilder()
                .setType(TYPE_PLAIN_MESSAGE)
                .setText(text)
                .build())
    }

    override fun closeConnection() {
        if (!closeImpl) {
            closeImpl = true
            sentMessagesObserver.onCompleted()
            fireConnectionClosed()
        }
    }

    internal fun closeConnectionSilent() {
        if (!closeImpl) {
            closeImpl = true
            fireConnectionClosed()
        }
    }

    override fun sendUserInfo() {
        assert(!closeImpl)
        sentMessagesObserver.onNext(PtpChat.Message.newBuilder()
                .setType(TYPE_USER_INFO)
                .setText(owner.name)
                .build())
    }


    internal fun createReceivedMessagesObserver() = object : MessagesObserverBase() {
        override fun onError(t: Throwable?) {
            closeConnectionSilent()
        }

        override fun onCompleted() {
            closeConnectionSilent()
        }

        override fun onNext(value: PtpChat.Message?) {
            value ?: return
            when (value.type) {
                ConnectionImpl.TYPE_PLAIN_MESSAGE -> fireMessageReceived(value.text)
                ConnectionImpl.TYPE_USER_INFO -> {
                    remoteUser.name = value.text
                    fireRemoteUserChanged()
                }
            }
        }
    }
}

