package com.example.kafkacrusher.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
    
    @Bean
    public Docket get() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .paths(PathSelectors.ant("/*"))
                .apis(RequestHandlerSelectors.basePackage("com.example.kafkacrusher"))
                .build().apiInfo(createApiInfo());
    }

    private ApiInfo createApiInfo() {
        return new ApiInfo("Kafka Crusher",
                "REST client for Kafka",
                "1.0.0-beta",
                "-",
                new Contact("Lukasz Popek", "-", "lukasz.popek@hotmail.com"),
                "-",
                "-",
                Collections.emptyList()
        );
    }
}
