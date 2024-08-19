package io.vinicius.banking.feat.transaction

import io.vinicius.banking.grpc.TransactionRequest
import io.vinicius.banking.grpc.TransactionResponse
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import io.vinicius.banking.grpc.transactionResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.access.prepost.PreAuthorize

@GrpcService
class TransactionService : TransactionServiceGrpcKt.TransactionServiceCoroutineImplBase() {

    @PreAuthorize("isAuthenticated()")
    override suspend fun create(request: TransactionRequest): TransactionResponse {
        return transactionResponse {
            transactionId = 1
            success = true
        }
    }
}