package io.vinicius.banking.transactions.feat.account

import com.google.protobuf.Empty
import io.vinicius.banking.grpc.AccountServiceGrpcKt
import io.vinicius.banking.grpc.MutateAccount
import io.vinicius.banking.proto.accountList
import io.vinicius.banking.shared.feat.account.AccountType
import io.vinicius.banking.shared.feat.account.fromProto
import io.vinicius.banking.transactions.exception.NotFoundException
import io.vinicius.banking.transactions.feat.user.UserRepository
import io.vinicius.banking.transactions.ktx.subject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import net.devh.boot.grpc.server.service.GrpcService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.context.SecurityContextHolder
import java.math.BigDecimal
import java.time.OffsetDateTime
import io.vinicius.banking.proto.Account as ProtoAccount
import io.vinicius.banking.proto.AccountList as ProtoAccountList

@GrpcService
class AccountService(
    private val userRepo: UserRepository,
    private val accountRepo: AccountRepository
) : AccountServiceGrpcKt.AccountServiceCoroutineImplBase() {

    @PreAuthorize("isAuthenticated()")
    override suspend fun create(request: MutateAccount): ProtoAccount {
        val subjectId = SecurityContextHolder.getContext().subject
        val user = userRepo.findByIdOrNull(subjectId)
            ?: throw NotFoundException("User with id $subjectId not found")

        val account = Account(
            user = user,
            type = AccountType.fromProto(request.type),
            balance = BigDecimal.ZERO,
            createdAt = OffsetDateTime.now()
        )

        val newAccount = withContext(Dispatchers.IO) {
            accountRepo.save(account)
        }

        return newAccount.toProto()
    }

    @PreAuthorize("isAuthenticated()")
    override suspend fun retrieve(request: Empty): ProtoAccountList {
        val subjectId = SecurityContextHolder.getContext().subject
        val accounts = withContext(Dispatchers.IO) {
            accountRepo.findByUserId(subjectId)
        }

        return accountList {
            results.addAll(accounts.map { it.toProto() })
        }
    }
}