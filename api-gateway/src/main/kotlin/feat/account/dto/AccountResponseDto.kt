package io.vinicius.banking.api.feat.account.dto

import io.vinicius.banking.grpc.AccountResponse
import io.vinicius.banking.shared.feat.account.AccountType
import io.vinicius.banking.shared.feat.account.fromProto
import java.math.BigDecimal

data class AccountResponseDto(
    val id: Int,
    val type: AccountType,
    val balance: BigDecimal
) {
    companion object {
        fun fromProto(proto: AccountResponse): AccountResponseDto {
            return AccountResponseDto(
                id = proto.id,
                type = AccountType.fromProto(proto.type),
                balance = BigDecimal(proto.balance)
            )
        }
    }
}