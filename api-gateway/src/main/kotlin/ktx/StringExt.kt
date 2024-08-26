package io.vinicius.banking.api.ktx

fun String.capitalize() = this.replaceFirstChar { it.uppercase() }