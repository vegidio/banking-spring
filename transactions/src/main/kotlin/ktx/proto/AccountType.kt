package io.vinicius.banking.transactions.ktx.grpc

import io.vinicius.banking.transactions.feat.account.AccountType
import io.vinicius.banking.grpc.AccountType as ProtoAccountType

fun ProtoAccountType.toModel() = when (this) {
    ProtoAccountType.CHECKING -> AccountType.Checking
    ProtoAccountType.SAVINGS -> AccountType.Savings
    else -> throw IllegalArgumentException("Invalid account type")
}