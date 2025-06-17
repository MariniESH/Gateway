package com.example.apigateway.filter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class HeaderLoggingFilter implements GatewayFilter {

    private static final Logger log = LoggerFactory.getLogger(HeaderLoggingFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        exchange.getRequest()
                .getHeaders()
                .forEach((name, values) ->
                        log.info("[Gateway → downstream] {} = {}", name, values));
        return chain.filter(exchange);
    }
}