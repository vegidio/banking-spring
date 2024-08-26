package io.vinicius.banking.transactions.ktx

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt

val SecurityContext.subject: Int
    get() {
        val jwt = authentication.principal as Jwt
        return jwt.subject.toInt()
    }

