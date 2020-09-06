package com.github.kenyjm.example.kotlinspringkafka.share.validation

import com.github.kenyjm.example.kotlinspringkafka.share.util.JisCharacterChecker
import javax.validation.Constraint
import javax.validation.ConstraintValidator
import javax.validation.ConstraintValidatorContext
import javax.validation.Payload
import kotlin.reflect.KClass

@Target(AnnotationTarget.FIELD)
@Retention(AnnotationRetention.RUNTIME)
@MustBeDocumented
@Constraint(validatedBy = [UnderJisL2Validator::class])
annotation class UnderJisL2(
    val message: String = "JIS第二水準外の文字が含まれています",
    val groups: Array<KClass<out Any>> = [],
    val payload: Array<KClass<out Payload>> = []
)

class UnderJisL2Validator : ConstraintValidator<UnderJisL2, String> {
    override fun isValid(value: String, context: ConstraintValidatorContext): Boolean {
        return JisCharacterChecker.isUnderJisL2(value)
    }
}