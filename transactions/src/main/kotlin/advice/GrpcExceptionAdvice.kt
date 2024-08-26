package io.vinicius.banking.transactions.advice

import io.github.oshai.kotlinlogging.KotlinLogging
import io.grpc.Status
import io.grpc.StatusException
import io.vinicius.banking.transactions.exception.GrpcException
import net.devh.boot.grpc.server.advice.GrpcAdvice
import net.devh.boot.grpc.server.advice.GrpcExceptionHandler
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException

@GrpcAdvice
class GrpcExceptionAdvice {
    private val logger = KotlinLogging.logger {}

    @GrpcExceptionHandler(GrpcException::class)
    fun handleGrpcException(e: GrpcException): StatusException {
        logger.error(e) { "gRPC error" }
        return e
    }

    @GrpcExceptionHandler
    fun handleUnknownException(e: Exception): Status {
        return if (e is AuthenticationCredentialsNotFoundException) {
            Status.UNAUTHENTICATED
        } else if (e is IllegalArgumentException && e.message == "token cannot be empty") {
            Status.UNAUTHENTICATED.withDescription("JWT_INVALID")
        } else {
            logger.error(e) { "Unknown gRPC error" }
            Status.UNKNOWN.withDescription(e.message)
        }
    }
}