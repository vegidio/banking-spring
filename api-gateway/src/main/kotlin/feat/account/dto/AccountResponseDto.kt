package io.vinicius.banking.api.feat.account.dto

import io.vinicius.banking.shared.feat.account.AccountType
import io.vinicius.banking.shared.feat.account.fromProto
import io.vinicius.banking.shared.ktx.toOffsetDateTime
import java.math.BigDecimal
import java.time.OffsetDateTime
import io.vinicius.banking.proto.Account as ProtoAccount

data class AccountResponseDto(
    val id: Int,
    val type: AccountType,
    val balance: BigDecimal,
    val createdAt: OffsetDateTime
) {
    companion object {
        fun fromProto(proto: ProtoAccount): AccountResponseDto {
            return AccountResponseDto(
                id = proto.id,
                type = AccountType.fromProto(proto.type),
                balance = BigDecimal(proto.balance),
                createdAt = proto.createdAt.toOffsetDateTime()
            )
        }
    }
}