package com.github.kenyjm.example.kotlinspringkafka.api.model

import com.github.kenyjm.example.kotlinspringkafka.share.type.EncryptedString
import java.time.LocalDateTime

data class User(
    val userId: String,
    val firstName: EncryptedString,
    val lastName: EncryptedString,
    val zipCode: EncryptedString,
    val shipAddress: EncryptedString,
    val mailAddress: EncryptedString,
    val registeredAt: LocalDateTime,
    val deletedAt: LocalDateTime? = null
)