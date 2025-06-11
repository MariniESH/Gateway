package com.example.apigateway.config;

import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class GatewayConfiguration {

    private final AuthenticationFilter authenticationFilter;

    public GatewayConfiguration(AuthenticationFilter authenticationFilter) {
        this.authenticationFilter = authenticationFilter;

    }

    @Bean
    public RouteLocator routes(RouteLocatorBuilder builder) {
        String corso = "http://localhost:8080";
        String docenteAlunni = "http://localhost:8081";
        return builder.routes()
                .route(r -> r.path("/corsi/**", "/iscrizioni/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(corso))
                .route(r -> r.path("/alunni/**", "/docenti/**")
                        .filters(f -> f.filter(authenticationFilter))
                        .uri(docenteAlunni))
                .build();
    }
}
