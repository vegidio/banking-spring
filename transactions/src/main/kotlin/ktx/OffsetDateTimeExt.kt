package io.vinicius.banking.ktx

import com.google.protobuf.Timestamp
import java.time.OffsetDateTime

fun OffsetDateTime.toGrpc(): Timestamp {
    return Timestamp.newBuilder()
        .setSeconds(this.toEpochSecond())
        .setNanos(this.nano)
        .build()
}