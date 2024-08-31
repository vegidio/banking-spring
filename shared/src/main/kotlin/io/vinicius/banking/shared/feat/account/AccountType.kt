package io.vinicius.banking.shared.feat.account

import io.vinicius.banking.grpc.AccountType as ProtoAccountType

enum class AccountType {
    Checking,
    Savings;

    companion object
}

fun AccountType.Companion.fromProto(proto: ProtoAccountType) = when (proto) {
    ProtoAccountType.CHECKING -> AccountType.Checking
    ProtoAccountType.SAVINGS -> AccountType.Savings
    else -> throw IllegalArgumentException("Unknown account type: $proto")
}

fun AccountType.toProto() = when (this) {
    AccountType.Checking -> ProtoAccountType.CHECKING
    AccountType.Savings -> ProtoAccountType.SAVINGS
}