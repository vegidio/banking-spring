package io.vinicius.banking.api.exception

import org.springframework.http.HttpStatus

class BadRequestException(
    status: HttpStatus = HttpStatus.BAD_REQUEST,
    type: String = "BAD_REQUEST",
    title: String? = "Bad Request",
    detail: String? = null,
    instance: String? = null
) : HttpException(status, type, title, detail, instance)