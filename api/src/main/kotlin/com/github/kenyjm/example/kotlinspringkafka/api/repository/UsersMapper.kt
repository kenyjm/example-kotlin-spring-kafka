package com.github.kenyjm.example.kotlinspringkafka.api.repository

import com.github.kenyjm.example.kotlinspringkafka.api.model.User
import org.apache.ibatis.annotations.Insert
import org.apache.ibatis.annotations.Mapper
import org.apache.ibatis.annotations.Select
import org.springframework.stereotype.Repository

@Repository
@Mapper
interface UsersMapper {

    @Insert("""
        INSERT INTO users(
            user_id,
            first_name,
            last_name,
            zip_code,
            ship_address,
            mail_address,
            registered_at
        )
        VALUES(
            #{userId},
            #{firstName},
            #{lastName},
            #{zipCode},
            #{shipAddress},
            #{mailAddress},
            #{registeredAt}
        )
    """
    )
    fun insert(user: User)

    @Select("""
        SELECT * FROM users
        WHERE user_id = #{userId}
    """
    )
    fun find(userId: String): User?

    @Select("""
        SELECT * FROM users
    """
    )
    fun selectAll(): List<User>
}