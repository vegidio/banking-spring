package io.vinicius.banking.ktx

fun String.capitalize() = this.replaceFirstChar { it.uppercase() }