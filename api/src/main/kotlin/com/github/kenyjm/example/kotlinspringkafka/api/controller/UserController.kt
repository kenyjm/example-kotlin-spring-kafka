package com.github.kenyjm.example.kotlinspringkafka.api.controller

import com.github.kenyjm.example.kotlinspringkafka.api.model.User
import com.github.kenyjm.example.kotlinspringkafka.api.service.UserService
import com.github.kenyjm.example.kotlinspringkafka.share.validation.UnderJisL2
import com.github.kenyjm.example.kotlinspringkafka.share.validation.ValidMailAddress
import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Pattern

@RestController
@RequestMapping("user")
class UserController(
    private val userService: UserService
) {

    @PostMapping("register")
    fun add(
        @RequestBody @Validated request: UserAddRequest
    ): UserAddResponse {
        val user = userService.register(
            request.userId,
            request.firstName,
            request.lastName,
            request.zipCode,
            request.shipAddress,
            request.mailAddress
        )
        return UserAddResponse(
            resultCode = ResultCode.SUCCESS,
            message = "User registered.",
            user = user
        )
    }

    @GetMapping("list")
    fun list(): UserListResponse {
        return UserListResponse(
            resultCode = ResultCode.SUCCESS,
            users = userService.selectAll()
        )
    }

    data class UserAddRequest(

        @field:NotEmpty
        @field:Pattern(regexp = "[a-z0-9_]{8,}", message = "ユーザIDの書式が不正です")
        val userId: String = "",

        @field:NotEmpty
        @field:UnderJisL2
        val firstName: String = "",

        @field:NotEmpty
        @field:UnderJisL2
        val lastName: String = "",

        @field:NotEmpty
        @field:Pattern(regexp = "[0-9]{7}", message = "郵便番号の書式が不正です")
        val zipCode: String = "",

        @field:NotEmpty
        @field:UnderJisL2
        val shipAddress: String = "",

        @field:ValidMailAddress
        val mailAddress: String = ""
    )

    data class UserAddResponse(
        val resultCode: ResultCode,
        val message: String,
        val user: User
    )

    data class UserListResponse(
        val resultCode: ResultCode,
        val users: List<User>
    )
}