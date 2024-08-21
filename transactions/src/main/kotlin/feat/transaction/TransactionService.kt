package io.vinicius.banking.feat.transaction

import com.google.protobuf.Empty
import io.vinicius.banking.grpc.CreateTransactionRequest
import io.vinicius.banking.grpc.TransactionListResponse
import io.vinicius.banking.grpc.TransactionResponse
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import io.vinicius.banking.ktx.subject
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder

@GrpcService
class TransactionService(
    private val transactionRepo: TransactionRepository
) : TransactionServiceGrpcKt.TransactionServiceCoroutineImplBase() {

    @PreAuthorize("isAuthenticated()")
    override suspend fun create(request: CreateTransactionRequest): TransactionResponse {
        val subjectId = SecurityContextHolder.getContext().subject
        return super.create(request)
    }

    @PreAuthorize("isAuthenticated()")
    override suspend fun retrieve(request: Empty): TransactionListResponse {
        val subjectId = SecurityContextHolder.getContext().subject
        return super.retrieve(request)
    }
}