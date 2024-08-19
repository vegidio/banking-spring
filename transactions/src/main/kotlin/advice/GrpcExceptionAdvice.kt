package io.vinicius.banking.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import io.grpc.Status
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler

@GrpcAdvice
class GrpcExceptionAdvice {
    private val logger = KotlinLogging.logger {}

    @GrpcExceptionHandler
    fun handleUnknownException(e: Exception): Status {
        logger.error(e) { "Unknown gRPC error" }
        return Status.UNKNOWN.withDescription(e.message).withCause(e)
    }
}