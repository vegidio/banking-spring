package io.vinicius.banking.feat.account

import io.vinicius.banking.feat.user.User
import jakarta.persistence.Entity
import jakarta.persistence.Enumerated
import jakarta.persistence.Id
import jakarta.persistence.ManyToOne
import jakarta.persistence.Table
import org.hibernate.annotations.Generated
import org.hibernate.annotations.JdbcType
import org.hibernate.dialect.PostgreSQLEnumJdbcType
import org.hibernate.generator.EventType
import java.math.BigDecimal
import java.time.OffsetDateTime

@Entity
@Table(name = "accounts")
data class Account(
    @Id @Generated(event = [EventType.INSERT])
    val id: Int,

    @ManyToOne
    val user: User,

    @Enumerated
    @JdbcType(PostgreSQLEnumJdbcType::class)
    val type: AccountType,

    val balance: BigDecimal,
    val createdAt: OffsetDateTime
)