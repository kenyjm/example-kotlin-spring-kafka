package com.github.kenyjm.example.kotlinspringkafka.share.validation

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import javax.validation.Validation
import javax.validation.constraints.Size

@Suppress("TestFunctionName", "NonAsciiCharacters")
class UnderJisL2Test {

    private val validator = Validation.buildDefaultValidatorFactory().validator

    @Test
    fun test_validationOK() {
        // 檜はJIS第二水準
        val result = validator.validate(ForTest("檜山"))
        assertThat(result.size).isEqualTo(0)
    }

    @Test
    fun test_validationNG_DefaultMessage() {
        // 髙はJIS第二水準外(IBM拡張漢字)
        val result = validator.validate(ForTest("髙橋"))
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().constraintDescriptor.annotation.annotationClass).isEqualTo(UnderJisL2::class)
        assertThat(result.first().message).isEqualTo("JIS第二水準外の文字が含まれています")
    }

    @Test
    fun test_validationNG_メッセージ指定() {
        val result = validator.validate(ForTest2("髙橋"))
        assertThat(result.size).isEqualTo(1)
        assertThat(result.first().constraintDescriptor.annotation.annotationClass).isEqualTo(UnderJisL2::class)
        assertThat(result.first().message).isEqualTo("名前に使用できない漢字が含まれています")
    }

    @Test
    fun test_validationNG_複数違反() {
        val result = validator.validate(ForTest3("髙橋"))
        assertThat(result.size).isEqualTo(2)
        assertThat(result.map { it.constraintDescriptor.annotation.annotationClass }.toSet())
            .isEqualTo(setOf(UnderJisL2::class, Size::class))
    }

    data class ForTest(
        @field:UnderJisL2
        val name: String
    )

    data class ForTest2(
        @field:UnderJisL2(message = "名前に使用できない漢字が含まれています")
        val name: String
    )

    data class ForTest3(
        @field:UnderJisL2
        @field:Size(min = 5)
        val name: String
    )
}