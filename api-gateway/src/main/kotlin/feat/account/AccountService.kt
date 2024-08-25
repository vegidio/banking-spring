package io.vinicius.banking.feat.account

import io.github.oshai.kotlinlogging.KotlinLogging
import io.vinicius.banking.feat.account.dto.AccountCreateDto
import io.vinicius.banking.grpc.AccountServiceGrpc.AccountServiceBlockingStub
import io.vinicius.banking.grpc.createAccountRequest
import net.devh.boot.grpc.client.inject.GrpcClient
import net.devh.boot.grpc.client.security.CallCredentialsHelper
import org.springframework.stereotype.Service

@Service
class AccountService {
    private val logger = KotlinLogging.logger {}

    @GrpcClient("account")
    private val grpc: AccountServiceBlockingStub? = null

    fun createAccount(dto: AccountCreateDto) {
        val request = createAccountRequest {
            type = dto.type.toProto()
        }

        val coco = CallCredentialsHelper.bearerAuth("eyJ0eXAiOiJKV1QiLCJhbGciOiJFUzI1NiJ9.eyJzdWIiOiIxIiwiZXhwIjoxNzI0NjA5MzU5LCJpYXQiOjE3MjQ2MDU3NTksInVzZXJuYW1lIjoidmluaWNpdXMifQ.KWtULu0heyi3n61o6HobofIqDa-oNqI__RYRrHnkXT5jYI827VlM1sIVMAZz8lg1FmlT2olcMhXL2vKRPGmuTg")

        val response = grpc?.withCallCredentials(coco)?.create(request)
        logger.info { "Account created: $response" }
    }
}