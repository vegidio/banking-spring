package io.vinicius.banking.ktx

import org.springframework.security.core.context.SecurityContext
import org.springframework.security.oauth2.jwt.Jwt

val SecurityContext.jwt: String
    get() {
        val jwt = authentication.principal as Jwt
        return jwt.tokenValue
    }

