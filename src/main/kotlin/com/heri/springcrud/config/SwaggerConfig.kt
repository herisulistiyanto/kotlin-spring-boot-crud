package com.heri.springcrud.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.ApiInfo
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket {
        return Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.heri.springcrud.controller"))
                .paths(PathSelectors.any())
                .build()
                .apiInfo(metaInfo())
    }

    private fun metaInfo(): ApiInfo {
        return ApiInfo(
                "Simple Crud API",
                "Created Using Kotlin Spring-Boot",
                "V1",
                "Use it wisely bruh",
                Contact("Heri Sulistiyanto", "", ""),
                "",
                "",
                emptyList()
        )
    }

}