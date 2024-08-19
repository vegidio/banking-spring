package io.vinicius.banking

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class TransactionsApplication

fun main(args: Array<String>) {
    val context = runApplication<TransactionsApplication>(*args)
    val port = context.environment.getProperty("grpc.server.port")

    logger.info { "Transactions started on gRPC port :$port" }
}