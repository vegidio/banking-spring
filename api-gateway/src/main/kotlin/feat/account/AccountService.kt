package io.vinicius.banking.api.feat.account

import com.google.protobuf.Empty
import io.vinicius.banking.api.feat.account.dto.AccountCreateRequestDto
import io.vinicius.banking.api.feat.account.dto.AccountResponseDto
import io.vinicius.banking.grpc.AccountServiceGrpcKt.AccountServiceCoroutineStub
import io.vinicius.banking.grpc.createAccountRequest
import io.vinicius.banking.shared.feat.account.toProto
import org.springframework.stereotype.Service

@Service
class AccountService(private val grpcAccount: AccountServiceCoroutineStub) {

    suspend fun createAccount(dto: AccountCreateRequestDto): AccountResponseDto {
        val request = createAccountRequest {
            type = dto.type.toProto()
        }

        val response = grpcAccount.create(request)
        return AccountResponseDto.fromProto(response)
    }

    suspend fun retrieveAccounts(): List<AccountResponseDto> {
        val response = grpcAccount.retrieve(Empty.getDefaultInstance())
        return response.resultsList.map { AccountResponseDto.fromProto(it) }
    }
}