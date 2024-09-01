package io.vinicius.banking.fraud

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class FraudApplication

fun main(args: Array<String>) {
    runApplication<FraudApplication>(*args)
    logger.info { "Fraud Detection started" }
}