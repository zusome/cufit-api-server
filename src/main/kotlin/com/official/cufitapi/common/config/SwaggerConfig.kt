package com.official.cufitapi.common.config

import com.google.api.ErrorReason
import com.official.cufitapi.common.annotation.Authorization
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.Operation
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.media.StringSchema
import io.swagger.v3.oas.models.parameters.Parameter
import org.springdoc.core.customizers.OperationCustomizer
import org.springdoc.core.models.GroupedOpenApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import org.springframework.web.method.HandlerMethod

@Configuration
@Profile("!prod")
class SwaggerConfig {
    companion object {
        const val DESCRIPTION = """
        인증은 JWT 기반으로 이루어져있습니다.
        Header: Authorization : Bearer {token}
        
        401 에러가 발생할 경우, 토큰이 만료되었거나, 잘못된 토큰일 수 있습니다. -> 토큰을 재발급 받아주세요.
        그럼에도 동작하지 않는 경우, -> 서버에게 문의해주세요.
         
        500 에러가 발생할 경우, 서버에 문제가 있을 수 있습니다. -> 서버에게 문의해주세요.
        """
    }

    @Bean
    fun generalGroupedOpenApi(): GroupedOpenApi {
        return GroupedOpenApi
            .builder()
            .group("Cufit")
            .addOpenApiCustomizer { openApi: OpenAPI ->
                openApi.info = Info()
                    .title("Cufit API Docs")
                    .description(DESCRIPTION)
                    .version("1.0.0")
            }
            .addOperationCustomizer(customize())
            .build()
    }


    @Bean
    fun customize(): OperationCustomizer {
        return OperationCustomizer { operation: Operation, handlerMethod: HandlerMethod ->
            val methodParameters = handlerMethod.methodParameters
            val hasAuthorizationAnnotation = methodParameters
                .any { it.hasParameterAnnotation(Authorization::class.java) }

            if (hasAuthorizationAnnotation) {
                // operation.parameters가 null인지 확인
                val existingParameters = operation.parameters ?: mutableListOf()

                // 기존 파라미터에서 특정 파라미터 제거
                val filteredParameters = existingParameters
                    .filterNot { parameter -> parameter.name == "authorizationUser" }

                operation.parameters = filteredParameters.toMutableList()

                // Authorization 헤더 추가
                val authHeader = Parameter()
                    .apply { `in`("header") }
                    .apply { name("Authorization") }
                    .apply { description("Access token") }
                    .apply { required(true) }
                    .apply { schema(StringSchema().example("Bearer {{accessToken}}")) }

                operation.addParametersItem(authHeader)
            }
            operation
        }
    }
}
