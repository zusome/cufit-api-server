package com.official.cufitapi.common.config

import com.official.cufitapi.common.config.resolver.AuthorizationArgumentResolver
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class MvcConfig(
    private val authorizationArgumentResolver: AuthorizationArgumentResolver
) : WebMvcConfigurer {

    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(authorizationArgumentResolver)
    }
}
