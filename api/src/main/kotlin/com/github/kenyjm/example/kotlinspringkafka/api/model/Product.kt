package com.github.kenyjm.example.kotlinspringkafka.api.model

import java.time.LocalDateTime

data class Product(
    val productId: String,
    val productName: String,
    val registeredAt: LocalDateTime
)