package io.vinicius.banking.transactions.feat.account

import io.vinicius.banking.grpc.AccountResponse
import io.vinicius.banking.grpc.accountResponse
import io.vinicius.banking.shared.feat.account.AccountType
import io.vinicius.banking.shared.feat.account.toProto
import io.vinicius.banking.shared.ktx.toProto
import io.vinicius.banking.transactions.feat.user.User
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "accounts")
data class Account(
    @Id @GeneratedValue
    val id: Int = 0,

    @ManyToOne
    val user: User,

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType::class)
    val type: AccountType,

    val balance: BigDecimal,
    val createdAt: OffsetDateTime
)

fun Account.toProto(): AccountResponse {
    val self = this
    return accountResponse {
        id = self.id
        userId = self.user.id
        type = self.type.toProto()
        balance = self.balance.toString()
        createdAt = self.createdAt.toProto()
    }
}