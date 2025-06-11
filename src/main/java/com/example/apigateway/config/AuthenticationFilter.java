package com.example.apigateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthenticationFilter implements GatewayFilter {

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String authentication = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (authentication != null) {
            return chain.filter(exchange.mutate().request(rq -> rq.headers(headers -> headers.set(HttpHeaders.AUTHORIZATION, authentication))).build());
        }
        return chain.filter(exchange);
    }
}
