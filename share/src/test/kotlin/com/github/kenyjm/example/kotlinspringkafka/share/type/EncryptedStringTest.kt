package com.github.kenyjm.example.kotlinspringkafka.share.type

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.util.stream.IntStream
import kotlin.streams.toList

@Suppress("NonAsciiCharacters", "TestFunctionName")
class EncryptedStringTest {

    @Test
    fun test_インスタンスの同値が正しく判定されること() {
        val es1 = EncryptedString.fromString("あいうえお")
        val es2 = EncryptedString.fromString("あいうえお")
        val es3 = EncryptedString.fromString("ABC")

        assertThat(es1).isEqualTo(es2)
        assertThat(es1).isNotEqualTo(es3)

        assertThat(es1.byteArray).isEqualTo(es2.byteArray)
        assertThat(es1.byteArray).isNotEqualTo(es3.byteArray)

        assertThat(es1.base64).isEqualTo(es2.base64)
        assertThat(es1.base64).isNotEqualTo(es3.base64)
    }

    @Test
    fun test_インスタンス生成が正しく行われること() {
        val string = "あいうえお"
        val byteArray = EncryptedString.fromString(string).byteArray
        val base64 = EncryptedString.fromString(string).base64

        assertThat(EncryptedString.fromString(string)).isEqualTo(EncryptedString.fromByteArray(byteArray))
        assertThat(EncryptedString.fromString(string)).isEqualTo(EncryptedString.fromBase64(base64))

        assertThat(EncryptedString.fromString(string)).isSameAs(EncryptedString.fromByteArray(byteArray))
        assertThat(EncryptedString.fromString(string)).isSameAs(EncryptedString.fromBase64(base64))
    }

    @Test
    fun test_暗号化したものを復号して元に戻ること() {
        val string = "あいうえお"
        val byteArray = EncryptedString.fromString(string).byteArray
        val base64 = EncryptedString.fromString(string).base64

        assertThat(EncryptedString.fromString(string).string).isEqualTo(string)
        assertThat(EncryptedString.fromByteArray(byteArray).string).isEqualTo(string)
        assertThat(EncryptedString.fromBase64(base64).string).isEqualTo(string)
    }

    @Test
    fun test_並列に生成したインスタンスが全て同一であること() {
        val list = IntStream
            .range(1, 10000).boxed().parallel()
            .map { EncryptedString.fromString("Test") }
            .toList()

        list.forEach { assertThat(list.first()).isSameAs(it) }
    }
}