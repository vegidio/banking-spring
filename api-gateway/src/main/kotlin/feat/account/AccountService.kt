package io.vinicius.banking.api.feat.account

import io.github.oshai.kotlinlogging.KotlinLogging
import io.vinicius.banking.api.feat.account.dto.AccountCreateDto
import io.vinicius.banking.grpc.AccountServiceGrpcKt.AccountServiceCoroutineStub
import io.vinicius.banking.grpc.createAccountRequest
import org.springframework.stereotype.Service

@Service
class AccountService(private val grpcAccount: AccountServiceCoroutineStub) {
    private val logger = KotlinLogging.logger {}

    suspend fun createAccount(dto: AccountCreateDto) {
        val request = createAccountRequest {
            type = dto.type.toProto()
        }

        val response = grpcAccount.create(request)
        logger.info { "Account created: $response" }
    }
}