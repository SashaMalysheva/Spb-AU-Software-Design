package spbau.sashamalysheva.chat.core.impl

import io.grpc.stub.StreamObserver

internal open class MessagesObserverBase : StreamObserver<PtpChat.Message>{
    override fun onCompleted() {
    }

    override fun onNext(value: PtpChat.Message?) {
    }

    override fun onError(t: Throwable?) {
    }
}