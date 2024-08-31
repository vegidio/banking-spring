package io.vinicius.banking.shared.feat.transaction

import io.vinicius.banking.grpc.TransactionType as ProtoTransactionType

enum class TransactionType {
    Deposit,
    Withdrawal,
    Transfer,
    Payment;

    companion object
}

fun TransactionType.Companion.fromProto(proto: ProtoTransactionType) = when (proto) {
    ProtoTransactionType.DEPOSIT -> TransactionType.Deposit
    ProtoTransactionType.WITHDRAWAL -> TransactionType.Withdrawal
    ProtoTransactionType.TRANSFER -> TransactionType.Transfer
    ProtoTransactionType.PAYMENT -> TransactionType.Payment
    else -> throw IllegalArgumentException("Unknown transaction type: $proto")
}

fun TransactionType.toProto() = when (this) {
    TransactionType.Deposit -> ProtoTransactionType.DEPOSIT
    TransactionType.Withdrawal -> ProtoTransactionType.WITHDRAWAL
    TransactionType.Transfer -> ProtoTransactionType.TRANSFER
    TransactionType.Payment -> ProtoTransactionType.PAYMENT
}