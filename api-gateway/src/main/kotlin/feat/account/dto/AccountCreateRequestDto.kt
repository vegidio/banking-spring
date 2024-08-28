package io.vinicius.banking.api.feat.account.dto

import io.vinicius.banking.shared.feat.account.AccountType
import jakarta.validation.constraints.NotNull

data class AccountCreateRequestDto(
    @field:NotNull
    val type: AccountType
)