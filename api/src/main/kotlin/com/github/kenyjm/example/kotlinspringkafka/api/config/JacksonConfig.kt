package com.github.kenyjm.example.kotlinspringkafka.api.config

import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.databind.module.SimpleModule
import com.github.kenyjm.example.kotlinspringkafka.share.type.EncryptedString
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter

@Configuration
class JacksonConfig {

    @Bean
    fun mappingJackson2HttpMessageConverter(
        builder: Jackson2ObjectMapperBuilder
    ) = MappingJackson2HttpMessageConverter(
        builder
            .modulesToInstall(SimpleModule().addSerializer(EncryptedString.StringSerializer()))
            .featuresToEnable(SerializationFeature.INDENT_OUTPUT)
            .build()
    )
}