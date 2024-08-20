package io.vinicius.banking.feat.transaction

import io.vinicius.banking.grpc.TransactionRequest
import io.vinicius.banking.grpc.TransactionResponse
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import io.vinicius.banking.grpc.transactionResponse
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.oauth2.jwt.Jwt

@GrpcService
class TransactionService(
    private val transactionRepo: TransactionRepository
) : TransactionServiceGrpcKt.TransactionServiceCoroutineImplBase() {

    @PreAuthorize("isAuthenticated()")
    override suspend fun create(request: TransactionRequest): TransactionResponse {
        val jwt = SecurityContextHolder.getContext().authentication.principal as Jwt

        return transactionResponse {
            transactionId = 1
            success = true
        }
    }
}