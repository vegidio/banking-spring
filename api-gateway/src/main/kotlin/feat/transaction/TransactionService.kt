package io.vinicius.banking.api.feat.transaction

import io.vinicius.banking.api.feat.transaction.dto.TransactionCreateDto
import io.vinicius.banking.api.feat.transaction.dto.TransactionResponseDto
import io.vinicius.banking.grpc.TransactionServiceGrpcKt
import io.vinicius.banking.grpc.createTransactionRequest
import io.vinicius.banking.grpc.retrieveByAccountRequest
import io.vinicius.banking.shared.feat.transaction.toProto
import org.springframework.stereotype.Service

@Service
class TransactionService(private val grpcTransaction: TransactionServiceGrpcKt.TransactionServiceCoroutineStub) {

    suspend fun createTransaction(dto: TransactionCreateDto): TransactionResponseDto {
        val request = createTransactionRequest {
            accountId = dto.accountId
            type = dto.type.toProto()
            amount = dto.amount.toPlainString()
            destinationAccountId = dto.destinationAccountId ?: 0
            message = dto.message ?: ""
        }

        val response = grpcTransaction.create(request)
        return TransactionResponseDto.fromProto(response)
    }

    suspend fun retrieveTransactions(accountId: Int): List<TransactionResponseDto> {
        val request = retrieveByAccountRequest {
            this.accountId = accountId
        }
        val response = grpcTransaction.retrieve(request)
        return response.resultsList.map { TransactionResponseDto.fromProto(it) }
    }
}