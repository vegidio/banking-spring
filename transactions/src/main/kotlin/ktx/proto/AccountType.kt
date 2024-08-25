package io.vinicius.banking.ktx.grpc

import io.vinicius.banking.feat.account.AccountType
import io.vinicius.banking.grpc.AccountType as ProtoAccountType

fun ProtoAccountType.toModel() = when (this) {
    ProtoAccountType.CHECKING -> AccountType.Checking
    ProtoAccountType.SAVINGS -> AccountType.Savings
    else -> throw IllegalArgumentException("Invalid account type")
}