package io.vinicius.banking.ktx.grpc

import io.vinicius.banking.feat.account.AccountType
import io.vinicius.banking.grpc.AccountType as GrpcAccountType

fun GrpcAccountType.toModel() = when (this) {
    GrpcAccountType.CHECKING -> AccountType.Checking
    GrpcAccountType.SAVINGS -> AccountType.Savings
    else -> throw IllegalArgumentException("Invalid account type")
}