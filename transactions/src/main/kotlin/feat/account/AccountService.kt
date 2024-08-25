package io.vinicius.banking.feat.account

import com.google.protobuf.Empty
import io.vinicius.banking.exception.NotFoundException
import io.vinicius.banking.feat.user.UserRepository
import io.vinicius.banking.grpc.AccountListResponse
import io.vinicius.banking.grpc.AccountResponse
import io.vinicius.banking.grpc.AccountServiceGrpcKt
import io.vinicius.banking.grpc.CreateAccountRequest
import io.vinicius.banking.grpc.accountListResponse
import io.vinicius.banking.ktx.grpc.toModel
import io.vinicius.banking.ktx.subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import java.math.BigDecimal
import java.time.OffsetDateTime

@GrpcService
class AccountService(
    private val userRepo: UserRepository,
    private val accountRepo: AccountRepository
) : AccountServiceGrpcKt.AccountServiceCoroutineImplBase() {

    @PreAuthorize("isAuthenticated()")
    override suspend fun create(request: CreateAccountRequest): AccountResponse {
        val subjectId = SecurityContextHolder.getContext().subject
        val user = userRepo.findByIdOrNull(subjectId)
            ?: throw NotFoundException("User with id $subjectId not found")

        val account = Account(
            user = user,
            type = request.type.toModel(),
            balance = BigDecimal.ZERO,
            createdAt = OffsetDateTime.now()
        )

        val newAccount = withContext(Dispatchers.IO) {
            accountRepo.save(account)
        }

        return newAccount.toProto()
    }

    @PreAuthorize("isAuthenticated()")
    override suspend fun retrieve(request: Empty): AccountListResponse {
        val subjectId = SecurityContextHolder.getContext().subject
        val accounts = withContext(Dispatchers.IO) {
            accountRepo.findByUserId(subjectId)
        }

        return accountListResponse {
            results.addAll(accounts.map { it.toProto() })
        }
    }
}