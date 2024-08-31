package io.vinicius.banking.api.config

import io.vinicius.banking.grpc.AccountServiceGrpcKt
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import net.devh.boot.grpc.client.inject.GrpcClient
import net.devh.boot.grpc.client.inject.GrpcClientBean
import net.devh.boot.grpc.client.inject.GrpcClientBeans
import org.springframework.context.annotation.Configuration

@Configuration
@GrpcClientBeans(
    value = [
        GrpcClientBean(
            clazz = AccountServiceGrpcKt.AccountServiceCoroutineStub::class,
            client = GrpcClient("account")
        ),
        GrpcClientBean(
            clazz = TransactionServiceGrpcKt.TransactionServiceCoroutineStub::class,
            client = GrpcClient("transaction")
        )
    ]
)
class GrpcClientConfig