package com.github.kenyjm.example.kotlinspringkafka.api.controller

import com.github.kenyjm.example.kotlinspringkafka.share.exception.DataAlreadyExistsException
import com.github.kenyjm.example.kotlinspringkafka.share.exception.DataNotFoundException
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.web.HttpMediaTypeNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

@ControllerAdvice
class ExceptionHandler {

    private val logger = LoggerFactory.getLogger(javaClass)

    @ExceptionHandler(DataNotFoundException::class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    fun handle(e: DataNotFoundException): ErrorResponse {
        val message = e.message ?: "Data not found."
        logger.warn(message)
        return ErrorResponse(ResultCode.FAILED, message)
    }

    @ExceptionHandler(MethodArgumentNotValidException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handle(e: MethodArgumentNotValidException): ErrorResponse {
        val message = "Validation error."
        val signature = "%s#%s(%s)".format(
            e.parameter.executable.declaringClass.simpleName,
            e.parameter.executable.name,
            e.parameter.executable.parameters.joinToString(", ") { it.type.simpleName }
        )
        logger.warn("$message at $signature")
        if (logger.isDebugEnabled) {
            val details = e.bindingResult.fieldErrors.joinToString(", ") { "${it.field}: ${it.defaultMessage}" }
            logger.debug("$message details { $details }")
        }
        return ErrorResponse(ResultCode.FAILED, message)
    }

    @ExceptionHandler(HttpMessageNotReadableException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handle(e: HttpMessageNotReadableException): ErrorResponse {
        val message = "Request format error."
        logger.warn("$message : ${e.message}")
        return ErrorResponse(ResultCode.FAILED, message)
    }

    @ExceptionHandler(HttpMediaTypeNotSupportedException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handle(e: HttpMediaTypeNotSupportedException): ErrorResponse {
        val message = e.message ?: "Media type not supported."
        logger.warn(message)
        return ErrorResponse(ResultCode.FAILED, message)
    }

    @ExceptionHandler(DataAlreadyExistsException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    fun handle(e: DataAlreadyExistsException): ErrorResponse {
        val message = e.message ?: "Data already exists."
        logger.warn(message)
        return ErrorResponse(ResultCode.FAILED, message)
    }

    @ExceptionHandler(Throwable::class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ResponseBody
    fun handle(e: Throwable): ErrorResponse {
        val message = "Server error."
        logger.error(message, e)
        return ErrorResponse(ResultCode.FAILED, message)
    }

    data class ErrorResponse(
        val resultCode: ResultCode,
        val message: String
    )
}