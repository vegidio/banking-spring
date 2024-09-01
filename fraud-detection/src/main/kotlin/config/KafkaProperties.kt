package io.vinicius.banking.fraud.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.stereotype.Component

@Component
@ConfigurationProperties(prefix = "spring.kafka")
class KafkaProperties {
    lateinit var bootstrapServers: String
    lateinit var schemaRegistryUrl: String
}