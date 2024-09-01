package io.vinicius.banking.fraud.feat.fraud

import io.github.oshai.kotlinlogging.KotlinLogging
import io.vinicius.banking.proto.Transaction
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class FraudService {
    private val logger = KotlinLogging.logger {}

    @KafkaListener(
        topics = ["transaction_created"],
        groupId = "fraud-detection",
        containerFactory = "transactionCreatedContainerFactory"
    )
    fun listenFraudDetection(transaction: Transaction) {
        logger.info { "Received transaction: $transaction" }
    }
}