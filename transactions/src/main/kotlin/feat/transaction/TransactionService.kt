package io.vinicius.banking.feat.transaction

import io.vinicius.banking.grpc.TransactionRequest
import io.vinicius.banking.grpc.TransactionResponse
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import io.vinicius.banking.grpc.transactionResponse
import net.devh.boot.grpc.server.service.GrpcService

@GrpcService
class TransactionService : TransactionServiceGrpcKt.TransactionServiceCoroutineImplBase() {
    override suspend fun create(request: TransactionRequest): TransactionResponse {
        return transactionResponse {
            transactionId = 1
            success = true
        }
    }
}