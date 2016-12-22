package spbau.sashamalysheva.chat.core.impl

import io.grpc.ManagedChannelBuilder
import spbau.sashamalysheva.chat.api.Connection
import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.core.base.ClientBase

/**
 * [Client] implementation using gRPC framework
 */
class ClientImpl(user: User) : ClientBase(user) {

    override fun connect(host: String, port: Int): Connection {
        val channel = ManagedChannelBuilder.forAddress(host, port).usePlaintext(true).build()
        val stub = PtpServerGrpc.newStub(channel)
        val connection = ConnectionImpl(owner)
        val sentMessagesObserver = stub.ptp(connection.createReceivedMessagesObserver())
        connection.sentMessagesObserver = sentMessagesObserver
        return connection
    }
}