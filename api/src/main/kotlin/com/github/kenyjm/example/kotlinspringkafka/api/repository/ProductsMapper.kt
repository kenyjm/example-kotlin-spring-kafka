package com.github.kenyjm.example.kotlinspringkafka.api.repository

import com.github.kenyjm.example.kotlinspringkafka.api.model.Product
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface ProductsMapper {

    @Select("""
        SELECT * FROM products
        WHERE product_id = #{productId}
    """)
    fun find(productId: String): Product?
}