package io.vinicius.banking.api.feat.account.dto

import io.vinicius.banking.api.feat.account.AccountType
import jakarta.validation.constraints.NotNull

data class AccountCreateDto(
    @field:NotNull
    val type: AccountType
)