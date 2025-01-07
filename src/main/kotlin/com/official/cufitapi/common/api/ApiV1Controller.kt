package com.official.cufitapi.common.api

import org.springframework.stereotype.Component
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@Component
@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@RestController
@RequestMapping("/api/v1")
annotation class ApiV1Controller()
