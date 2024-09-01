package io.vinicius.banking.fraud.config

import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig
import io.vinicius.banking.grpc.TransactionResponse
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

@Configuration
@EnableKafka
class KafkaConsumerConfig {

    /**
     * If there's only one container factory in the application, then we can name this `kafkaListenerContainerFactory`
     * and remove the `containerFactory` attribute from the `@KafkaListener` annotation in the Service class.
     */
    @Bean
    fun transactionCreatedContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, TransactionResponse> {
        return ConcurrentKafkaListenerContainerFactory<String, TransactionResponse>().apply {
            consumerFactory = DefaultKafkaConsumerFactory(
                mapOf(
                    ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to "http://localhost:19092",
                    KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG to "http://localhost:18081",
                    KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE to TransactionResponse::class.qualifiedName,
                ),
                ErrorHandlingDeserializer(StringDeserializer()),
                ErrorHandlingDeserializer(KafkaProtobufDeserializer())
            )
        }
    }
}