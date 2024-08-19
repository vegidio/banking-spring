package io.vinicius.banking.feat.transaction.dto

import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

enum class TransactionType {
    Deposit,
    Withdrawal,
    Transfer,
    Payment
}

data class TransactionCreateDto(
    @field:NotNull
    val accountId: Int,

    @field:NotNull
    val type: TransactionType,

    @field:Positive
    val amount: BigDecimal,

    val message: String
)