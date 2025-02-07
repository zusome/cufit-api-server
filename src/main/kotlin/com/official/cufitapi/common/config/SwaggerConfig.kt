package com.official.cufitapi.common.config

import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile


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
    fun openAPI(): OpenAPI =
        OpenAPI()
            .info(
                Info()
                    .title("Cufit API Docs")
                    .description(DESCRIPTION)
                    .version("1.0.0"),
            )
}