package io.vinicius.banking.shared.ktx

fun String.capitalize() = this.replaceFirstChar { it.uppercase() }