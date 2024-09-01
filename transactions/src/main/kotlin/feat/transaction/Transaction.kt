package io.vinicius.banking.transactions.feat.transaction

import io.vinicius.banking.proto.transaction
import io.vinicius.banking.shared.feat.transaction.TransactionType
import io.vinicius.banking.shared.feat.transaction.toProto
import io.vinicius.banking.shared.ktx.toProto
import io.vinicius.banking.transactions.feat.account.Account
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
import io.vinicius.banking.proto.Transaction as ProtoTransaction

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id @GeneratedValue
    val id: Int = 0,

    @ManyToOne
    val account: Account,

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType::class)
    val type: TransactionType,

    val amount: BigDecimal,
    val destinationAccountId: Int?,
    val message: String?,
    val createdAt: OffsetDateTime,
)

fun Transaction.toProto(): ProtoTransaction {
    val self = this
    return transaction {
        id = self.id
        accountId = self.account.id
        type = self.type.toProto()
        amount = self.amount.toString()
        destinationAccountId = self.destinationAccountId ?: 0
        message = self.message ?: ""
        createdAt = self.createdAt.toProto()
    }
}