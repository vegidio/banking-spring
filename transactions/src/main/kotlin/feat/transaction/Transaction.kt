package io.vinicius.banking.feat.transaction

import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import org.hibernate.annotations.Generated
import org.hibernate.generator.EventType
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "transactions")
data class Transaction(
    @Id @Generated(event = [EventType.INSERT])
    val id: Int,

    val accountId: Int,
    val type: TransactionType,
    val amount: BigDecimal,
    val createdAt: OffsetDateTime,
    val message: String
)
