package com.official.cufitapi.common.annotation

import org.springframework.core.Ordered
import org.springframework.core.annotation.Order

@MustBeDocumented
@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
@Order(Ordered.HIGHEST_PRECEDENCE)
annotation class Authorization(
    vararg val value: AuthorizationType
)
