package com.github.kenyjm.example.kotlinspringkafka.api.repository

import com.github.kenyjm.example.kotlinspringkafka.api.model.Order
import com.github.kenyjm.example.kotlinspringkafka.api.model.Product
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface OrdersMapper {

    @Insert("""
        INSERT INTO orders(order_id, user_id, product_id, accepted_at)
        VALUES(#{orderId}, #{userId}, #{productId}, #{acceptedAt})
    """)
    fun insert(order: Order)

    @Select("""
        SELECT * FROM orders
        WHERE order_id = #{orderId}
    """)
    fun find(orderId: String): Order?
}