package com.example.HW4.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("User management API")
                        .version("1.0")
                        .description("API для управления пользователями HATEOAS"))
                .addServersItem(new Server().url("/").description("Default Server URL"));
    }

}
