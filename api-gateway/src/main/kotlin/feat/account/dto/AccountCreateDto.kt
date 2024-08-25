package io.vinicius.banking.feat.account.dto

import io.vinicius.banking.feat.account.AccountType
import jakarta.validation.constraints.NotNull

data class AccountCreateDto(
    @field:NotNull
    val type: AccountType
)