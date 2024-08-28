package io.vinicius.banking.api.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springdoc.core.customizers.OpenApiCustomizer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class OpenApiConfig {
    @Bean
    fun customOpenApi(): OpenAPI {
        return OpenAPI()
            .info(
                Info()
                    .title("Banking API")
                    .description("API Gateway to send requests to other microservices")
                    .version("1.0")
            )
            .components(
                Components()
                    .addSecuritySchemes(
                        "access-token",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    )
                    .addSecuritySchemes(
                        "refresh-token",
                        SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")
                    )
            )
    }

    @Bean
    fun sortSchemasAlphabetically() = OpenApiCustomizer {
        it.components.schemas = it.components.schemas.toSortedMap()
    }
}