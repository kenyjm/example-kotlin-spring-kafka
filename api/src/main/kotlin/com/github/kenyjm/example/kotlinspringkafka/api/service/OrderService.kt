package com.github.kenyjm.example.kotlinspringkafka.api.service

import com.github.kenyjm.example.kotlinspringkafka.share.exception.DataNotFoundException
import com.github.kenyjm.example.kotlinspringkafka.api.model.Order
import com.github.kenyjm.example.kotlinspringkafka.api.model.kafka.OrderAccepted
import com.github.kenyjm.example.kotlinspringkafka.api.repository.OrdersMapper
import com.github.kenyjm.example.kotlinspringkafka.api.repository.ProductsMapper
import com.github.kenyjm.example.kotlinspringkafka.api.repository.UsersMapper
import com.github.kenyjm.example.kotlinspringkafka.share.component.IdGenerator
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class OrderService(
    private val ordersMapper: OrdersMapper,
    private val usersMapper: UsersMapper,
    private val productsMapper: ProductsMapper,
    private val orderAcceptedKafkaTemplate: KafkaTemplate<String, OrderAccepted>,
    private val idGenerator: IdGenerator
) {

    fun accept(userId: String, productId: String): Order {

        val user = usersMapper.find(userId) ?: throw DataNotFoundException("User not found. userId=$userId")

        productsMapper.find(productId) ?: throw DataNotFoundException("Product not found. productId=$productId")

        val order = Order(
            orderId = idGenerator.createUniqueId(),
            userId = userId,
            productId = productId,
            acceptedAt = LocalDateTime.now()
        )
        ordersMapper.insert(order)

        val event = OrderAccepted(
            orderId = order.orderId,
            userId = order.userId,
            productId = order.productId,
            acceptedAt = order.acceptedAt,
            firstName = user.firstName,
            lastName = user.lastName,
            zipCode = user.zipCode,
            shipAddress = user.shipAddress
        )
        orderAcceptedKafkaTemplate.send("OrderAccepted", event)

        return order
    }
}