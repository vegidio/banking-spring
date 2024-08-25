package io.vinicius.banking.config.interceptor

import io.github.oshai.kotlinlogging.KotlinLogging
import io.grpc.CallOptions
import io.grpc.Channel
import io.grpc.ClientCall
import io.grpc.ClientInterceptor
import io.grpc.MethodDescriptor
import io.vinicius.banking.ktx.jwt
import net.devh.boot.grpc.client.interceptor.GrpcGlobalClientInterceptor
import net.devh.boot.grpc.client.security.CallCredentialsHelper
import org.springframework.security.core.context.SecurityContextHolder

@GrpcGlobalClientInterceptor
class GrpcAuthInterceptor : ClientInterceptor {
    private val logger = KotlinLogging.logger {}

    override fun <ReqT : Any?, RespT : Any?> interceptCall(
        method: MethodDescriptor<ReqT, RespT>,
        options: CallOptions,
        next: Channel
    ): ClientCall<ReqT, RespT> {
        val jwt = SecurityContextHolder.getContext().jwt
        val bearerToken = CallCredentialsHelper.bearerAuth(jwt)
        val optionsWithCreds = options.withCallCredentials(bearerToken)

        logger.debug { "Adding Bearer Token to gRPC call: $jwt" }
        return next.newCall(method, optionsWithCreds);
    }
}