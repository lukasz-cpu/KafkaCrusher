package com.example.kafkacrusher.configuration;


import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openApi() {
        return new OpenAPI()
                .info(new Info().title("KafkaCrusher")
                        .description("Simple REST client for Kafka")
                        .version("v0.0.1")
                        .license(new License().name("").url("")))
                .externalDocs(new ExternalDocumentation()
                        .description("")
                        .url(""));
    }
}
