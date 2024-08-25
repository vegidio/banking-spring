package io.vinicius.banking.config

import io.vinicius.banking.grpc.AccountServiceGrpcKt.AccountServiceCoroutineStub
import net.devh.boot.grpc.client.inject.GrpcClient
import net.devh.boot.grpc.client.inject.GrpcClientBean
import net.devh.boot.grpc.client.inject.GrpcClientBeans
import org.springframework.context.annotation.Configuration

@Configuration
@GrpcClientBeans(
    value = [
        GrpcClientBean(clazz = AccountServiceCoroutineStub::class, client = GrpcClient("account")),
    ]
)
class GrpcClientConfig