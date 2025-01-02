package com.official.cufitapi.common.config.resolver

import com.official.cufitapi.common.annotation.Authorization
import com.official.cufitapi.common.annotation.AuthorizationUser
import com.official.cufitapi.domain.auth.application.AuthorizationTokenParsingUseCase
import com.official.cufitapi.domain.auth.application.MemberFindUseCase
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpHeaders
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer

@Component
class AuthorizationArgumentResolver(
    private val authorizationTokenParsingUseCase: AuthorizationTokenParsingUseCase,
    private val memberFindUseCase: MemberFindUseCase
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.hasParameterAnnotation(Authorization::class.java)
    }

    @Throws(Exception::class)
    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): AuthorizationUser? =
        parameter.getParameterAnnotation(Authorization::class.java)
            ?.let { authorizationUser(it, webRequest) }

    private fun authorizationUser(authorization: Authorization, webRequest: NativeWebRequest): AuthorizationUser? {
        val request = webRequest.nativeRequest as HttpServletRequest
        val authorizationHeader = request.getHeader(HttpHeaders.AUTHORIZATION)
        val accessToken = parsingBearerToken(authorizationHeader)
        val memberId = authorizationTokenParsingUseCase.parsing(accessToken).let(String::toLong)
        val authorizationMember = memberFindUseCase.findById(memberId)
        return authorization.value
            .firstOrNull { it.isAll() || authorizationMember.isSameAuthority(it.name) }
            ?.let { AuthorizationUser(memberId) }
    }

    private fun parsingBearerToken(bearerToken: String): String {
        if (bearerToken.startsWith(BEARER)) {
            return bearerToken.replace(BEARER, EMPTY)
        }
        return bearerToken
    }

    companion object {
        private const val BEARER: String = "Bearer "
        private const val EMPTY: String = ""
    }
}
