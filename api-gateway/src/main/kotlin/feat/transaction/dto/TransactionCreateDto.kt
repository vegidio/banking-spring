package io.vinicius.banking.api.feat.transaction.dto

import io.vinicius.banking.api.feat.transaction.TransactionType
import jakarta.validation.constraints.Min
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Positive
import java.math.BigDecimal

data class TransactionCreateDto(
    @field:Min(1)
    val accountId: Int,

    @field:NotNull
    val type: TransactionType,

    @field:Positive
    val amount: BigDecimal,

    val message: String?
)