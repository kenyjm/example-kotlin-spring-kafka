package com.github.kenyjm.example.kotlinspringkafka.api.model.kafka

import com.github.kenyjm.example.kotlinspringkafka.share.type.EncryptedString
import java.time.LocalDateTime

class OrderAccepted(
    val orderId: String,
    val userId: String,
    val productId: String,
    val firstName: EncryptedString,
    val lastName: EncryptedString,
    val zipCode: EncryptedString,
    val shipAddress: EncryptedString,
    val acceptedAt: LocalDateTime
)