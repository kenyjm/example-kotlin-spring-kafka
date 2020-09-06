package com.github.kenyjm.example.kotlinspringkafka.api.model

import java.time.LocalDateTime

data class Order(
    val orderId: String,
    val userId: String,
    val productId: String,
    val acceptedAt: LocalDateTime
)
