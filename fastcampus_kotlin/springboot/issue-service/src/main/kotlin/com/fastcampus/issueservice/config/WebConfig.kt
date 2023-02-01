package com.fastcampus.issueservice.config

import com.fastcampus.issueservice.exception.UnauthorizedException
import com.fasterxml.jackson.annotation.JsonProperty
import kotlinx.coroutines.runBlocking
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Configuration
import org.springframework.core.MethodParameter
import org.springframework.stereotype.Component
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport

@Configuration
class WebConfig(
    private val authUserHandlerArgumentResolver: AuthUserHandlerArgumentResolver,
): WebMvcConfigurationSupport() {

    override fun addArgumentResolvers(argumentResolvers: MutableList<HandlerMethodArgumentResolver>) {
        argumentResolvers.apply { // scope 함수
            add(authUserHandlerArgumentResolver)
        }
    }

    override fun addResourceHandlers(registry: ResourceHandlerRegistry) {
        registry.addResourceHandler("/**")
            .addResourceLocations(
                "classpath:/META-INF/resources/",
                "classpath:/resources/",
                "classpath:/static/",
                "classpath:/public/"
            )
    }
}

@Component
class AuthUserHandlerArgumentResolver(
    @Value("\${auth.url}") val authUrl: String, // application yml 에 있음
) : HandlerMethodArgumentResolver { // 스프링 제공 인터페이스 구현

    override fun supportsParameter(parameter: MethodParameter): Boolean =
        // true 이면 아래 resolveArgument()가 실행된다.
        AuthUser::class.java.isAssignableFrom(parameter.parameterType) // 타입 체크

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): Any? {

        val authHeader = webRequest.getHeader("Authorization") ?: throw UnauthorizedException()

        // blocking (runBlocking)
        return runBlocking {
            WebClient.create()
                .get()
                .uri(authUrl)
                .header("Authorization", authHeader)
                .retrieve()
                .awaitBody<AuthUser>()
        }
    }

}

/**
 * 인증 객체
 */
data class AuthUser(
    @JsonProperty("id")
    val userId: Long,
    val username: String,
    val email: String,
    val profileUrl: String? = null,
)