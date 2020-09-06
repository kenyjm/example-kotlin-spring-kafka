package com.github.kenyjm.example.kotlinspringkafka.share.component

import org.springframework.stereotype.Component
import java.util.UUID

@Component
class IdGenerator {
    fun createUniqueId() = UUID.randomUUID().toString()
}