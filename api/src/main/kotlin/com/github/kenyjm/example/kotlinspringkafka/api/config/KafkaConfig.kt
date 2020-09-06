package com.github.kenyjm.example.kotlinspringkafka.api.config

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.kenyjm.example.kotlinspringkafka.api.model.kafka.OrderAccepted
import com.github.kenyjm.example.kotlinspringkafka.share.type.EncryptedString
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.serializer.JsonSerializer

@Configuration
class KafkaConfig(
    @Value("\${spring.kafka.bootstrap-servers}")
    val bootstrapServers: String
) {

    @Bean
    fun kafkaObjectMapper(
        builder: Jackson2ObjectMapperBuilder
    ): ObjectMapper = builder
        .modulesToInstall(SimpleModule().addSerializer(EncryptedString.Base64Serializer()))
        .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
        .build()

    @Bean
    fun producerConfigs(): Map<String, Any> = mapOf(
        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG to bootstrapServers
    )

    @Bean
    fun orderAcceptedProducerFactory(
        kafkaObjectMapper: ObjectMapper
    ) = DefaultKafkaProducerFactory<String, OrderAccepted>(producerConfigs()).apply {
        setKeySerializer(StringSerializer())
        setValueSerializer(JsonSerializer(kafkaObjectMapper))
    }

    @Bean
    fun orderAcceptedKafkaTemplate(
        orderAcceptedProducerFactory: ProducerFactory<String, OrderAccepted>
    ) = KafkaTemplate(orderAcceptedProducerFactory)
}