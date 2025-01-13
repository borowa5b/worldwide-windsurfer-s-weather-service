package com.borowa5b.wwws.infrastructure.configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class SwaggerConfiguration {

    @Bean
    public OpenAPI openApi() {
        final var openAPI = new OpenAPI();
        openAPI.info(new Info()
                .title("Worldwide Windsurfers Weather Service")
                .description("Author: Mateusz Borowski <<borowa5b@gmail.com>>")
                .version("v1"));
        final var server = new Server();
        server.url("/");
        openAPI.servers(List.of(server));
        return openAPI;
    }
}
