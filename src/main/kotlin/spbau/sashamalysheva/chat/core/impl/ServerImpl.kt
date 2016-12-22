package spbau.sashamalysheva.chat.core.impl

import io.grpc.Server
import io.grpc.ServerBuilder
import spbau.sashamalysheva.chat.api.User
import spbau.sashamalysheva.chat.core.base.ServerBase

/**
 * [Server] implementation using gRPC framework
 */
class ServerImpl(override val port: Int,
                 owner: User)
: ServerBase(owner) {

    private val impl: Server

    init {
        val service = Service(this)
        impl = ServerBuilder.forPort(port).addService(service).build()
    }

    override fun start() {
        impl.start()
    }

    override fun terminate() {
        impl.shutdownNow()
    }
}