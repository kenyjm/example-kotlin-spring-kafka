package com.github.kenyjm.example.kotlinspringkafka.share.util

import java.nio.charset.Charset

object JisCharacterChecker {

    fun isUnderJisL2(str: String): Boolean {
        // JIS第二水準までをサポートしているiso-2022-jpとしてバイトに変換し再度文字列化する
        val reconverted = str
            .toByteArray(Charset.forName("iso-2022-jp"))
            .toString(Charset.forName("iso-2022-jp"))
        // 元の文字列と一致 = iso-2022-jpで正しく変換可能 = JIS第二水準以下
        return str == reconverted
    }
}