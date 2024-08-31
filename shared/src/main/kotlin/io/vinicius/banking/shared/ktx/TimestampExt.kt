package io.vinicius.banking.shared.ktx

import com.google.protobuf.Timestamp
import java.time.LocalDateTime
import java.time.OffsetDateTime
import java.time.ZoneOffset

fun Timestamp.toOffsetDateTime(): OffsetDateTime {
    return LocalDateTime.ofEpochSecond(this.seconds, this.nanos, ZoneOffset.UTC).atOffset(ZoneOffset.UTC)
}