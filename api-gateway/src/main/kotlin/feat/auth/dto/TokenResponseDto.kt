package io.vinicius.banking.api.feat.auth.dto

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)