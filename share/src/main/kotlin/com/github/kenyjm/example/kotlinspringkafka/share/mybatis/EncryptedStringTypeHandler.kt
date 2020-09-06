package com.github.kenyjm.example.kotlinspringkafka.share.mybatis

import com.github.kenyjm.example.kotlinspringkafka.share.type.EncryptedString
import org.apache.ibatis.type.BaseTypeHandler
import org.apache.ibatis.type.JdbcType
import java.sql.CallableStatement
import java.sql.PreparedStatement
import java.sql.ResultSet

class EncryptedStringTypeHandler : BaseTypeHandler<EncryptedString>() {

    override fun setNonNullParameter(
        ps: PreparedStatement, i: Int, parameter: EncryptedString, jdbcType: JdbcType?
    ) = ps.setString(i, parameter.base64)

    override fun getNullableResult(
        rs: ResultSet, columnName: String
    ) = EncryptedString.fromBase64(rs.getString(columnName))


    override fun getNullableResult(
        rs: ResultSet, columnIndex: Int
    ) = EncryptedString.fromBase64(rs.getString(columnIndex))

    override fun getNullableResult(
        cs: CallableStatement, columnIndex: Int
    ) = EncryptedString.fromBase64(cs.getString(columnIndex))
}