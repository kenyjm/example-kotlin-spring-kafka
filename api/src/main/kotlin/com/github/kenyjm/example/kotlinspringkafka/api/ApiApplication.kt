package com.github.kenyjm.example.kotlinspringkafka.api

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication(
    scanBasePackages = [
        "com.github.kenyjm.example.kotlinspringkafka.api",
        "com.github.kenyjm.example.kotlinspringkafka.share"
    ]
)
class ApiApplication

fun main(args: Array<String>) {
    runApplication<ApiApplication>(*args)
}
