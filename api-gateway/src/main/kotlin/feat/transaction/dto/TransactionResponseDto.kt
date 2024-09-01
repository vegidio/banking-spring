package io.vinicius.banking.api.feat.transaction.dto

import io.vinicius.banking.shared.feat.transaction.TransactionType
import io.vinicius.banking.shared.feat.transaction.fromProto
import io.vinicius.banking.shared.ktx.toOffsetDateTime
import java.math.BigDecimal
import java.time.OffsetDateTime
import io.vinicius.banking.proto.Transaction as ProtoTransaction

data class TransactionResponseDto(
    val id: Int,
    val accountId: Int,
    val type: TransactionType,
    val amount: BigDecimal,
    val destinationAccountId: Int?,
    val message: String?,
    val createdAt: OffsetDateTime
) {
    companion object {
        fun fromProto(proto: ProtoTransaction): TransactionResponseDto {
            return TransactionResponseDto(
                id = proto.id,
                accountId = proto.accountId,
                type = TransactionType.fromProto(proto.type),
                amount = BigDecimal(proto.amount),
                destinationAccountId = proto.destinationAccountId,
                message = proto.message,
                createdAt = proto.createdAt.toOffsetDateTime()
            )
        }
    }
}
