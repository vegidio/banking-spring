package io.vinicius.banking.transactions.feat.transaction

import io.vinicius.banking.grpc.CreateTransactionRequest
import io.vinicius.banking.grpc.RetrieveByAccountRequest
import io.vinicius.banking.grpc.TransactionListResponse
import io.vinicius.banking.grpc.TransactionResponse
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import io.vinicius.banking.grpc.transactionListResponse
import io.vinicius.banking.shared.feat.transaction.TransactionType
import io.vinicius.banking.shared.feat.transaction.fromProto
import io.vinicius.banking.transactions.exception.NotFoundException
import io.vinicius.banking.transactions.feat.account.AccountRepository
import io.vinicius.banking.transactions.ktx.subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import java.math.BigDecimal
import java.time.OffsetDateTime

@GrpcService
class TransactionService(
    private val accountRepo: AccountRepository,
    private val transactionRepo: TransactionRepository
) : TransactionServiceGrpcKt.TransactionServiceCoroutineImplBase() {

    @PreAuthorize("isAuthenticated()")
    override suspend fun create(request: CreateTransactionRequest): TransactionResponse {
        val account = accountRepo.findByIdOrNull(request.accountId)
            ?: throw NotFoundException("Account with id ${request.accountId} not found")

        val transaction = Transaction(
            account = account,
            type = TransactionType.fromProto(request.type),
            amount = BigDecimal(request.amount),
            destinationAccountId = request.destinationAccountId.takeIf { it != 0 },
            message = request.message.ifBlank { null },
            createdAt = OffsetDateTime.now()
        )

        val newTransaction = withContext(Dispatchers.IO) {
            transactionRepo.save(transaction)
        }

        return newTransaction.toProto()
    }

    @PreAuthorize("isAuthenticated()")
    override suspend fun retrieve(request: RetrieveByAccountRequest): TransactionListResponse {
        val subjectId = SecurityContextHolder.getContext().subject
        val account = withContext(Dispatchers.IO) {
            accountRepo.findByUserIdAndId(subjectId, request.accountId)
                ?: throw NotFoundException("Account with id ${request.accountId} not found")
        }

        val transactions = withContext(Dispatchers.IO) {
            transactionRepo.findByAccountId(account.id)
        }

        return transactionListResponse {
            results.addAll(transactions.map { it.toProto() })
        }
    }
}