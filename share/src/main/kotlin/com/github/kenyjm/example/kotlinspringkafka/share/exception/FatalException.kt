package com.github.kenyjm.example.kotlinspringkafka.share.exception

class FatalException : RuntimeException {
    constructor(message: String) : super(message)
    constructor(message: String, cause: Throwable) : super(message, cause)
}