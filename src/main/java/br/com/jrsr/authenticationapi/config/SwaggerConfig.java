package br.com.jrsr.authenticationapi.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.Contact;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI authenticationAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Authentication API")
                        .version("1.0.0")
                        .description("API for user authentication")
                        .contact(new Contact()
                                .name("João Rezende")
                                .email("joaoricardorezende@gmail.com")
                        ))
                .externalDocs(new ExternalDocumentation()
                        .description("Complete Documentation")
                        .url("https://github.com/jrsrezende/authentication-api"));
    }
}
