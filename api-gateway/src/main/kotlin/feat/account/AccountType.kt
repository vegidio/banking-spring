package io.vinicius.banking.api.feat.account

import io.vinicius.banking.grpc.AccountType as GrpcAccountType

enum class AccountType {
    Checking,
    Savings
}

fun AccountType.toProto() = when (this) {
    AccountType.Checking -> GrpcAccountType.CHECKING
    AccountType.Savings -> GrpcAccountType.SAVINGS
}