package io.vinicius.banking.exception

import io.vinicius.banking.model.Error
import org.springframework.http.HttpStatus

open class HttpException(
    val status: HttpStatus,
    type: String,
    title: String? = null,
    detail: String? = null,
    instance: String? = null
) : RuntimeException() {
    val body = Error(status.value(), type, title, detail, instance)
}