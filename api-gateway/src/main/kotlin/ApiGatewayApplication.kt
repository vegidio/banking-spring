package io.vinicius.banking

import io.github.oshai.kotlinlogging.KotlinLogging
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

private val logger = KotlinLogging.logger {}

@SpringBootApplication
class ApiGatewayApplication

fun main(args: Array<String>) {
    val context = runApplication<ApiGatewayApplication>(*args)
    val port = context.environment.getProperty("server.port")

    logger.info { "API Gateway started on port :$port" }
}