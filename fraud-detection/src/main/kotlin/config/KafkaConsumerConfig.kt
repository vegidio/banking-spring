package io.vinicius.banking.fraud.config

import com.google.protobuf.Message
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializer
import io.confluent.kafka.serializers.protobuf.KafkaProtobufDeserializerConfig
import io.vinicius.banking.proto.Transaction
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer

@EnableKafka
@Configuration
class KafkaConsumerConfig(
    private val kafkaProperties: KafkaProperties
) {

    /**
     * If there's only one container factory in the application, then we can name this `kafkaListenerContainerFactory`
     * and remove the `containerFactory` attribute from the `@KafkaListener` annotation in the Service class.
     */
    @Bean
    fun transactionCreatedContainerFactory(): ConcurrentKafkaListenerContainerFactory<String, Transaction> {
        return ConcurrentKafkaListenerContainerFactory<String, Transaction>().apply {
            consumerFactory = createConsumerFactory(Transaction::class.java)
        }
    }

    // region - Private methods

    fun <T: Message> createConsumerFactory(valueClass: Class<T>): DefaultKafkaConsumerFactory<String, T> {
        return DefaultKafkaConsumerFactory(
            mapOf(
                ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG to kafkaProperties.bootstrapServers,
                KafkaProtobufDeserializerConfig.SCHEMA_REGISTRY_URL_CONFIG to kafkaProperties.schemaRegistryUrl,
                KafkaProtobufDeserializerConfig.SPECIFIC_PROTOBUF_VALUE_TYPE to valueClass.name
            ),
            ErrorHandlingDeserializer(StringDeserializer()),
            ErrorHandlingDeserializer(KafkaProtobufDeserializer())
        )
    }

    // endregion
}