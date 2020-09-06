package com.github.kenyjm.example.kotlinspringkafka.share.type

import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.ser.std.StdSerializer
import java.util.Base64
import java.util.concurrent.ConcurrentHashMap
import javax.crypto.Cipher
import javax.crypto.spec.SecretKeySpec

class EncryptedString private constructor(
    val string: String
) : Comparable<EncryptedString>, CharSequence by string {

    companion object {
        private const val algorithm = "AES"
        private const val transformation = "AES/ECB/PKCS5Padding"
        private val encCipher = Cipher.getInstance(transformation)
        private val decCipher = Cipher.getInstance(transformation)
        private val base64Encoder = Base64.getEncoder()
        private val base64Decoder = Base64.getDecoder()
        private val instanceMap = ConcurrentHashMap<String, EncryptedString>()

        init {
            val keyString = System.getenv("ENCRYPT_KEY") ?: "default secret"
            // ?: throw InitializeException("EncryptedString initialize error. Environment variable ENCRYPT_KEY is not specified.")

            val key = SecretKeySpec(keyString.toByteArray(Charsets.UTF_8).copyOf(16), algorithm)
            encCipher.init(Cipher.ENCRYPT_MODE, key)
            decCipher.init(Cipher.DECRYPT_MODE, key)
        }

        fun fromString(string: String) = instanceMap.computeIfAbsent(string) { EncryptedString(it) }
        fun fromByteArray(byteArray: ByteArray) = fromString(String(decCipher.doFinal(byteArray)))
        fun fromBase64(base64Str: String) = fromByteArray(base64Decoder.decode(base64Str))
    }

    override fun compareTo(other: EncryptedString) = string.compareTo(other.string)
    override fun toString() = "****"
    override fun equals(other: Any?) = other is EncryptedString && string == other.string
    override fun hashCode() = string.hashCode()

    val byteArray get() = encCipher.doFinal(string.toByteArray())!!
    val base64 get() = base64Encoder.encodeToString(byteArray)!!

    class StringSerializer : StdSerializer<EncryptedString>(EncryptedString::class.java) {
        override fun serialize(value: EncryptedString, gen: JsonGenerator, provider: SerializerProvider) {
            gen.writeString(value.string)
        }
    }

    class Base64Serializer : StdSerializer<EncryptedString>(EncryptedString::class.java) {
        override fun serialize(value: EncryptedString, gen: JsonGenerator, provider: SerializerProvider) {
            gen.writeString(value.base64)
        }
    }
}

fun String.encrypt() = EncryptedString.fromString(this)
