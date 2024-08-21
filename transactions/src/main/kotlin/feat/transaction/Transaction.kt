package io.vinicius.banking.feat.transaction

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id @GeneratedValue
    val id: Int = 0,

    val accountId: Int,
    val type: TransactionType,
    val amount: BigDecimal,
    val createdAt: OffsetDateTime,
    val message: String
)
