package com.github.kenyjm.example.kotlinspringkafka.share.validation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.constraints.Pattern
import javax.validation.constraints.Size

@Suppress("NonAsciiCharacters", "TestFunctionName")
class ValidMailAddressTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun test_validationOK() {
        val result = validator.validate(ForTest("test@example.com"))
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun test_validationNG_DefaultMessage() {
        val result = validator.validate(ForTest("invalid-address"))
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().constraintDescriptor.annotation.annotationClass).isEqualTo(ValidMailAddress::class)
        assertThat(result.first().message).isEqualTo("メールアドレスの書式が不正です")
    }

    @Test
    fun test_validationNG_メッセージ指定() {
        val result = validator.validate(ForTest2("invalid-address"))
        assertThat(result.size).isEqualTo(1)
        //assertThat(result.first().constraintDescriptor.annotation.annotationClass).isEqualTo(ValidMailAddress::class)
        assertThat(result.first().message).isEqualTo("不正なアドレス")
    }

    data class ForTest(
        @field:ValidMailAddress
        val email: String
    )

    data class ForTest2(
        @field:ValidMailAddress(message = "不正なアドレス")
        val email: String
    )
}