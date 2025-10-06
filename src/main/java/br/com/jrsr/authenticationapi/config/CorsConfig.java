package br.com.jrsr.authenticationapi.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") //mapeia todas as rotas
                .allowedOrigins("http://localhost:4200") //origem que pode acessar a API
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") //métodos HTTP permitidos
                .allowedHeaders("*") //quais cabeçalhos podem ser enviados
                .allowCredentials(true); //se cookies e credenciais são permitidos
    }
}
