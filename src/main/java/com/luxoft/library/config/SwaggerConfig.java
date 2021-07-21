package com.luxoft.library.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import org.springframework.context.annotation.Configuration;

@Configuration
@OpenAPIDefinition(
        info = @io.swagger.v3.oas.annotations.info.Info(
                title = "Library Project",
                version = "1.0",
                description = "Library Project REST API using Spring-Boot"
        ))
public class SwaggerConfig {
}
