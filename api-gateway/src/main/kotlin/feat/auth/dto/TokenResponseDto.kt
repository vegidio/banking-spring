package io.vinicius.banking.feat.auth.dto

data class TokenResponseDto(
    val accessToken: String,
    val refreshToken: String
)