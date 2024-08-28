package io.vinicius.banking.api.feat.account

import io.github.oshai.kotlinlogging.KotlinLogging
import io.vinicius.banking.api.feat.account.dto.AccountCreateRequestDto
import io.vinicius.banking.api.feat.account.dto.AccountResponseDto
import io.vinicius.banking.grpc.AccountServiceGrpcKt.AccountServiceCoroutineStub
import io.vinicius.banking.grpc.createAccountRequest
import io.vinicius.banking.shared.feat.account.AccountType
import io.vinicius.banking.shared.feat.account.fromProto
import io.vinicius.banking.shared.feat.account.toProto
import org.springframework.stereotype.Service
import java.math.BigDecimal

@Service
class AccountService(private val grpcAccount: AccountServiceCoroutineStub) {
    private val logger = KotlinLogging.logger {}

    suspend fun createAccount(dto: AccountCreateRequestDto): AccountResponseDto {
        val request = createAccountRequest {
            type = dto.type.toProto()
        }

        val response = grpcAccount.create(request)
        logger.info { "Account created: $response" }

        return AccountResponseDto(
            id = response.id,
            type = AccountType.fromProto(request.type),
            balance = BigDecimal(response.balance),
        )
    }
}