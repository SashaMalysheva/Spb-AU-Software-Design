package spbau.sashamalysheva.chat.core.impl

import io.grpc.stub.StreamObserver

internal class Service(private val serverImpl: ServerImpl) : PtpServerGrpc.PtpServerImplBase() {

    override fun ptp(responseObserver: StreamObserver<PtpChat.Message>?): StreamObserver<PtpChat.Message> {
        responseObserver ?: return super.ptp(responseObserver)

        val connection = ConnectionImpl(serverImpl.owner)
        connection.sentMessagesObserver = responseObserver
        val receivedMessagesObserver = connection.createReceivedMessagesObserver()
        serverImpl.fireConnectionAccepted(connection)
        return receivedMessagesObserver
    }
}