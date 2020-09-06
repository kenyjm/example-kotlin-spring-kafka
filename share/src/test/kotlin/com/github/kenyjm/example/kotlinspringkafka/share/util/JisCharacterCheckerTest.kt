package com.github.kenyjm.example.kotlinspringkafka.share.util

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import kotlin.streams.toList

@Suppress("NonAsciiCharacters", "TestFunctionName", "SpellCheckingInspection")
class JisCharacterCheckerTest {

    @Test
    fun test_isUnderJisL2_isTrue_半角英数記号() {
        assertThat(JisCharacterChecker.isUnderJisL2("abcdefghijklmnopqrstuvwxyz")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ABCDEFGHIJKLMNOPQRSTUVWXYZ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("0123456789")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("!\"#$%&'()-=^~¥|@`[{;+:*]},<.>/?_\\")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isTrue_全角英数記号() {
        assertThat(JisCharacterChecker.isUnderJisL2("ａｂｃｄｅｆｇｈｉｊｋｌｍｎｏｐｑｒｓｔｕｖｗｘｙｚ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ＡＢＣＤＥＦＧＨＩＪＫＬＭＮＯＰＱＲＳＴＵＶＷＸＹＺ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("０１２３４５６７８９）")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("！”＃＄％＆’（）＝＾￥｜＠｀［｛；＋：＊］｝，＜．＞／？＿＼")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isTrue_ひらがな() {
        assertThat(JisCharacterChecker.isUnderJisL2("あいうえおかきくけこさしすせそたちつてとなにぬねの")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("はひふへほまみむめもやゆよらりるれろわをん")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("がぎぐげごさじずぜぞだぢづでどばびぶべぼ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("はひぷぺぽ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ぁぃぅぇぉっゃゅょゎ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ゐゑ")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isTrue_全角カタカナ() {
        assertThat(JisCharacterChecker.isUnderJisL2("アイウエオカキクケコサシスセソタチツテトナニヌネノ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ハヒフヘホマミムメモヤユヨラリルレロワヲン")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ガギグゲゴザジズゼゾダヂヅデドバビブベボ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("パピプペポ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ァィゥェォッャュョヮヵヶ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ヰヱ")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isTrue_半角カタカナ() {
        assertThat(JisCharacterChecker.isUnderJisL2("ｱｲｳｴｵｶｷｸｹｺｻｼｽｾｿﾀﾁﾂﾃﾄﾅﾆﾇﾈﾉ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ﾊﾋﾌﾍﾎﾏﾐﾑﾒﾓﾔﾕﾖﾗﾘﾙﾚﾛﾜｦﾝ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ｶﾞｷﾞｸﾞｹﾞｺﾞｻﾞｼﾞｽﾞｾﾞｿﾞﾀﾞﾁﾞﾂﾞﾃﾞﾄﾞﾊﾞﾋﾞﾌﾞﾍﾞﾎﾞ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ﾊﾟﾋﾟﾌﾟﾍﾟﾎﾟ")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("ｧｨｩｪｫｯｬｭｮ")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isTrue_JIS第一水準() {
        assertThat(JisCharacterChecker.isUnderJisL2("亜唖娃阿哀愛挨姶逢")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("亘鰐詫藁蕨椀湾碗腕")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isTrue_JIS第二水準() {
        assertThat(JisCharacterChecker.isUnderJisL2("弌丐丕个丱丶丼丿乂")).isTrue()
        assertThat(JisCharacterChecker.isUnderJisL2("堯槇遙瑤凜熙")).isTrue()
    }

    @Test
    fun test_isUnderJisL2_isFalse_JIS第三水準() {
        "俱㐂丨丯丰亍仡份仿".toSingleStringList().forEach {
            assertThat(JisCharacterChecker.isUnderJisL2(it)).isFalse()
        }
        "龐龔龗龢姸屛幷瘦繫".toSingleStringList().forEach {
            assertThat(JisCharacterChecker.isUnderJisL2(it)).isFalse()
        }
    }

    @Test
    fun test_isUnderJisL2_isFalse_JIS第四水準() {
        "丂丏丒丩丫丮乀乇么".toSingleStringList().forEach {
            assertThat(JisCharacterChecker.isUnderJisL2(it)).isFalse()
        }
        "齆齓齕齘齝齩齭齰齵".toSingleStringList().forEach {
            assertThat(JisCharacterChecker.isUnderJisL2(it)).isFalse()
        }
    }

    @Test
    fun test_isUnderJisL2_isFalse_IBM拡張文字() {
        "髙閒塚德﨑彅弴燁珉鄧".peek().toSingleStringList().forEach {
            assertThat(JisCharacterChecker.isUnderJisL2(it)).isFalse()
        }
    }

    @Test
    fun test_isUnderJisL2_isFalse_emoji() {
        "\uD83D\uDE00\uD83D\uDCA2\uD83C\uDF63\uD83C\uDF7A".peek().toSingleStringList().forEach {
            assertThat(JisCharacterChecker.isUnderJisL2(it)).isFalse()
        }
    }

    @Test
    fun test_isUnderJisL2_isFalse_混合文字列() {
        val str = "髙村薫"
        str.peek()
        assertThat(JisCharacterChecker.isUnderJisL2(str)).isFalse()
    }

    // サロゲートペアを考慮して1文字ずつのStringに分割する
    private fun String.toSingleStringList(): List<String> {
        return this.codePoints().toList().map { String(Character.toChars(it)) }
    }

    // デバッグ用: 1文字ごとにJIS第二水準内外かを出力する
    private fun String.peek(): String {
        this.toSingleStringList().forEach {
            if (JisCharacterChecker.isUnderJisL2(it))
                println("'$it' is under JIS Level2")
            else
                println("'$it' is not under JIS Level2")
        }
        return this
    }
}