package com.github.kenyjm.example.kotlinspringkafka.api.aop

import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component
import org.springframework.stereotype.Controller
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RestController

@Aspect
@Component
class LoggingAspect {

    private val logger = LoggerFactory.getLogger(javaClass)

    @Around(
        "@within(org.springframework.web.bind.annotation.RestController)"
                + " || @within(org.springframework.stereotype.Controller)"
                + " || @within(org.springframework.stereotype.Service)"
    )
    fun methodInOut(pjp: ProceedingJoinPoint): Any? {

        val targetClass = pjp.target.javaClass

        val targetType = when {
            targetClass.isAnnotationPresent(Controller::class.java) -> "Controller"
            targetClass.isAnnotationPresent(RestController::class.java) -> "Controller"
            targetClass.isAnnotationPresent(Service::class.java) -> "Service"
            else -> return pjp.proceed()
        }

        val signature = getSignature(pjp)

        logger.info("[$targetType In] $signature")
        if (logger.isDebugEnabled) {
            logger.debug("[$targetType Args] ${pjp.args.joinToString()}")
        }

        val ret = try {
            pjp.proceed()
        } catch (e: Throwable) {
            logger.info("[$targetType Out(with exception)] $signature")
            if (logger.isDebugEnabled) {
                logger.debug("[$targetType Exception] ${e.javaClass}")
            }
            throw e
        }

        logger.info("[$targetType Out] $signature")
        if (logger.isDebugEnabled) {
            logger.debug("[$targetType Returns] $ret")
        }

        return ret
    }

    private fun getSignature(pjp: ProceedingJoinPoint): String {
        val clazz = pjp.target.javaClass.simpleName
        val method = pjp.signature.name
        val args = pjp.args.joinToString(", ") { if (it == null) "null" else it.javaClass.simpleName }
        return "$clazz#$method($args)"
    }
}