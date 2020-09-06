package com.github.kenyjm.example.kotlinspringkafka.api.service

import com.github.kenyjm.example.kotlinspringkafka.share.exception.DataAlreadyExistsException
import com.github.kenyjm.example.kotlinspringkafka.api.model.User
import com.github.kenyjm.example.kotlinspringkafka.api.repository.UsersMapper
import com.github.kenyjm.example.kotlinspringkafka.share.type.encrypt
import org.springframework.stereotype.Service
import java.time.LocalDateTime

@Service
class UserService(
    private val usersMapper: UsersMapper
) {

    fun register(
        userId: String,
        firstName: String,
        lastName: String,
        zipCode: String,
        shipAddress: String,
        mailAddress: String
    ): User {

        if (usersMapper.find(userId) != null) {
            throw DataAlreadyExistsException("User already exists. userId=${userId}")
        }

        val user = User(
            userId = userId,
            firstName = firstName.encrypt(),
            lastName = lastName.encrypt(),
            zipCode = zipCode.encrypt(),
            shipAddress = shipAddress.encrypt(),
            mailAddress = mailAddress.encrypt(),
            registeredAt = LocalDateTime.now()
        )
        usersMapper.insert(user)
        return user
    }

    fun selectAll() = usersMapper.selectAll()

}