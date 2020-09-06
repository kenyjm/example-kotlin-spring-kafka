package com.github.kenyjm.example.kotlinspringkafka.api.controller

import com.github.kenyjm.example.kotlinspringkafka.api.model.Order
import com.github.kenyjm.example.kotlinspringkafka.api.service.OrderService
import com.github.kenyjm.example.kotlinspringkafka.share.type.EncryptedString
import com.github.kenyjm.example.kotlinspringkafka.share.type.encrypt
import org.slf4j.LoggerFactory
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.constraints.NotEmpty

@RestController
@RequestMapping("order")
class OrderController(
    private val orderService: OrderService
) {

    private val logger = LoggerFactory.getLogger(javaClass)

    @PostMapping("accept")
    fun accept(
        @RequestBody @Validated request: OrderAcceptRequest
    ): OrderAcceptResponse {
        val order = orderService.accept(request.userId, request.productId)
        return OrderAcceptResponse(
            resultCode = ResultCode.SUCCESS,
            message = "Order accepted.",
            order = order
        )
    }

    data class OrderAcceptRequest(

        @field:NotEmpty
        val userId: String = "",

        @field:NotEmpty
        val productId: String = ""
    )

    data class OrderAcceptResponse(
        val resultCode: ResultCode,
        val message: String,
        val order: Order
    )
}