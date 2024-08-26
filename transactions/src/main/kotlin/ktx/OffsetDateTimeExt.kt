package io.vinicius.banking.transactions.ktx

import com.google.protobuf.Timestamp
import java.time.OffsetDateTime

fun OffsetDateTime.toProto(): Timestamp {
    return Timestamp.newBuilder()
        .setSeconds(this.toEpochSecond())
        .setNanos(this.nano)
        .build()
}